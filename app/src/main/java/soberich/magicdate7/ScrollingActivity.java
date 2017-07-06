package soberich.magicdate7;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.joda.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import soberich.magicdate7.controller.CustomExpandableListAdapter;
import soberich.magicdate7.controller.ExpandableListDataPump;
import soberich.magicdate7.wheelpicker.widgets.WheelDatePicker;

/**
 * MagicDate
 * @author soberich 2017-05-12
 * @author soberich 2017-06-17
 *         New project structure
 * @version 1.2.0
 */
public class ScrollingActivity extends AppCompatActivity {

    static final int CALENDAR_EVENT_ADDED = 100;
    static final String DEBUG_TAG = "fucking",
            SESSION_ID_KEY = "session_id",
            SAME_BIRTHDAY_REQUESTED_COUNT = "same_birthday_requested_count";
    String device_brand, device_build_version, device_model,
            os_family, os_version, app_version,
            country, closest_locality, system_language, latitude, longitude;
    Timestamp timestamp,
    // query_timestamp,
    onResumeTimestamp, onPauseTimestamp;
    long session_start_timestamp,
            session_stop_timestamp,
            duration = 0;
    long sameBdayReqCount = 0L, sessionID = 0L;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List < String > expGroupTitle;
    HashMap < String, List < String >> expListWholeSet;
    Button buttonOK;
    ImageButton imageButtonAddCalEvent;
    View buffer;

    WheelDatePicker wheelDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FloatingActionButton fab = null;
        try {
            setContentView(R.layout.activity_scrolling);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            device_brand = Build.MANUFACTURER;
            device_build_version = Build.VERSION_CODES.class.getFields()[android.os.Build.VERSION.SDK_INT].getName();
            device_model = Build.MODEL;
            os_version = Build.VERSION.RELEASE;
            app_version = BuildConfig.VERSION_NAME;
            country = Locale.getDefault().getISO3Country();
            system_language = Locale.getDefault().getISO3Language();
            sameBdayReqCount = 0L;
            sessionID = 0L;

            session_start_timestamp = getCurrTimestamp().getTime();
            // session_stop_timestamp
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.v(DEBUG_TAG, e.getMessage());
            e.printStackTrace();
        }

        JSONObject jsonObjSession = null;
        try {
            jsonObjSession = new JSONObject();
            jsonObjSession.put("device_brand", device_brand);
            jsonObjSession.put("device_build_version", device_build_version);
            jsonObjSession.put("device_model", device_model);
            jsonObjSession.put("os_version", os_version);
            jsonObjSession.put("app_version", app_version);
            jsonObjSession.put("country", country);
            jsonObjSession.put("closest_locality", closest_locality);
            jsonObjSession.put("location_longitude", longitude);
            jsonObjSession.put("location_latitude", latitude);
            jsonObjSession.put("system_language", system_language);
            jsonObjSession.put("session_start_timestamp", String.valueOf(session_start_timestamp));

            Toast.makeText(getApplicationContext(),
                    jsonObjSession.toString(1), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),
                    "JSON build failure", Toast.LENGTH_LONG).show();
        }

        postViaVolley(jsonObjSession,
                SESSION_ID_KEY,
                "http://soberich.myartsonline.com/create_session.php");

        wheelDatePicker = new WheelDatePicker(this);
        wheelDatePicker = (WheelDatePicker) findViewById(R.id.wheelDatePicker);
        wheelDatePicker.setSelectedYear(Calendar.getInstance().get(Calendar.YEAR));
        wheelDatePicker.setSelectedMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
        wheelDatePicker.setSelectedDay(Calendar.getInstance().get(Calendar.DATE));

        buttonOK = (Button) findViewById(R.id.buttonOK);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate localDate = new LocalDate(wheelDatePicker.getCurrentDate());
                try {
                    Toast.makeText(ScrollingActivity.this,
                            wheelDatePicker.getCurrentDate().toString(), Toast.LENGTH_SHORT).show();
                    expListWholeSet = ExpandableListDataPump.getData(ScrollingActivity.this, localDate);

                    expGroupTitle = new ArrayList < > (expListWholeSet.keySet());
                    expandableListAdapter = new CustomExpandableListAdapter(ScrollingActivity.this,
                            expGroupTitle, expListWholeSet);
                    expandableListView.setAdapter(expandableListAdapter);
                    imageButtonAddCalEvent = (ImageButton) ScrollingActivity.this.findViewById(R.id.imageButton_plus);
                } catch (Exception e) {
                    Toast.makeText(ScrollingActivity.this,
                            e.getMessage(),Toast.LENGTH_LONG).show();
                }
                JSONObject jsonObjQuery = null;
                try {
                    jsonObjQuery = new JSONObject();
                    jsonObjQuery.put("session_id", sessionID);
                    // jsonObjQuery.put("query_timestamp", query_timestamp);
                    jsonObjQuery.put("birthday_requested", localDate.toString());

                    Toast.makeText(ScrollingActivity.this.getApplicationContext(),
                            jsonObjQuery.toString(1), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ScrollingActivity.this.getApplicationContext(),
                            "JSON build failure", Toast.LENGTH_LONG).show();
                }

                ScrollingActivity.this.postViaVolley(jsonObjQuery,
                        SAME_BIRTHDAY_REQUESTED_COUNT,
                        "http://soberich.myartsonline.com/create_query.php");

            }
        });

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(ScrollingActivity.this,
                        expGroupTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(ScrollingActivity.this,
                        expGroupTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(ScrollingActivity.this,
                        expGroupTitle.get(groupPosition) +
                                " -> " +
                                expListWholeSet.get(
                                        expGroupTitle.get(groupPosition)).get(
                                        childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_EMAIL, "43personal@gmail.com");
                intent.putExtra(Intent.EXTRA_SUBJECT,
                        "Hey! Just Take a Look at this Magic app! I've got fantastic reason for celebrating!");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (0 != sessionID) {
            // увеличиваем время продолжительности сеанса
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onResumeTimestamp = getCurrTimestamp();

    }

    @Override
    protected void onPause() {
        super.onPause();
        duration += getCurrTimestamp().getTime() - onResumeTimestamp.getTime();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //sessionID = 0L;
        session_stop_timestamp = getCurrTimestamp().getTime();
    }

    public Timestamp getCurrTimestamp() {
        return new Timestamp(Calendar.getInstance().getTime().getTime());
    }

    public void onClickAddEvent(View v) {
        buffer = v;
        String groupFullDescription = ((TextView)(((View) v.getParent())
                .findViewById(R.id.listTitle)))
                .getText().toString();
        LocalDate localDate = LocalDate.parse(groupFullDescription.substring(0, 10));

        String descriptionForCalendar = groupFullDescription.substring(15)
                .replace("\t", "")
                .replace("\n", " ");

        Toast.makeText(this, groupFullDescription
                        .substring(0, 10),
                Toast.LENGTH_LONG).show();

        Calendar beginTime = Calendar.getInstance();
        beginTime.set(localDate.getYear(), localDate.getMonthOfYear() - 1, localDate.getDayOfMonth());

        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true)
                .putExtra(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALARM)
                .putExtra(CalendarContract.Reminders.MINUTES, 0)
                .putExtra(CalendarContract.Reminders.MINUTES, 0)
                .putExtra(CalendarContract.Reminders.MINUTES, 0)
                .putExtra(CalendarContract.Events.TITLE, descriptionForCalendar)
                .putExtra(CalendarContract.Events.DESCRIPTION, "" + descriptionForCalendar)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, "Planet Earth")
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_FREE)
                .putExtra(Intent.EXTRA_EMAIL, new String[] {
                        "43personal@gmail.com"
                });

        startActivityForResult(intent, CALENDAR_EVENT_ADDED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == CALENDAR_EVENT_ADDED) {
            // Don't know why OK request is not recognized when done,
            // either RESULT_CANCELED IS recognized when request OK ?!??!?!
            if (resultCode == Activity.RESULT_CANCELED) {
                // The user added the event.
                ((ImageButton) buffer).setImageResource(R.drawable.ic_action_event_added);
            }
        }
    }

    private void postViaVolley(JSONObject JSON, final String response_key, String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, JSON,
                new Response.Listener < JSONObject > () {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("Volley:Response ", "" + response.toString());
                        Toast.makeText(ScrollingActivity.this.getApplicationContext(),
                                response.toString() + "", Toast.LENGTH_LONG).show();
                        switch (response_key) {
                            case SAME_BIRTHDAY_REQUESTED_COUNT:
                                try {
                                    sameBdayReqCount = response.getLong(response_key);
                                    Toast.makeText(ScrollingActivity.this.getApplicationContext(),
                                            String.valueOf(sameBdayReqCount), Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(ScrollingActivity.this.getApplicationContext(),
                                            e.toString(), Toast.LENGTH_LONG).show();
                                }
                                break;
                            case SESSION_ID_KEY:
                                //assignResponseValue(JSON, (Long)sessionID, response_key);
                                try {
                                    sessionID = response.getLong(response_key);
                                    Toast.makeText(ScrollingActivity.this.getApplicationContext(),
                                            String.valueOf(sessionID), Toast.LENGTH_LONG).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(ScrollingActivity.this.getApplicationContext(),
                                            e.toString(), Toast.LENGTH_LONG).show();
                                }
                                break;
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.v("Volley:ERROR ", error.getMessage());
                Toast.makeText(ScrollingActivity.this.getApplicationContext(),
                        error.toString() + "", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        requestQueue.add(jsObjRequest);
        requestQueue.start();
    }
}
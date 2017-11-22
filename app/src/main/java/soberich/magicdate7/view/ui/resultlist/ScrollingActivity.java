package soberich.magicdate7.view.ui.resultlist;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.joda.time.LocalDate;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import soberich.magicdate7.R;
import soberich.magicdate7.view.ui.about.AboutActivity;
import soberich.magicdate7.view.ui.wheelpicker.widgets.WheelDatePicker;
import soberich.magicdate7.viewmodel.ResEntriesViewModel;

/**
 * MagicDate
 * @author soberich 2017-05-12
 * @author soberich 2017-06-17
 *         New project structure
 * @version 1.2.0
 */
public class ScrollingActivity extends AppCompatActivity
        implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    private static final String TAG = "WholeApp";
    private static final String FRAG_TAG_STRING = "RESULT_FRAG";
    private Bundle savedInstanceState;
    private ResEntriesListFragment resFragment;
    private ResEntriesViewModel viewModel;

    Button buttonOK;
    WheelDatePicker wheelDatePicker;
    LocalDate lastDateRequested;

//    @Override
//    public AndroidSupportInjector<Fragment> fragmentInjector() {
//        return fragmentInjector;
//    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;
        viewModel = ViewModelProviders.of(this).get(ResEntriesViewModel.class);
        Log.d("TAG", viewModel.getBirthDate().toString());
        FloatingActionButton fab = null;
         setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //wheelDatePicker = new WheelDatePicker(this);
        wheelDatePicker = findViewById(R.id.wheel_date_picker);
        wheelDatePicker.setSelectedYear(viewModel.getBirthDate().getYear());
        wheelDatePicker.setSelectedMonth(viewModel.getBirthDate().getMonthOfYear());
        wheelDatePicker.setSelectedDay(viewModel.getBirthDate().getDayOfMonth());

        if (findViewById(R.id.main_container) != null
                && resFragment == null) {
            startFragmentForDisplayResult();
        }

        buttonOK = findViewById(R.id.button_OK);
        //expandableListView = (ExpandableListView) findViewById(R.id.expandable_list_view);

        buttonOK.setOnClickListener(view -> {
            WeakReference<Context> contextWeakReference = new WeakReference<>(ScrollingActivity.this);
            LocalDate localDate = new LocalDate(wheelDatePicker.getCurrentDate());
            if (!localDate.equals(lastDateRequested)) {
                lastDateRequested = localDate;
                viewModel.loadEntries(localDate);
            } else {
                Toast.makeText(contextWeakReference.get(), "Please, see your last result for this date!", Toast.LENGTH_LONG).show();
            }

            /*JSONObject jsonObjQuery = null;
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
                    "http://soberich.myartsonline.com/create_query.php");*/

        });

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, "43personal@gmail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT,
                    "Hey! Just Take a Look at this Magic app! I've got fantastic reason for celebrating!");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Intent intentAlter = new Intent(Intent.ACTION_SEND);
                intentAlter.setType("text/html");
                intentAlter.putExtra(Intent.EXTRA_EMAIL, "43personal@gmail.com");
                intentAlter.putExtra(Intent.EXTRA_SUBJECT, "Hey! Just Take a Look at this Magic app! I've got fantastic reason for celebrating!");
                intentAlter.putExtra(Intent.EXTRA_TEXT, "I'm email body.");

                if (intentAlter.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(intentAlter, "Send Email"));
                } else {
                    Snackbar.make(view, "No email app found, maybe install Gmail", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }

    private void startFragmentForDisplayResult() {
        Log.d(TAG, "startFragmentForDisplayResult() called");
        if(savedInstanceState == null) {
            Log.d("TAG", "savedInstanceState == null");
            //resFragment = new ResEntriesListFragment();
            Log.d("TAG","resFragment object = "+ resFragment.toString());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, resFragment, FRAG_TAG_STRING)
                    .addToBackStack(FRAG_TAG_STRING)
                    .commit();
        } else {
            Log.d(TAG, viewModel.getBirthDate().toString());
            //resFragment = getSupportFragmentManager().findFragmentByTag(FRAG_TAG_STRING);
        }
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
        if (id == R.id.about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
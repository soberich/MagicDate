package soberich.magicdate7.view.ui.resultlist;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.LocalDate;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.List;

import soberich.magicdate7.R;
import soberich.magicdate7.view.viewobjects.ResEntry;

/**
 *
 * Created by soberich on 9/17/17.
 */

public class ResEntryListAdapter extends BaseExpandableListAdapter {

    private WeakReference<Context> context;
    private List<ResEntry> resEntries;
    //private int previousResEntriesSize;

    public ResEntryListAdapter(Context context, List<ResEntry> resEntries) {
        //expGroupTitle = Arrays.asList(expListWholeSet.keySet().toArray().toString());
        //this.resEntries = new ArrayList<>(resEntries == null ? 0 : this.resEntries.size());
        //this.resEntries = new ArrayList<>();
        this.resEntries = resEntries;
        //this.resEntries = resEntries;
        this.context = new WeakReference<>(context);
        Log.d("TAG", String.valueOf(resEntries == null ? 0 : this.resEntries.size())+" = this is the size of resEntry");
    }


    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        //Log.d("TAG", String.valueOf(resEntries.get(listPosition).getDescription().get(expandedListPosition)) + " = getChild()");
        return resEntries.get(listPosition).getDescription().get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        Log.d("TAG", String.valueOf(expandedListPosition) + " = getChildId()");
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        Log.d("TAG", String.valueOf(convertView ==null) + " !! convertView is null (true is BAD");
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.get()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            Log.d("TAG", String.valueOf(convertView ==null) + " !! convertView is null");
        }
        TextView expandedListTextView = convertView.findViewById(R.id.expanded_list_item);
        expandedListTextView.setText(expandedListText);
        //Log.d("TAG", expandedListText + " = getChildView() => expandedListText");
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        Log.d("TAG", String.valueOf(resEntries.get(listPosition).getDescription().size()) + " = getChildrenCount()");
        return resEntries.get(listPosition).getDescription().size();
    }

    @Override
    public Object getGroup(int listPosition) {
        Log.d("TAG", String.valueOf(resEntries.get(listPosition)) + " = getGroup()");
        return resEntries.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        //previousResEntriesSize += resEntries == null ? 0 : resEntries.size() -previousResEntriesSize;
        //Log.d("TAG", String.valueOf(resEntries.get(0).getResultDate().toString()) + "  "+resEntries.get(0).getComment() + " = First res");
        Log.d("TAG", String.valueOf(resEntries == null ? 0 : resEntries.size()) + " = getGroupCount()");
        return resEntries == null ? 0 : resEntries.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        Log.d("TAG", String.valueOf(listPosition) + " = getGroupId()");
        return listPosition/* + getGroupCount() - previousResEntriesSize*/;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        final ResEntry currResEntry = resEntries.get(listPosition);
        String listTitleLeft = currResEntry.getResultDate().toString("d MMM yyyy");
        String listTitleRight = String.valueOf(currResEntry.getComment());

        Log.d("TAG", "getGroupView() => " + String.valueOf(listPosition));
        //Log.d("TAG", String.valueOf(listTitleLeft) + " = listTitleLeft var");
        //Log.d("TAG", String.valueOf(listTitleRight) + " = listTitleRight var");

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.get()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }

	    // TODO DELETE !!
        //isExpanded = currResEntry.isExpanded();

        TextView listTitleTextViewLeft = convertView.findViewById(R.id.list_group_title_left),
                 listTitleTextViewRight = convertView.findViewById(R.id.list_group_title_right);
        listTitleTextViewLeft.setText(listTitleLeft);
        listTitleTextViewRight.setText(listTitleRight);

        ImageButton imageButton = convertView.findViewById(R.id.image_button_plus);

        //code that marks a row as liked depending on the datasource item's value.
        if(!currResEntry.isAddedToCal()) {
            imageButton.setImageResource(R.drawable.ic_calendar_add);
        } else {
            imageButton.setImageResource(R.drawable.ic_calendar_added);
        }
        imageButton.setOnClickListener(view -> {

            // TODO switch to DataBinding
            //((ImageButton)view).setImageResource(R.drawable.ic_action_event_added);
            //////////////////////
            Log.d("TAG", String.valueOf(currResEntry.isAddedToCal()) + " = resEntries.get(listPosition) expression");

            LocalDate localDate = currResEntry.getResultDate();
            Log.d("TAG", localDate.toString() + " = localDate.toString()");
            String descForCalendar = currResEntry.getComment();
            Log.d("TAG", descForCalendar + " = descForCalendar var");

            // TODO delete Toast
            Toast.makeText(context.get(), localDate.toString("dd-MM-yyyy")
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
                    .putExtra(CalendarContract.Events.TITLE, descForCalendar)
                    .putExtra(CalendarContract.Events.DESCRIPTION, descForCalendar)
                    .putExtra(CalendarContract.Events.EVENT_LOCATION, "Planet Earth")
                    .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_FREE);

            Log.d("TAG", String.valueOf(currResEntry.isAddedToCal()) + " = BOOLEAN isAddedToCal");
            //set class member called isAddedToCal in datasource class in this onClickListener
            currResEntry.setAddedToCal(true);
            //set tag to the row index, so to know which is modified
            notifyDataSetChanged();
            context.get().startActivity(intent);
        });
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        Log.d("TAG", "hasStableIds()");
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        Log.d("TAG", "isChildSelectable()");
        return true;
    }

}


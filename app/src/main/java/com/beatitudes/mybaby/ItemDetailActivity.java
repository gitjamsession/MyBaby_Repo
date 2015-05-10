package com.beatitudes.mybaby;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.beatitudes.mybaby.Baby.BabyVaccineContentProvider;


/**
 * An activity representing a single Item detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link ItemDetailFragment}.
 */
public class ItemDetailActivity extends ActionBarActivity {

    private static final int PROJECTION_ID_INDEX = 0;
    private static final int PROJECTION_VACCINE_NAME_INDEX = 1;
    private static final int PROJECTION_VACCINE_DATE_INDEX = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

      /*  EditText etName, etHospVisitDate;
        TextView tvEntriesList;*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        // Show the Up button in the action bar.
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }

        Button view = (Button) findViewById(R.id.viewButton);
        Button add = (Button) findViewById(R.id.createButton);
        Button modify = (Button) findViewById(R.id.updateButton);
        Button delete = (Button) findViewById(R.id.deleteButton);



        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etHospVisitDate = (EditText) findViewById(R.id.etHospVisitDate);

        final TextView tvEntriesList = (TextView) findViewById(R.id.tvContactsText);


        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                displayEntries();
                Log.v("VaccineCP<<<<<<", "Completed Displaying Entries list>>>>>>>>>>");
            }


        });

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String name = etName.getText().toString();
                String visit_date = etHospVisitDate.getText().toString();

                if (name.equals("") && visit_date.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }


                //createEntry(name, visit_date);
                Log.v("VaccineCP","<<<<Created new Hospital visit Entry");
            }
        });

        modify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String name = etName.getText().toString();
                String visit_date = etHospVisitDate.getText().toString();

                if (name.equals("") && visit_date.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields are empty",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                //updateEntry(name, visit_date);
                Log.v("VaccineCP<<<<<<<<<","updated vaccination entry>>>>>>");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String name = etName.getText().toString();

                if (name.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Vaccine Name Field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                //deleteEntry(name);
                Log.v("VaccineCP<<<<<<","Deleted created entry>>>>>");
            }

        });}


    private void displayEntries() {

        String[] projection =
                new String[]{
                        BabyVaccineContentProvider.KEY_ID,
                        BabyVaccineContentProvider.KEY_VACCINE_NAME,
                        BabyVaccineContentProvider.KEY_VACCINE_DATE};

        Cursor calCursor = null;

        ContentResolver cr = getContentResolver();

        Uri uri = BabyVaccineContentProvider.CONTENT_URI;

        /*String selection = "((" + CalendarContract.Calendars.CALENDAR_DISPLAY_NAME + " = ?))";
        String[] selectionArgs = new String[] {"Vaccination Calendar"};

        calCursor = cr.query(uri, projection, selection, selectionArgs, null);*/
        calCursor = cr.query(uri, null, null, null, null);

        while (calCursor.moveToNext()) {
            long calID = 0;
            String displayName = null;
            String displayDate = null;


            // Get the field values
            calID = calCursor.getLong(PROJECTION_ID_INDEX);
            displayName = calCursor.getString(PROJECTION_VACCINE_NAME_INDEX);
            displayDate = calCursor.getString(PROJECTION_VACCINE_DATE_INDEX);

            Log.v("STRTAG>>>>>>","Details printed<<<<<<<<<<<<<<"+displayName+displayDate);

        }


    }
/*
    //// To get Calendar ID - Helper Function
    private long getCalendarId() {
        String[] projection = new String[]{CalendarContract.Calendars._ID};
        String selection =
                CalendarContract.Calendars.ACCOUNT_NAME +
                        " = ? " +
                        CalendarContract.Calendars.ACCOUNT_TYPE +
                        " = ? ";
        // use the same values as above:
        String[] selArgs =
                new String[]{
                        MY_ACCOUNT_NAME,
                        CalendarContract.ACCOUNT_TYPE_LOCAL};
        Cursor cursor =
                getContentResolver().
                        query(
                                CalendarContract.Calendars.CONTENT_URI,
                                projection,
                                selection,
                                selArgs,
                                null);
        if (cursor.moveToFirst()) {
            return cursor.getLong(0);
        }
        return -1;
    }
*/
    /*
    private void createEntry(String name, String entry) {


        long calId = getCalendarId();
        if (calId == -1) {
            // no calendar account; react meaningfully
            return;
        }
        Calendar cal = new GregorianCalendar(2012, 11, 14);
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long start = cal.getTimeInMillis();
        ContentValues values2 = new ContentValues();
        values2.put(CalendarContract.Events.DTSTART, start);
        values2.put(CalendarContract.Events.DTEND, start);
        values2.put(CalendarContract.Events.RRULE,
                "FREQ=DAILY;COUNT=20;BYDAY=MO,TU,WE,TH,FR;WKST=MO");
        values2.put(CalendarContract.Events.TITLE, "Some title");
        values2.put(CalendarContract.Events.EVENT_LOCATION, "Kalamassery");
        values2.put(CalendarContract.Events.CALENDAR_ID, calId);
        values2.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Calcutta");
        values2.put(CalendarContract.Events.DESCRIPTION,
                "Description of the event - All day Vaccination event");
// reasonable defaults exist:
        values2.put(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
        values2.put(CalendarContract.Events.SELF_ATTENDEE_STATUS,
                CalendarContract.Events.STATUS_CONFIRMED);
        values2.put(CalendarContract.Events.ALL_DAY, 1);
        values2.put(CalendarContract.Events.ORGANIZER, "some.mail@some.address.com");
        values2.put(CalendarContract.Events.GUESTS_CAN_INVITE_OTHERS, 1);
        values2.put(CalendarContract.Events.GUESTS_CAN_MODIFY, 1);
        values2.put(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
        Uri uri2 =
                getContentResolver().
                        insert(CalendarContract.Events.CONTENT_URI, values2);
        long eventId = new Long(uri2.getLastPathSegment());

    }

    private void updateEntry(String name, String entry) {

        long calID = getCalendarId();
        ContentValues values = new ContentValues();
// The new display name for the calendar
        values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, "Vaccine B Calendar");
        Uri updateUri = ContentUris.withAppendedId(CalendarContract.Calendars.CONTENT_URI, calID);
        int rows = getContentResolver().update(updateUri, values, null, null);
        Log.v("Update Log:<<<<<", "Rows updated: " + rows + " " + calID + " " + CalendarContract.Calendars.CALENDAR_DISPLAY_NAME + ">>>>>>>>>");
    }

    private void deleteEntry(String name) {

        ContentResolver cr = getContentResolver();
        String where = CalendarContract.CalendarEntity.ACCOUNT_NAME + " = ? ";
        String[] params = new String[] { name };

        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation
                .newDelete(CalendarContract.Events.CONTENT_URI)
                .withSelection(where, params).build());
        try {
            cr.applyBatch(CalendarContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Toast.makeText(MainActivity.this,
                "Deleted the entry with name '" + name + "'",
                Toast.LENGTH_SHORT).show();

    }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */
}

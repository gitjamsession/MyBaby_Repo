package com.beatitudes.mybaby;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.beatitudes.mybaby.Baby.BabyContent;
import com.beatitudes.mybaby.Baby.BabyVaccineContentProvider;
import com.beatitudes.mybaby.Baby.VaccineItem;
import com.beatitudes.mybaby.Baby.VaccineItemAdapter;

import java.util.ArrayList;

////import android.content.CursorLoader;

/**
 * A list fragment representing a list of Items. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link ItemDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ItemListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {



    private ArrayList<VaccineItem> vaccineItems;
    private VaccineItemAdapter myvaccineitemadapter;
    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: replace with a real list adapter.
        setListAdapter(new ArrayAdapter<BabyContent.BabyItem>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                BabyContent.ITEMS));

        getLoaderManager().initLoader(0, null, this);

    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(BabyContent.ITEMS.get(position).id);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(getActivity(),BabyVaccineContentProvider.CONTENT_URI, null, null, null, null);
        return loader;
    }
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if(cursor!= null && cursor.moveToFirst()) {
            int keyNameIndex = cursor.getColumnIndex(BabyVaccineContentProvider.KEY_VACCINE_NAME);
            vaccineItems.clear();
            while (cursor.moveToNext()) {
                VaccineItem newItem = new VaccineItem(cursor.getString(keyNameIndex));
                vaccineItems.add(newItem);
            }
            myvaccineitemadapter.notifyDataSetChanged();
        }
    }
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void onNewItemAdded(String newItem) {
        ContentResolver cr = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(BabyVaccineContentProvider.KEY_VACCINE_NAME, newItem);
        cr.insert(BabyVaccineContentProvider.CONTENT_URI, values);
        getLoaderManager().restartLoader(0, null, this);
    }

    private void addNewEntry(String name, String date) {
        Cursor cursor = getActivity().getContentResolver().query(
                BabyVaccineContentProvider.CONTENT_URI, null, null, null, null);

        int count = cursor.getCount();

        if (count > 0) {
            while (cursor.moveToNext()) {

                String display_name = BabyVaccineContentProvider.KEY_VACCINE_NAME;
                int colIndex = cursor.getColumnIndex(display_name);
                String existName = cursor.getString(colIndex);

                /**if (existName.equals(name)) {
                    Toast.makeText(BabyVaccineContentProvider.this,
                            "The contact name: " + name + " already exists",
                            Toast.LENGTH_SHORT).show();
                    return;
                }**/
            }
        }

        // Operation
        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        int rawvaccinedataInsertIndex = ops.size();

        ops.add(ContentProviderOperation.newInsert(BabyVaccineContentProvider.CONTENT_URI)
                .withValue(BabyVaccineContentProvider.KEY_VACCINE_NAME, null)
                .withValue(BabyVaccineContentProvider.KEY_VACCINE_DATE, null).build());

        try {
            getActivity().getContentResolver().applyBatch(BabyVaccineContentProvider.CONTENT_AUTHORITY, ops);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /**Toast.makeText(
                NativeContentProvider.this,
                "Created a new contact with name: " + name + " and Phone No: "
                        + phone, Toast.LENGTH_SHORT).show();
        **/
    }
}


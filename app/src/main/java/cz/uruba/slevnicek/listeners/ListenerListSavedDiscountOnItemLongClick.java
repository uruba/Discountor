package cz.uruba.slevnicek.listeners;

import android.support.v7.app.ActionBarActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import cz.uruba.slevnicek.R;
import cz.uruba.slevnicek.SavedDiscountsFragment;
import cz.uruba.slevnicek.helpers.SavedDiscountsHelper;
import cz.uruba.slevnicek.models.item_definitions.DiscountItem;

/**
 * Created by Temp on 29.7.2014.
 */
public class ListenerListSavedDiscountOnItemLongClick implements AdapterView.OnItemLongClickListener {
    private SavedDiscountsFragment fragment;
    private int position;
    private android.support.v7.view.ActionMode actionMode;

    public ListenerListSavedDiscountOnItemLongClick(SavedDiscountsFragment fragment){
        this.fragment = fragment;
    }

    private ListenerListSavedDiscountOnItemLongClick(){

    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        actionMode = ((ActionBarActivity) fragment.getActivity()).startSupportActionMode(new AMCallback());
        this.position = position;

        return true;
    }

    private class AMCallback implements android.support.v7.view.ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(android.support.v7.view.ActionMode actionMode, Menu menu) {
            MenuInflater inflater = actionMode.getMenuInflater();
            inflater.inflate(R.menu.list_discounts, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(android.support.v7.view.ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(android.support.v7.view.ActionMode actionMode, MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.action_delete_item:
                    DiscountItem clickedItem = (DiscountItem) fragment.getListView().getItemAtPosition(position);

                    SavedDiscountsHelper helper = new SavedDiscountsHelper(fragment.getActivity());
                    helper.deleteByID(clickedItem);

                    ((SavedDiscountsFragment.DiscountListArrayAdapter) fragment.getListView().getAdapter()).remove(clickedItem);

                    Toast
                        .makeText(fragment.getActivity(), R.string.saved_discount_item_delete, Toast.LENGTH_SHORT)
                        .show();

                    actionMode.finish();
                    return true;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(android.support.v7.view.ActionMode actionMode) {

        }
    }
}

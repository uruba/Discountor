package cz.uruba.discountor;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;

import cz.uruba.discountor.dialogs.SaveDiscountItemPromptDialog;
import cz.uruba.discountor.listeners.ListenerEditTextInFragmentFocus;
import cz.uruba.discountor.models.ModelDiscountItem;
import cz.uruba.discountor.models.item_definitions.DiscountItem;
import cz.uruba.discountor.utils.CurrencyProvider;


public abstract class AbstractCalculatorFragment extends Fragment {
    protected SharedPreferences sharedPref;
    protected InputMethodManager imm;

    protected ModelDiscountItem modelDiscountItem;

    protected View rootView;
    protected ArrayList<EditText> eligibleEditTexts;
    protected EditText activeEditText;
    protected boolean paused;

    protected ViewGroup layoutMain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        setHasOptionsMenu(true);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        modelDiscountItem = new ModelDiscountItem(this.getActivity());

        paused = false;
        activeEditText = null;
    }

    protected void populateEligibleEditTextsList() {
        // find all the EditText components in the rootView and bind a focus change listener to them
        if (rootView != null) {
            eligibleEditTexts = new ArrayList<>();

            traverseViewGroupForEditTexts((ViewGroup) rootView);
        }
    }

    protected void traverseViewGroupForEditTexts(ViewGroup viewGroup) {
        for (int i = 0, count = viewGroup.getChildCount(); i < count; i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof EditText) {
                view.setOnFocusChangeListener(new ListenerEditTextInFragmentFocus(this));
                eligibleEditTexts.add((EditText) view);
            }
            else if (view instanceof ViewGroup) {
                traverseViewGroupForEditTexts((ViewGroup) view);
            }
        }
    }

    public void setEligibleEditTextsFocusableInTouchMode(boolean requestedValue) {
        if (eligibleEditTexts != null) {
            for (EditText editText : eligibleEditTexts) {
                editText.setFocusable(requestedValue);
                editText.setFocusableInTouchMode(requestedValue);
            }
        }
    }

    @Override
    public void onResume(){
        if ((paused == true) && (activeEditText != null)) {
            // TODO focus previously active EditText instead of the default one
            // focusAndShowKeyboard(activeEditText, true);
            focusDefaultEditText(false);
        } else {
            if (paused == false) {
                resetEditValues();
            }
            focusDefaultEditText(false);
        }

        paused = false;

        super.onResume();
    }

    @Override
    public void onPause(){

        paused = true;

        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.discount, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_reset:
                boolean showKeyboard = sharedPref.getBoolean("settings_show_keyboard_on_delete", true);
                resetEditValues();
                focusDefaultEditText(showKeyboard);
                break;
            case R.id.action_save:
                SaveDiscountItemPromptDialog dialog = new SaveDiscountItemPromptDialog();
                dialog.addOnDiscountSaveListener((ActivityMain) getActivity());
                dialog.setTargetFragment(this, 0);
                dialog.show(getActivity().getSupportFragmentManager(), null);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;

    }

    abstract public void saveResultValuesToDB(String optionalName);

    protected String getStringFromNumberEditText(EditText edit) {
        return edit.getText().toString().equals("") ? edit.getHint().toString() : edit.getText().toString();
    }

    protected String getStringPriceBeforeMinusPriceAfter(DiscountItem item) {
        return CurrencyProvider.getFormattedAmount(item.getPriceBefore(), true) +
                " – " +
                CurrencyProvider.getFormattedAmount(item.getPriceAfter(), true);
    }

    protected void focusAndShowKeyboard(EditText edit, boolean showKeyboard) {
        if (edit != null) {
            edit.requestFocus();
        }


        if (showKeyboard) {
            imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void setActiveEditText(EditText editText) {
        if (eligibleEditTexts.contains(editText)) {
            activeEditText = editText;
        }
    }

    abstract protected DiscountItem calculateResult();

    abstract public void setTextResult();

    abstract public void resetEditValues();
    abstract public void focusDefaultEditText(boolean ShowKeyboard);
}

package cz.uruba.discountor;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import cz.uruba.discountor.dialogs.SaveDiscountItemPromptDialog;
import cz.uruba.discountor.models.ModelDiscountItem;
import cz.uruba.discountor.models.item_definitions.DiscountItem;
import cz.uruba.discountor.utils.CurrencyProvider;


public abstract class AbstractCalculatorFragment extends Fragment {
    protected SharedPreferences sharedPref;
    protected InputMethodManager imm;

    protected ModelDiscountItem modelDiscountItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        setHasOptionsMenu(true);
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        modelDiscountItem = new ModelDiscountItem(this.getActivity());
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
                resetEditValues(showKeyboard);
                break;
            case R.id.action_save:
                SaveDiscountItemPromptDialog dialog = new SaveDiscountItemPromptDialog();
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
        edit.requestFocus();


        if (showKeyboard) {
            imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    abstract protected DiscountItem calculateResult();

    abstract public void setTextResult();

    abstract public void resetEditValues(boolean showKeyboard);
}

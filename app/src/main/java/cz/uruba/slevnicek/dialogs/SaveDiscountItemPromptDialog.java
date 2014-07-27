package cz.uruba.slevnicek.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import cz.uruba.slevnicek.AbstractCalculatorFragment;
import cz.uruba.slevnicek.DiscountCalculatorFragment;
import cz.uruba.slevnicek.R;
import cz.uruba.slevnicek.models.ModelDiscountItem;
import cz.uruba.slevnicek.models.item_definitions.DiscountItem;

/**
 * Created by Temp on 27.7.2014.
 */
public class SaveDiscountItemPromptDialog extends android.support.v4.app.DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Set an EditText view to get user input
        final EditText input = new EditText(this.getActivity());
        input.setHint(R.string.prompt_name_optional);
        input.setSingleLine(true);


        return new AlertDialog.Builder(getActivity())
                .setView(input)
                .setTitle(R.string.promt_save_discount_title)
                .setPositiveButton(R.string.prompt_save,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String optionalName = input.getText().toString();
                                ((DiscountCalculatorFragment)getTargetFragment()).saveResultValuesToDB(optionalName);
                            }
                        }
                )
                .setNegativeButton(R.string.prompt_cancel,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }
                )
                .create();
    }
}

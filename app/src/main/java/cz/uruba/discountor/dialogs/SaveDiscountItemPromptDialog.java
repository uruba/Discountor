package cz.uruba.discountor.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cz.uruba.discountor.DiscountCalculatorFragment;
import cz.uruba.discountor.R;

/**
 * Created by Temp on 27.7.2014.
 */
public class SaveDiscountItemPromptDialog extends android.support.v4.app.DialogFragment {


    @Override
    public void onStart() {
        super.onStart();
        final Resources res = getResources();
        final int themeRed = res.getColor(R.color.theme_red);
        final int lightGrey = res.getColor(R.color.light_grey);

        // Title
        final int titleId = res.getIdentifier("alertTitle", "id", "android");
        final View title = getDialog().findViewById(titleId);
        if (title != null) {
            ((TextView) title).setTextColor(themeRed);
        }

        // Title divider
        final int titleDividerId = res.getIdentifier("titleDivider", "id", "android");
        final View titleDivider = getDialog().findViewById(titleDividerId);
        if (titleDivider != null) {
            titleDivider.setBackgroundColor(lightGrey);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final View dialogView = View.inflate(this.getActivity(), R.layout.dialog_save_discount_item_prompt, null);
        final EditText optionalNameEditText = (EditText) dialogView.findViewById(R.id.optionalNameEditText);

        return new AlertDialog.Builder(getActivity())
                .setView(dialogView)
                .setTitle(R.string.prompt_save_discount_title)
                .setPositiveButton(R.string.prompt_save,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                String optionalName = optionalNameEditText.getText().toString();
                                ((DiscountCalculatorFragment)getTargetFragment()).saveResultValuesToDB(optionalName.trim());
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

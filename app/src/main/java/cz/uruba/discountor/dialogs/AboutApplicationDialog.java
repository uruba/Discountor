package cz.uruba.discountor.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cz.uruba.discountor.DiscountCalculatorFragment;
import cz.uruba.discountor.R;

/**
 * Created by Temp on 3.8.2014.
 */
public class AboutApplicationDialog extends DialogFragment {

    private Resources res;

    @Override
    public void onStart() {
        super.onStart();

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
            titleDivider.setBackgroundColor(themeRed);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        res = getResources();

        View dialogView = View.inflate(this.getActivity(), R.layout.dialog_about_application, null);

        TextView contentAbout = (TextView) dialogView.findViewById(R.id.about_content);
        String text = res.getString(R.string.about_text);
        contentAbout.setText(Html.fromHtml(res.getString(R.string.about_text)));

        return new AlertDialog.Builder(getActivity())
                .setView(dialogView)
                .setTitle(R.string.title_activity_about)
                .setPositiveButton(R.string.prompt_email,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {

                            }
                        }
                )
                .setNeutralButton(R.string.prompt_rate,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {

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

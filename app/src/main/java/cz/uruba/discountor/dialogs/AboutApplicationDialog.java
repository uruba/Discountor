package cz.uruba.discountor.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cz.uruba.discountor.DiscountCalculatorFragment;
import cz.uruba.discountor.R;

/**
 * Created by Temp on 3.8.2014.
 */
public class AboutApplicationDialog extends DialogFragment {

    private Resources res;
    private ActionBarActivity parentActivity;

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
        parentActivity = (ActionBarActivity) getActivity();

        View dialogView = View.inflate(parentActivity, R.layout.dialog_about_application, null);

        TextView versionAbout = (TextView) dialogView.findViewById(R.id.about_version);
        TextView dateBuiltAbout = (TextView) dialogView.findViewById(R.id.about_title);

        PackageInfo pInfo;
        String versionName, dateBuilt;
        try {
            versionName = parentActivity.getPackageManager()
                    .getPackageInfo(parentActivity.getPackageName(), 0)
                    .versionName;

            ApplicationInfo ai = parentActivity.getPackageManager()
                    .getApplicationInfo(parentActivity.getPackageName(), 0);
            ZipFile zf = new ZipFile(ai.sourceDir);
            ZipEntry ze = zf.getEntry("classes.dex");
            long time = ze.getTime();
            dateBuilt = SimpleDateFormat.getDateInstance(DateFormat.LONG).format(new java.util.Date(time));
            zf.close();
        } catch (Exception e) {
            versionName = "";
            dateBuilt = "";
        }

        versionAbout.setText(versionName);
        dateBuiltAbout.setText(dateBuilt);

        TextView contentAbout = (TextView) dialogView.findViewById(R.id.about_content);
        contentAbout.setText(Html.fromHtml(res.getString(R.string.about_text)));

        TextView thankyouAbout = (TextView) dialogView.findViewById(R.id.about_thank_you);
        thankyouAbout.setText(R.string.about_thank_you);

        return new AlertDialog.Builder(getActivity())
                .setView(dialogView)
                .setTitle(R.string.about_title)
                .setIcon(R.drawable.ic_launcher)
                .setPositiveButton(R.string.prompt_email,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("message/rfc822");
                                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"uruba@outlook.com"});
                                i.putExtra(Intent.EXTRA_SUBJECT, "[Discountor] ");
                                try {
                                    startActivity(Intent.createChooser(i, res.getString(R.string.prompt_email_title)));
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(parentActivity, R.string.toast_no_email_client, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                )
                .setNeutralButton(R.string.prompt_rate,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Uri uri = Uri.parse("market://details?id=" + parentActivity.getPackageName());
                                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                                try {
                                    startActivity(goToMarket);
                                } catch (ActivityNotFoundException e) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + parentActivity.getPackageName())));
                                }
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

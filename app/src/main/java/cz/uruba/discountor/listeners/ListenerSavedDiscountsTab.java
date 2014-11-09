package cz.uruba.discountor.listeners;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;

import cz.uruba.discountor.SavedDiscountsFragment;
import cz.uruba.discountor.SavedDiscountsStatsFragment;

/**
 * Created by Vaclav on 9.11.2014.
 */
public class ListenerSavedDiscountsTab<T extends Fragment> implements ActionBar.TabListener {
    private int fragmentContainerId;
    private FragmentActivity activity;
    private String tag;
    private Class<T> fragmentClass;
    private Fragment fragmentInstance;


    public static ListenerSavedDiscountsTab<SavedDiscountsFragment> createInstanceList(int fragmentContainerId,
                                                                                       FragmentActivity activity,
                                                                                       String tag) {
        return new ListenerSavedDiscountsTab<SavedDiscountsFragment>(fragmentContainerId, activity, tag, SavedDiscountsFragment.class);
    }

    public static ListenerSavedDiscountsTab<SavedDiscountsStatsFragment> createInstanceStats(int fragmentContainerId,
                                                                                            FragmentActivity activity,
                                                                                            String tag) {
        return new ListenerSavedDiscountsTab<SavedDiscountsStatsFragment>(fragmentContainerId, activity, tag, SavedDiscountsStatsFragment.class);
    }

    private ListenerSavedDiscountsTab
            (int fragmentContainerId, FragmentActivity activity, String tag, Class<T> fragmentClass) {
        this.fragmentContainerId = fragmentContainerId;
        this.activity = activity;
        this.tag = tag;
        this.fragmentClass = fragmentClass;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        if (fragmentInstance == null) {
            fragmentInstance = Fragment.instantiate(activity, fragmentClass.getName());
            fragmentTransaction.add(fragmentContainerId, fragmentInstance, tag);
        } else {
            fragmentTransaction.attach(fragmentInstance);
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        if (fragmentInstance != null) {
            fragmentTransaction.detach(fragmentInstance);
        }
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }
}

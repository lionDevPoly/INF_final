package lion.favouritespots;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by opris_000 on 4/1/2016.
 */
public class MainWindowViewpagerAdapter extends FragmentStatePagerAdapter {

    private List<TabItem> tabItemList = new ArrayList<>();
    private Context curContext = null;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public MainWindowViewpagerAdapter(Context context, FragmentManager fm, List<TabItem> newTabItemList) {
        super(fm);

        tabItemList = newTabItemList;
        curContext = context;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        return tabItemList.get(position);
    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return tabItemList.get(position).getTitle(curContext);
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return tabItemList.size();
    }
}


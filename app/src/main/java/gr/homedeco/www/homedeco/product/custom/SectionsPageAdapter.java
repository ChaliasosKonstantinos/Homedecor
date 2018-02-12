package gr.homedeco.www.homedeco.product.custom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SectionsPageAdapter extends FragmentPagerAdapter{
    private final List<Fragment> myFragmentList = new ArrayList<>();
    private final List<String> myFragmentTitleList = new ArrayList<>();

    public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Adds a fragment and it's title to the list
     *
     * @param fragment the Fragment to be added to the list
     * @param title the title of the Fragment
     */
    public void addFragment(Fragment fragment, String title) {
        myFragmentList.add(fragment);
        myFragmentTitleList.add(title);
    }

    /**
     * Returns the title of a specific fragment from the list of fragments
     *
     * @param position the position of the fragment on the list
     *
     * @return the title of the fragment
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return myFragmentTitleList.get(position);
    }

    /**
     * Returns a specific fragment from the list of fragments
     *
     * @param position the position of the fragment on the list
     *
     * @return a Fragment object
     */
    @Override
    public Fragment getItem(int position) {
        return myFragmentList.get(position);
    }

    /**
     * Returns the number of fragments on the list
     *
     * @return the number of fragments on the list
     */
    @Override
    public int getCount() {
        return myFragmentList.size();
    }


}

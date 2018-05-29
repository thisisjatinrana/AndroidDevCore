package rana.jatin.core.widget.viewPager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/*
*  BaseFragmentPagerAdapter is a super-powered {@link android.support.v4.app.FragmentPagerAdapter FragmentPagerAdapter}
*  to be used with {@link  android.support.v4.view.ViewPager ViewPager}
*/

public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private Context context;

    public BaseFragmentPagerAdapter(Context mContext, FragmentManager manager) {
        super(manager);
        context = mContext;
    }

    /*
    * returns fragments at specified position
    */
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /*
    * returns total counts of fragments
    */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    /*
    * add fragment to viewpager
    * @param fragment to be pushed
    * @param title of the fragment
    */
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        notifyDataSetChanged();
    }

    /*
  * remove fragment at position
  * @param position of fragment to remove
  */
    public void removeFragment(int position) {
        mFragmentList.remove(position);
        mFragmentTitleList.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}


package com.example.sagoo.assingment_bigstep_umesh.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.sagoo.assingment_bigstep_umesh.Fragments.HistoryFragment;
import com.example.sagoo.assingment_bigstep_umesh.Fragments.MusicFragment;
import com.example.sagoo.assingment_bigstep_umesh.Fragments.VideoFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public  TabsAdapter(FragmentManager fm, int NoOfTabs){
        super(fm);
        this.mNumOfTabs = NoOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MusicFragment();
            case 1:
                return new VideoFragment();
            case 2:
                return new HistoryFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

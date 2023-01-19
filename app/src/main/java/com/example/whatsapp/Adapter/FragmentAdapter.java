package com.example.whatsapp.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsapp.Fragments.callFragment;
import com.example.whatsapp.Fragments.chatsFragment;
import com.example.whatsapp.Fragments.statusFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return  new chatsFragment();
            case 1: return  new statusFragment();
            case 2: return  new callFragment();
            default: return new chatsFragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title=null;
        if(position==0){
            title="CHATS";

        }
        if(position==1){
            title="STATUS";

        }
        if(position==2){
            title="CALLS";

        }
        return title;
    }
}

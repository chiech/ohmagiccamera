package com.irisstudio.demo;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import androidx.legacy.app.FragmentPagerAdapter;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;

public class StickerPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private ArrayList<CategoryRowInfo> dataList = null;
    private HashMap<String, StickerGridFragment> hashMap = new HashMap();

    public StickerPagerAdapter(Context context, FragmentManager fm, ArrayList<CategoryRowInfo> list) {
        super(fm);
        this.context = context;
        this.dataList = list;
    }

    public Fragment getItem(int position) {
        String categoryName = ((CategoryRowInfo) this.dataList.get(position)).getCATEGORY_NAME();
        StickerGridFragment stickerGridFragment = new StickerGridFragment();
        Bundle bundle = new Bundle();
        bundle.putString("categoryName", categoryName);
        bundle.putInt("totalItems", ((CategoryRowInfo) this.dataList.get(position)).getTOTAL_ITEMS());
        stickerGridFragment.setArguments(bundle);
        this.hashMap.put(categoryName, stickerGridFragment);
        Log.i("testing", "Not Contain : " + categoryName);
        return stickerGridFragment;
    }

    public CharSequence getPageTitle(int position) {
        return ((CategoryRowInfo) this.dataList.get(position)).getCATEGORY_NAME();
    }

    public int getCount() {
        return this.dataList.size();
    }
}

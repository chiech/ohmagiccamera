package com.irisstudio.demo.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.irisstudio.demo.HoriontalListAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mobo.andro.apps.ohmagiccamera.R;

public class TabHost extends HorizontalScrollView {
    private ContainerHost containerHost;
    private Context ctx;
    private OnTabClickListener listener;
    private LinearLayout llayout;
    private String selectedTabName = null;
    private ArrayList<String> tabNameList = new ArrayList();
    private Typeface ttf;

    public interface OnTabClickListener {
        void onTabClick(int i, String str);
    }

    public void setOnTabClickListener(OnTabClickListener l) {
        this.listener = l;
    }

    public TabHost(Context context) {
        super(context);
        intiTabHost(context);
    }

    public TabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        intiTabHost(context);
    }

    public TabHost(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intiTabHost(context);
    }

    private void intiTabHost(Context context) {
        this.ctx = context;
        setOverScrollMode(2);
        setHorizontalScrollBarEnabled(false);
        this.llayout = new LinearLayout(this.ctx);
        LayoutParams llp = new LayoutParams(-1, -1);
        addView(this.llayout);
    }

    public void attachContainerHost(ContainerHost ch) {
        this.containerHost = ch;
    }

    public ContainerHost getContainerHost() {
        return this.containerHost;
    }

    public void addTab(String tabName) {
        this.llayout.addView(createTabLayout(tabName));
        this.llayout.postInvalidate();
        this.llayout.requestLayout();
        this.tabNameList.add(tabName);
    }

    public void addTab(String tabName, HoriontalListAdapter adapter) {
        this.llayout.addView(createTabLayout(tabName));
        this.llayout.postInvalidate();
        this.llayout.requestLayout();
        if (this.containerHost != null) {
            this.containerHost.addAdapter(tabName, adapter);
        }
        this.tabNameList.add(tabName);
    }

    public void addTabAtIndex(String tabName, int index) {
        this.llayout.addView(createTabLayout(tabName), index);
        this.llayout.postInvalidate();
        this.llayout.requestLayout();
        this.tabNameList.add(index, tabName);
    }

    public void addTabAtIndex(String tabName, int index, HoriontalListAdapter adapter) {
        this.llayout.addView(createTabLayout(tabName), index);
        this.llayout.postInvalidate();
        this.llayout.requestLayout();
        if (this.containerHost != null) {
            this.containerHost.addAdapter(tabName, adapter);
        }
        this.tabNameList.add(index, tabName);
    }

    public void removeTabAtIndex(int index) {
        this.llayout.removeViewAt(index);
        this.llayout.postInvalidate();
        this.llayout.requestLayout();
        if (this.containerHost != null) {
            this.containerHost.removeAdapter((String) this.tabNameList.get(index));
        }
        this.tabNameList.remove(index);
        if (!this.tabNameList.contains(this.selectedTabName)) {
            this.selectedTabName = null;
        }
    }

    public void removeAllTabs() {
        while (this.tabNameList.size() > 0) {
            removeTabAtIndex(0);
        }
    }

    public void removeTabsAtIndex(int[] indexs) {
        Arrays.sort(indexs);
        for (int i = indexs.length - 1; i >= 0; i--) {
            this.llayout.removeViewAt(indexs[i]);
            if (this.containerHost != null) {
                this.containerHost.removeAdapter((String) this.tabNameList.get(indexs[i]));
            }
            this.tabNameList.remove(indexs[i]);
        }
        if (!this.tabNameList.contains(this.selectedTabName)) {
            this.selectedTabName = null;
        }
        this.llayout.postInvalidate();
        this.llayout.requestLayout();
    }

    public String getSelectedTabName() {
        return this.selectedTabName;
    }

    public List<String> getTabsNameList() {
        return this.tabNameList;
    }

    public int getTabsCount() {
        return this.tabNameList.size();
    }

    public void setTabSelected(int pos) {
        int childCount = this.llayout.getChildCount();
        if (childCount != 0 && pos >= 0 && pos < childCount) {
            for (int i = 0; i < childCount; i++) {
                this.llayout.getChildAt(i).setBackgroundResource(R.drawable.sticker_border_inact);
            }
            this.llayout.getChildAt(pos).setBackgroundResource(R.drawable.sticker_border_act);
            this.selectedTabName = (String) this.tabNameList.get(pos);
            if (this.containerHost != null) {
                this.containerHost.changeAdapter((String) this.tabNameList.get(pos));
            }
        }
    }

    public boolean containTab(String tabName) {
        return this.tabNameList.contains(tabName);
    }

    public void setTabSelected(String tabName) {
        if (tabName.contains(tabName)) {
            setTabSelected(this.tabNameList.indexOf(tabName));
        }
    }

    private RelativeLayout createTabLayout(final String tabName) {
        RelativeLayout view = (RelativeLayout) inflate(this.ctx, R.layout.tab_item, null);
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                int position = ((LinearLayout) v.getParent()).indexOfChild(v);
                if (TabHost.this.listener != null) {
                    TabHost.this.listener.onTabClick(position, tabName);
                }
                TabHost.this.setTabSelected(position);
            }
        });
        ((TextView) view.findViewById(R.id.tab_item_txt)).setText(tabName);
        ((TextView) view.findViewById(R.id.tab_item_txt)).setTypeface(this.ttf);
        return view;
    }
}

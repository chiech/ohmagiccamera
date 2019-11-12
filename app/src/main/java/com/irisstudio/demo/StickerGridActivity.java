package com.irisstudio.demo;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager.widget.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.astuetz.PagerSlidingTabStrip;
import com.irisstudio.demo.NetworkQueryHelper.ResponseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mobo.andro.apps.ohmagiccamera.R;

public class StickerGridActivity extends Activity {
    private static final String MyPREFERENCES = "MyPrefs";
    protected static boolean isRunning = false;
    protected static Context mContext = null;
    private StickerPagerAdapter adapter = null;
    private int categoriesCount = 0;
    private int curVer;
    private int dbVer;
    private TextView headerText;
    private ViewPager pager;
    private ProgressDialog ringProgressDialog;
    SharedPreferences sharedpreferences;
    private PagerSlidingTabStrip tabHost;
    private Typeface ttf;

    /* renamed from: com.irisstudio.demo.StickerGridActivity$1 */
    class C14621 implements OnPageChangeListener {
        C14621() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        public void onPageSelected(int position) {
            if (StickerGridActivity.this.adapter != null) {
                StickerGridActivity.this.headerText.setText(StickerGridActivity.this.adapter.getPageTitle(position));
            }
        }

        public void onPageScrollStateChanged(int state) {
        }
    }

    /* renamed from: com.irisstudio.demo.StickerGridActivity$2 */
    class C14632 implements OnClickListener {
        C14632() {
        }

        public void onClick(View v) {
            StickerGridActivity.this.finish();
            StickerGridActivity.this.overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
        }
    }

    /* renamed from: com.irisstudio.demo.StickerGridActivity$3 */
    class C14643 implements ResponseListener {
        C14643() {
        }

        public void onResponse(JSONObject response) {
            if (response != null) {
                try {
                    StickerGridActivity.this.dbVer = response.getInt("dbVersion");
                    StickerGridActivity.this.categoriesCount = response.getInt("categoryCount");
                    if (StickerGridActivity.this.curVer != StickerGridActivity.this.dbVer) {
                        StickerGridActivity.this.fetchAndInsertCategory();
                        return;
                    }
                    ArrayList<CategoryRowInfo> categoriesList = DatabaseHandler.getDbHandler(StickerGridActivity.this).getCategoriesList();
                    StickerGridActivity.this.intiCategoryList(categoriesList);
                    Log.i("testing", categoriesList.size() + "  " + categoriesList.toString());
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    StickerGridActivity.this.showNetworkErrorDialog();
                    return;
                }
            }
            StickerGridActivity.this.showNetworkErrorDialog();
        }
    }

    /* renamed from: com.irisstudio.demo.StickerGridActivity$4 */
    class C14654 implements ResponseListener {
        C14654() {
        }

        public void onResponse(JSONObject responseObj) {
            if (responseObj != null) {
                try {
                    JSONArray dataArr = responseObj.getJSONArray("data");
                    Log.i("testing", dataArr + " Server Categories Data " + dataArr.toString());
                    DatabaseHandler dh = DatabaseHandler.getDbHandler(StickerGridActivity.this);
                    dh.disableAllRow();
                    ArrayList<CategoryRowInfo> categoriesList = dh.getCategoriesList();
                    Log.i("testing", categoriesList + " Local Categories Data " + categoriesList.toString());
                    Iterator it = StickerGridActivity.this.getNewCategoryList(dataArr, categoriesList, dh).iterator();
                    while (it.hasNext()) {
                        dh.insertCategoryMasterRow((CategoryRowInfo) it.next());
                    }
                    Editor edit = StickerGridActivity.this.sharedpreferences.edit();
                    edit.putInt("DataVersion", StickerGridActivity.this.dbVer);
                    edit.commit();
                    categoriesList = dh.getCategoriesList();
                    StickerGridActivity.this.intiCategoryList(categoriesList);
                    Log.i("testing", categoriesList.size() + " and " + categoriesList.toString());
                    return;
                } catch (JSONException e) {
                    e.printStackTrace();
                    StickerGridActivity.this.showNetworkErrorDialog();
                    return;
                }
            }
            StickerGridActivity.this.showNetworkErrorDialog();
        }
    }

    /* renamed from: com.irisstudio.demo.StickerGridActivity$5 */
    class C14665 implements Comparator<CategoryRowInfo> {
        C14665() {
        }

        public int compare(CategoryRowInfo o1, CategoryRowInfo o2) {
            if (o1.getSEQUENCE() == o2.getSEQUENCE()) {
                return 0;
            }
            if (o1.getSEQUENCE() > o2.getSEQUENCE()) {
                return 1;
            }
            return -1;
        }
    }

    private class SelectionAsync extends AsyncTask<Object, Object, Integer> {
        ArrayList<CategoryRowInfo> categoriesList;
        String tabName = "";

        public SelectionAsync(ArrayList<CategoryRowInfo> categoriesLis) {
            this.categoriesList = categoriesLis;
        }

        protected Integer doInBackground(Object... params) {
            try {
                this.tabName = StickerGridActivity.this.getIntent().getStringExtra("tabName");
                Log.i("testing", "" + this.tabName);
                if (this.tabName.trim().length() > 0) {
                    for (int i = 0; i < this.categoriesList.size(); i++) {
                        if (((CategoryRowInfo) this.categoriesList.get(i)).getCATEGORY_NAME().equals(this.tabName)) {
                            return Integer.valueOf(i);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Integer.valueOf(-1);
        }

        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            if (integer.intValue() >= 0) {
                StickerGridActivity.this.headerText.setText(((CategoryRowInfo) this.categoriesList.get(0)).getCATEGORY_NAME());
                StickerGridActivity.this.pager.setCurrentItem(integer.intValue());
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_list);
        mContext = this;
        isRunning = true;
        this.sharedpreferences = getSharedPreferences(MyPREFERENCES, 0);
        initUI();
        this.ringProgressDialog = ProgressDialog.show(this, "", getResources().getString(R.string.please_wait) + "...", true);
        this.ringProgressDialog.setCancelable(false);
        if (isNetworkAvailable()) {
            checkDataVersionChanges();
        } else {
            ArrayList<CategoryRowInfo> categoriesList = DatabaseHandler.getDbHandler(this).getCategoriesList();
            if (categoriesList.size() == 0) {
                showNetworkErrorDialog();
            } else {
                intiCategoryList(categoriesList);
            }
        }
        this.ttf = Typeface.createFromAsset(getAssets(), "Roboto-Medium.ttf");
        this.headerText.setTypeface(this.ttf);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    private void initUI() {
        this.headerText = (TextView) findViewById(R.id.headertext);
        this.tabHost = (PagerSlidingTabStrip) findViewById(R.id.tabHost);
        this.pager = (ViewPager) findViewById(R.id.pager);
        this.tabHost.setTextColor(-1);
        this.tabHost.setDividerColor(Color.parseColor("#620b80"));
        this.tabHost.setIndicatorColor(Color.parseColor("#620b80"));
        this.tabHost.setIndicatorHeight(dpToPx(this, 5));
        this.tabHost.setOnPageChangeListener(new C14621());
        findViewById(R.id.btn_back).setOnClickListener(new C14632());
    }

    private void checkDataVersionChanges() {
        this.curVer = this.sharedpreferences.getInt("DataVersion", 0);
        NetworkQueryHelper.getInstance().getServerDbVersion(new C14643());
    }

    private void fetchAndInsertCategory() {
        NetworkQueryHelper.getInstance().getCategoriesData(this.categoriesCount, new C14654());
    }

    private void intiCategoryList(ArrayList<CategoryRowInfo> categoriesList) {
        Collections.sort(categoriesList, new C14665());
        this.adapter = new StickerPagerAdapter(this, getFragmentManager(), categoriesList);
        this.pager.setAdapter(this.adapter);
        this.tabHost.setViewPager(this.pager);
        if (categoriesList.size() > 0) {
            this.headerText.setText(((CategoryRowInfo) categoriesList.get(0)).getCATEGORY_NAME());
            new SelectionAsync(categoriesList).execute(new Object[0]);
        }
        if (this.ringProgressDialog.isShowing()) {
            this.ringProgressDialog.dismiss();
        }
    }

    private ArrayList<CategoryRowInfo> getNewCategoryList(JSONArray jsonArr, ArrayList<CategoryRowInfo> categoriesList, DatabaseHandler dh) {
        ArrayList<CategoryRowInfo> newCategoryList = new ArrayList();
        ArrayList<String> oldCategoryList = new ArrayList();
        String catName = "";
        Iterator it = categoriesList.iterator();
        while (it.hasNext()) {
            oldCategoryList.add(((CategoryRowInfo) it.next()).getCATEGORY_NAME());
        }
        for (int i = 0; i < jsonArr.length(); i++) {
            try {
                JSONObject obj = (JSONObject) jsonArr.get(i);
                catName = obj.getString("Category_Name");
                if (oldCategoryList.contains(catName)) {
                    dh.updateCategoryRow(catName, obj.getInt("Sequence"), obj.getInt("Total_Items"));
                    oldCategoryList.remove(catName);
                } else {
                    newCategoryList.add(new CategoryRowInfo(catName, obj.getInt("Sequence"), obj.getInt("Total_Items")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        it = oldCategoryList.iterator();
        while (it.hasNext()) {
            dh.deleteCategoryMasterRow((String) it.next());
        }
        return newCategoryList;
    }

    private void showNetworkErrorDialog() {
        if (this.ringProgressDialog.isShowing()) {
            this.ringProgressDialog.dismiss();
        }
        if (isRunning) {
            int style;
            final Activity activity = this;
            if (VERSION.SDK_INT >= 14) {
                style = 16974126;
            } else {
                style = 16973835;
            }
            new Builder(mContext, style).setTitle(mContext.getResources().getString(R.string.error)).setMessage(mContext.getResources().getString(R.string.something_wrong)).setCancelable(false).setPositiveButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    activity.finish();
                }
            }).create().show();
        }
    }

    public boolean isNetworkAvailable() {
        NetworkInfo netInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    protected void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }

    private int dpToPx(Context c, int dp) {
        float f = (float) dp;
        c.getResources();
        return (int) (f * Resources.getSystem().getDisplayMetrics().density);
    }
}

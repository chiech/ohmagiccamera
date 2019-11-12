package com.irisstudio.demo.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

import com.irisstudio.demo.DatabaseHandler;
import com.irisstudio.demo.HoriontalListAdapter;
import com.irisstudio.demo.LibContants;
import com.irisstudio.demo.StickerInfo;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.HashMap;

import mobo.andro.apps.ohmagiccamera.R;

public class ContainerHost extends RelativeLayout {
    private String actAdapter = "";
    private HashMap<String, HoriontalListAdapter> adapterHashMap = new HashMap();
    private Context ctx;
    private HorizontalListView horizontalListView;
    private OnItemClickListener itemClickListener = null;

    /* renamed from: com.irisstudio.demo.view.ContainerHost$1 */
    class C14691 implements android.widget.AdapterView.OnItemClickListener {
        C14691() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (ContainerHost.this.itemClickListener != null) {
                Uri uri = Uri.parse(((StickerInfo) ContainerHost.this.getActiveAdapter().getItem(position)).getIMAGE_PATH());
                if ("android.resource".equals(uri.getScheme())) {
                    ContainerHost.this.itemClickListener.onItemClick(((StickerInfo) ContainerHost.this.getActiveAdapter().getItem(position)).getIMAGE_PATH());
                } else if (new File(uri.getPath()).exists()) {
                    ContainerHost.this.itemClickListener.onItemClick(((StickerInfo) ContainerHost.this.getActiveAdapter().getItem(position)).getIMAGE_PATH());
                } else {
                    Log.i("testing", "Starting AsyncTask");
                    new SaveAndLoadAsync(position, (StickerInfo) ContainerHost.this.getActiveAdapter().getItem(position)).execute(new Object[0]);
                }
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(String str);
    }

    private class SaveAndLoadAsync extends AsyncTask<Object, Void, Boolean> {
        private ProgressDialog pdia;
        private int position;
        private StickerInfo stickerInfo;



        public SaveAndLoadAsync(int pos, StickerInfo info) {
            this.position = pos;
            this.stickerInfo = info;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.pdia = new ProgressDialog(ContainerHost.this.ctx);
            this.pdia.setMessage(ContainerHost.this.ctx.getResources().getString(R.string.downloadin_file));
            this.pdia.setCancelable(false);
            this.pdia.show();
        }

        protected Boolean doInBackground(Object... params) {
            try {
                String imagePath = saveBitmapObject(BitmapFactory.decodeStream(new URL(this.stickerInfo.getIMAGE_SERVER_PATH()).openStream()), this.stickerInfo.getSTICKER_NAME());
                DatabaseHandler dbHanlder = DatabaseHandler.getDbHandler(ContainerHost.this.ctx);
                dbHanlder.updateStickerImagePath(this.stickerInfo.getSTICKER_ID(), imagePath, true);
                dbHanlder.close();
                this.stickerInfo.setIMAGE_PATH(imagePath);
                return Boolean.valueOf(true);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
                return Boolean.valueOf(false);
            }
        }

        protected void onPostExecute(Boolean isDownloaded) {
            super.onPostExecute(isDownloaded);
            this.pdia.dismiss();
            if (isDownloaded.booleanValue()) {
                Log.i("testing", "Sticker Saved to : " + this.stickerInfo.getIMAGE_PATH());
                ((StickerInfo) ContainerHost.this.getActiveAdapter().getItem(this.position)).setIMAGE_PATH(this.stickerInfo.getIMAGE_PATH());
                ContainerHost.this.itemClickListener.onItemClick(this.stickerInfo.getIMAGE_PATH());
                return;
            }
        }

        private String saveBitmapObject(Bitmap bitmap, String fname) {
            File myDir = LibContants.getSaveFileLocation();
            myDir.mkdirs();
            File file = new File(myDir, fname + ".png");
            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream ostream = new FileOutputStream(file);
                bitmap.compress(CompressFormat.PNG, 100, ostream);
                ostream.close();
                return file.getPath();
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("testing", "Exception" + e.getMessage());
                return null;
            }
        }
    }

    public void setItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public ContainerHost(Context context) {
        super(context);
        initContainerHost(context);
    }

    public ContainerHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        initContainerHost(context);
    }

    public ContainerHost(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initContainerHost(context);
    }

    public void initContainerHost(Context context) {
        this.ctx = context;
        this.horizontalListView = new HorizontalListView(context);
        this.horizontalListView.setLayoutParams(new LayoutParams(-1, -1));
        addView(this.horizontalListView);
        this.horizontalListView.setOnItemClickListener(new C14691());
    }

    public void addAdapter(String name, HoriontalListAdapter horiontalListAdapter) {
        this.adapterHashMap.put(name, horiontalListAdapter);
    }

    public void removeAdapter(String name) {
        if (this.adapterHashMap.containsKey(name)) {
            this.adapterHashMap.remove(name);
        }
        if (!this.adapterHashMap.containsKey(this.actAdapter)) {
            this.actAdapter = "";
        }
    }

    public void changeAdapter(String name) {
        if (this.adapterHashMap.containsKey(name)) {
            this.horizontalListView.setVisibility(0);
            this.horizontalListView.setAdapter((ListAdapter) this.adapterHashMap.get(name));
        } else {
            this.horizontalListView.setVisibility(8);
        }
        if (this.adapterHashMap.containsKey(name)) {
            this.actAdapter = name;
        } else {
            this.actAdapter = "";
        }
    }

    public HoriontalListAdapter getActiveAdapter() {
        if (this.adapterHashMap.containsKey(this.actAdapter)) {
            return (HoriontalListAdapter) this.adapterHashMap.get(this.actAdapter);
        }
        return null;
    }

    public HoriontalListAdapter getAdapter(String tabName) {
        if (this.adapterHashMap.containsKey(tabName)) {
            return (HoriontalListAdapter) this.adapterHashMap.get(tabName);
        }
        return null;
    }
}

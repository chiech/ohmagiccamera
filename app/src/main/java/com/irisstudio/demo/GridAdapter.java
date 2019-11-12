package com.irisstudio.demo;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.irisstudio.demo.view.SquareFrameLayoutGrid;
import com.irisstudio.demo.view.SquareImageViewGrid;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;

import mobo.andro.apps.ohmagiccamera.R;

public class GridAdapter extends ArrayAdapter<StickerInfo> {
    public static int num = 0;
    Context context;

    private class SaveAndLoadAsync extends AsyncTask<Object, Void, Boolean> {
        private int position;
        private StickerInfo stickerInfo;

        /* renamed from: com.irisstudio.demo.GridAdapter$SaveAndLoadAsync$1 */
        class C14551 implements OnClickListener {
            C14551() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }

        public SaveAndLoadAsync(int pos) {
            this.position = pos;
            this.stickerInfo = (StickerInfo) GridAdapter.this.getItem(pos);
        }

        protected Boolean doInBackground(Object... params) {
            try {
                String imagePath = saveBitmapObject(BitmapFactory.decodeStream(new URL(this.stickerInfo.getIMAGE_SERVER_PATH()).openStream()), this.stickerInfo.getSTICKER_NAME());
                DatabaseHandler dbHanlder = DatabaseHandler.getDbHandler(GridAdapter.this.context);
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
            if (isDownloaded.booleanValue()) {
                Log.i("testing", "Sticker Saved to : " + this.stickerInfo.getIMAGE_PATH());
                ((StickerInfo) GridAdapter.this.getItem(this.position)).setIS_DOWNLOADED("true");
            } else if (StickerGridActivity.isRunning) {
                int style;
                if (VERSION.SDK_INT >= 14) {
                    style = 16974126;
                } else {
                    style = 16973835;
                }
                new Builder(StickerGridActivity.mContext, style).setTitle(StickerGridActivity.mContext.getResources().getString(R.string.no_internet)).setMessage(StickerGridActivity.mContext.getResources().getString(R.string.file_not_downloaded)).setCancelable(false).setPositiveButton(StickerGridActivity.mContext.getResources().getString(R.string.ok), new C14551()).create().show();
            }
            ((StickerInfo) GridAdapter.this.getItem(this.position)).setIS_DOWNLOADING(false);
            GridAdapter.this.notifyDataSetChanged();
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

    class ViewHolder {
        ImageView download;
        SquareImageViewGrid mThumbnail;
        int position;
        SquareFrameLayoutGrid root;
        private StickerInfo stickerInfo;
        Uri uri;

        /* renamed from: com.irisstudio.demo.GridAdapter$ViewHolder$1 */
        class C14561 implements View.OnClickListener {
            C14561() {
            }

            public void onClick(View v) {
                Log.i("testing", "convertView one " + ViewHolder.this.uri.getLastPathSegment());
                ViewHolder.this.download.setBackgroundResource(0);
       //         Glide.with(GridAdapter.this.context).load(Integer.valueOf(R.drawable.sticker_downloading)).asGif().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(ViewHolder.this.download);
                ViewHolder.this.stickerInfo.setIS_DOWNLOADING(true);
                ViewHolder.this.download.setOnClickListener(null);
                new SaveAndLoadAsync(ViewHolder.this.position).execute(new Object[0]);
            }
        }

        /* renamed from: com.irisstudio.demo.GridAdapter$ViewHolder$2 */
        class C14572 implements View.OnClickListener {
            C14572() {
            }

            public void onClick(View v) {
                Log.i("testing", "Downloaded " + ViewHolder.this.stickerInfo.getIMAGE_PATH());
                Toast.makeText(GridAdapter.this.context, GridAdapter.this.context.getResources().getString(R.string.deleted), 0).show();
                DatabaseHandler dbHanlder = DatabaseHandler.getDbHandler(GridAdapter.this.context);
                dbHanlder.updateStickerImagePath(ViewHolder.this.stickerInfo.getSTICKER_ID(), "", false);
                dbHanlder.close();
                File file = new File(ViewHolder.this.stickerInfo.getIMAGE_PATH());
                if (!file.exists()) {
                    ((StickerInfo) GridAdapter.this.getItem(ViewHolder.this.position)).setIS_DOWNLOADED("false");
                    GridAdapter.this.notifyDataSetChanged();
                } else if (file.delete()) {
                    ((StickerInfo) GridAdapter.this.getItem(ViewHolder.this.position)).setIS_DOWNLOADED("false");
                    GridAdapter.this.notifyDataSetChanged();
                }
            }
        }

        public ViewHolder(View view) {
            this.root = (SquareFrameLayoutGrid) view.findViewById(R.id.root);
            this.mThumbnail = (SquareImageViewGrid) view.findViewById(R.id.thumbnail_image);
            this.download = (ImageView) view.findViewById(R.id.download);
        }

        public void refreshImage() {
//            Glide.with(GridAdapter.this.context).load(this.stickerInfo.getTHUMB_SERVER_PATH()).thumbnail(0.5f).centerCrop().placeholder(R.drawable.sticker_loading).error(R.drawable.sticker_no_image).into(this.mThumbnail);
            if (this.stickerInfo.isIS_DOWNLOADING()) {
  //              Glide.with(GridAdapter.this.context).load(Integer.valueOf(R.drawable.sticker_downloading)).asGif().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(this.download);
                this.download.setOnClickListener(null);
            } else if (this.stickerInfo.IS_DOWNLOADED().equals("false")) {
//                Glide.with(GridAdapter.this.context).load(Integer.valueOf(R.drawable.sticker_downloading)).asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(this.download);
                this.download.setOnClickListener(new C14561());
            } else {
  //              Glide.with(GridAdapter.this.context).load(Integer.valueOf(R.drawable.sticker_delete)).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(this.download);
                this.download.setOnClickListener(new C14572());
            }
        }

        public void setStickerInfo(int pos) {
            this.position = pos;
            this.stickerInfo = (StickerInfo) GridAdapter.this.getItem(pos);
        }
    }

    public GridAdapter(Context context, List<StickerInfo> images) {
        super(context, 0, images);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        StickerInfo stickerInfo = (StickerInfo) getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.gridview_item, null);
            holder = new ViewHolder(convertView);
            holder.setStickerInfo(position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.setStickerInfo(position);
        }
        holder.uri = Uri.parse(((StickerInfo) getItem(position)).getTHUMB_PATH());
        holder.refreshImage();
        return convertView;
    }
}

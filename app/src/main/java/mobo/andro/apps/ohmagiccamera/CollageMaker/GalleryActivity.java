package mobo.andro.apps.ohmagiccamera.CollageMaker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import mobo.andro.apps.ohmagiccamera.CustomAds;
import mobo.andro.apps.ohmagiccamera.R;

public class GalleryActivity extends Activity {
    String[] extensions = new String[]{"jpg", "jpeg", "JPG", "JPEG", ".png", ",PNG"};
    private List<Uri> images;
    private ImageGalleryAdapter mGalleryAdapter;
    InterstitialAd mInterstitialAd;
    private Typeface ttf;

    class lis1 implements OnItemClickListener {
        lis1() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent gallActivity = new Intent(GalleryActivity.this, ShareImageActivity.class);
            gallActivity.putExtra("uri", ((Uri) GalleryActivity.this.images.get(i)).getPath());
            gallActivity.putExtra("fromGallery", true);
            GalleryActivity.this.startActivity(gallActivity);
//            if (GalleryActivity.this.mInterstitialAd.isLoaded()) {
//                GalleryActivity.this.mInterstitialAd.show();
//            }
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.GalleryActivity$3 */
    class lis2 implements OnItemLongClickListener {
        lis2() {
        }

        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            GalleryActivity.this.showopt((Uri) GalleryActivity.this.images.get(position));
            return true;
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.GalleryActivity$4 */
    class lis3 implements OnClickListener {
        lis3() {
        }

        public void onClick(View view) {
            GalleryActivity.this.finish();
        }
    }

    public class ImageGalleryAdapter extends ArrayAdapter<Uri> {
        Context context;

        public ImageGalleryAdapter(Context context, List<Uri> images) {
            super(context, 0, images);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.picker_grid_item_gallery_thumbnail, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Uri mUri = (Uri) getItem(position);
            Glide.with(this.context).load(mUri.toString()).apply(new RequestOptions().centerCrop()).thumbnail(0.1f).into(holder.mThumbnail);
            holder.uri = mUri;
            return convertView;
        }
    }

    class ViewHolder {
        ImageView mThumbnail;
        CustomSquareFrameLayout root;
        Uri uri;

        public ViewHolder(View view) {
            this.root = (CustomSquareFrameLayout) view.findViewById(R.id.root);
            this.mThumbnail = (ImageView) view.findViewById(R.id.thumbnail_image);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_gallery);

        LinearLayout adContainer=(LinearLayout) findViewById(R.id.adContainer);
        CustomAds.facebookAdBanner(GalleryActivity.this,adContainer);

        GridView galleryGridView = (GridView) findViewById(R.id.gallery_grid);
        this.images = loadAllImages(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString());
        this.mGalleryAdapter = new ImageGalleryAdapter(this, this.images);
        galleryGridView.setAdapter(this.mGalleryAdapter);
        galleryGridView.setOnItemClickListener(new lis1());
        galleryGridView.setOnItemLongClickListener(new lis2());
        findViewById(R.id.btn_back).setOnClickListener(new lis3());
    }

    private void showopt(final Uri uri) {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().requestFeature(1);
        dialog.setContentView(R.layout.options_dialog);
        TextView open = (TextView) dialog.findViewById(R.id.open);
        open.setTypeface(this.ttf);
        open.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(GalleryActivity.this, ShareImageActivity.class);
                intent.putExtra("uri", uri.getPath());
                intent.putExtra("fromGallery", true);
                GalleryActivity.this.startActivity(intent);
                dialog.dismiss();
                if (GalleryActivity.this.mInterstitialAd.isLoaded()) {
                    GalleryActivity.this.mInterstitialAd.show();
                }

            }
        });
        TextView delete = (TextView) dialog.findViewById(R.id.delete);
        delete.setTypeface(this.ttf);
        delete.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (GalleryActivity.this.del(uri)) {
                    GalleryActivity.this.mGalleryAdapter.remove(uri);
                    GalleryActivity.this.mGalleryAdapter.notifyDataSetChanged();
                    if (GalleryActivity.this.images.size() == 0) {
                        GalleryActivity.this.findViewById(R.id.txt_nopics).setVisibility(View.VISIBLE);
                    } else {
                        GalleryActivity.this.findViewById(R.id.txt_nopics).setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(GalleryActivity.this, GalleryActivity.this.getResources().getString(R.string.del_error_toast), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
        cancel.setTypeface(this.ttf);
        cancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private boolean del(Uri uri) {
        File file = new File(uri.getPath());
        if (!file.exists()) {
            return false;
        }
        boolean delete = file.delete();
        sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
        Toast.makeText(this, getResources().getString(R.string.deleted), Toast.LENGTH_SHORT).show();
        return delete;
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private List<Uri> loadAllImages(String rootFolder) {
        int i;
        HashMap<Long, Uri> hashMap = new HashMap();
        File file = new File(rootFolder, "/" + getResources().getString(R.string.folder_name));
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    if (!f.isDirectory()) {
                        for (String endsWith : this.extensions) {
                            if (f.getAbsolutePath().endsWith(endsWith)) {
                                hashMap.put(Long.valueOf(f.lastModified()), Uri.fromFile(f));
                            }
                        }
                    }
                }
            }
        }
        if (hashMap.size() == 0) {
            return new ArrayList();
        }
        findViewById(R.id.txt_nopics).setVisibility(View.GONE);
        List sortedKeys = new ArrayList(hashMap.keySet());
        Collections.sort(sortedKeys);
        int len = sortedKeys.size();
        List<Uri> images = new ArrayList();
        for (i = len - 1; i >= 0; i--) {
            images.add(hashMap.get(sortedKeys.get(i)));
        }
        return images;
    }

//    private boolean isNetworkAvailable() {
//        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }
}

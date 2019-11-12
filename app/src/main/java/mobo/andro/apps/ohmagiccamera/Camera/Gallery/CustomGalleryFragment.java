package mobo.andro.apps.ohmagiccamera.Camera.Gallery;

import android.app.AlertDialog.Builder;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import mobo.andro.apps.ohmagiccamera.R;

public class CustomGalleryFragment extends Fragment {
    private List<Uri> allImagesList = null;
    private ImageView btn_close;
    private ImageButton btn_ok;
    private RecyclerView folder_recyclerview;
    private GalleryRecyclerAdapter galleryRecyclerAdapter;
    private HashMap<String, List<Uri>> hashMap = new HashMap();
    private ArrayList<Integer> sizesFolder=new ArrayList<Integer>();
    private TextView headertext;
    private GridView imagesGridView;
    private ImageView img_arrow;
    private ImageGalleryActivity mGalleryActivity;
    private ImageGalleryAdapter mImagesAdapter = null;
    private ProgressBar progressBar;

    class C03361 implements OnClickListener {
        C03361() {
        }

        public void onClick(View view) {
            float f = 0.0f;
            if (CustomGalleryFragment.this.folder_recyclerview.getVisibility() == View.VISIBLE) {
                CustomGalleryFragment.this.folder_recyclerview.setVisibility(View.GONE);
            } else {
                CustomGalleryFragment.this.folder_recyclerview.setVisibility(View.VISIBLE);
            }
            ImageView access$100 = CustomGalleryFragment.this.img_arrow;
            if (CustomGalleryFragment.this.img_arrow.getRotationX() == 0.0f) {
                f = -180.0f;
            }
            access$100.setRotationX(f);
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.CustomGalleryFragment$2 */
    class C03372 implements OnItemClickListener {
        C03372() {
        }
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (adapterView.getAdapter() instanceof ImageGalleryAdapter) {

                Toast.makeText(mGalleryActivity, " selected image : "+(Uri) ((ImageGalleryAdapter) adapterView.getAdapter()).getItem(i), Toast.LENGTH_SHORT).show();
                Log.e("selected"," : "+(Uri) ((ImageGalleryAdapter) adapterView.getAdapter()).getItem(i));
                CustomGalleryFragment.this.mGalleryActivity.addImage((Uri) ((ImageGalleryAdapter) adapterView.getAdapter()).getItem(i));
            }
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.CustomGalleryFragment$3 */
    class C03383 implements OnClickListener {
        C03383() {
        }

        public void onClick(View view) {
            float f = 0.0f;
            if (CustomGalleryFragment.this.folder_recyclerview.getVisibility() == View.VISIBLE) {
                CustomGalleryFragment.this.folder_recyclerview.setVisibility(View.GONE);
                ImageView access$100 = CustomGalleryFragment.this.img_arrow;
                if (CustomGalleryFragment.this.img_arrow.getRotationX() == 0.0f) {
                    f = -180.0f;
                }
                access$100.setRotationX(f);
                return;
            }
            CustomGalleryFragment.this.getActivity().finish();
        }
    }

    /* renamed from: mobo.andro.apps.camera.CollageMaker.CustomGalleryFragment$4 */
    class C03394 implements OnClickListener {
        C03394() {
        }

        public void onClick(View view) {
        }
    }

    public class LoadGalleryAsync extends AsyncTask<Void, Object, Boolean> {

        /* renamed from: mobo.andro.apps.camera.CollageMaker.CustomGalleryFragment$LoadGalleryAsync$1 */
        class C03401 implements DialogInterface.OnClickListener {
            C03401() {
            }

           public void onClick(DialogInterface dialog, int which) {
       //        Intent intent = new Intent(CustomGalleryFragment.this.getActivity(), MainActivity.class);
       //        intent.addFlags(536870912);
       //        intent.addFlags(67108864);
       //        CustomGalleryFragment.this.startActivity(intent);
       //        CustomGalleryFragment.this.getActivity().finish();
            }
        }

        /* renamed from: mobo.andro.apps.camera.CollageMaker.CustomGalleryFragment$LoadGalleryAsync$2 */
        class C03412 implements GalleryRecyclerAdapter.OnItemClickListener {
            C03412() {
            }

            public void onImageClick(int pos, String folName) {
                float f;
                CustomGalleryFragment.this.galleryRecyclerAdapter.setSelected(pos);
                CustomGalleryFragment.this.mImagesAdapter = new ImageGalleryAdapter(CustomGalleryFragment.this.getActivity(), (List) CustomGalleryFragment.this.hashMap.get(folName));
                CustomGalleryFragment.this.imagesGridView.setAdapter(CustomGalleryFragment.this.mImagesAdapter);
                CustomGalleryFragment.this.headertext.setText(folName);
                CustomGalleryFragment.this.folder_recyclerview.setVisibility(View.GONE);
                ImageView access$100 = CustomGalleryFragment.this.img_arrow;
                if (CustomGalleryFragment.this.img_arrow.getRotationX() == 0.0f) {
                    f = -180.0f;
                } else {
                    f = 0.0f;
                }
                access$100.setRotationX(f);
            }
        }

        protected void onPreExecute() {
            super.onPreExecute();
            CustomGalleryFragment.this.progressBar.setVisibility(View.VISIBLE);
        }

        protected Boolean doInBackground(Void... uris) {
            CustomGalleryFragment.this.allImagesList = CustomGalleryFragment.this.getImagesData(CustomGalleryFragment.this.getActivity());
            return Boolean.valueOf(true);
        }

        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (CustomGalleryFragment.this.allImagesList.size() == 0) {
                int style;
                if (VERSION.SDK_INT >= 14) {
                    style = 16974126;
                } else {
                    style = 16973835;
                }
                new Builder(CustomGalleryFragment.this.getActivity(), style).setTitle(CustomGalleryFragment.this.getResources().getString(R.string.err_title)).setIcon(R.mipmap.ic_launcher).setMessage(CustomGalleryFragment.this.getResources().getString(R.string.err_msg1) + " " + CustomGalleryFragment.this.getResources().getString(R.string.err_msg2)).setPositiveButton(CustomGalleryFragment.this.getResources().getString(R.string.ok), new C03401()).create().show();
            } else {


                CustomGalleryFragment.this.mImagesAdapter = new ImageGalleryAdapter(CustomGalleryFragment.this.getActivity(), CustomGalleryFragment.this.allImagesList);
                CustomGalleryFragment.this.imagesGridView.setAdapter(CustomGalleryFragment.this.mImagesAdapter);
                if (CustomGalleryFragment.this.hashMap.size() != 0)
                {
                    CustomGalleryFragment.this.galleryRecyclerAdapter = new GalleryRecyclerAdapter(CustomGalleryFragment.this.getActivity(), CustomGalleryFragment.this.hashMap);
                    CustomGalleryFragment.this.folder_recyclerview.setAdapter(CustomGalleryFragment.this.galleryRecyclerAdapter);
                    CustomGalleryFragment.this.galleryRecyclerAdapter.setOnItemClickListner(new C03412());
                    CustomGalleryFragment.this.galleryRecyclerAdapter.setSelected(0);
                }
            }
            CustomGalleryFragment.this.progressBar.setVisibility(View.GONE);
        }
    }

    public static CustomGalleryFragment newInstance() {
        CustomGalleryFragment fragment = new CustomGalleryFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() == null) {
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.camera_fragment_custom_gallery, container, false);
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.img_arrow = (ImageView) view.findViewById(R.id.img_arrow);
        this.img_arrow.setRotation(-180.0f);
        this.headertext = (TextView) view.findViewById(R.id.headertext);
        this.mGalleryActivity = (ImageGalleryActivity) getActivity();
        this.imagesGridView = (GridView) view.findViewById(R.id.images_gridview);
        this.btn_close = (ImageView) view.findViewById(R.id.btn_close);
        this.btn_ok = (ImageButton) view.findViewById(R.id.btn_ok);
        this.folder_recyclerview = (RecyclerView) view.findViewById(R.id.folder_recyclerview);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(1);
        this.folder_recyclerview.setLayoutManager(llm);
        this.progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        this.headertext.setOnClickListener(new C03361());
        this.imagesGridView.setOnItemClickListener(new C03372());
        this.btn_close.setOnClickListener(new C03383());
        this.btn_ok.setOnClickListener(new C03394());
        if (getArguments().getBoolean("isOpenedForFreeCollage")) {
            this.btn_ok.setVisibility(View.VISIBLE);
        }
        new LoadGalleryAsync().execute(new Void[0]);
    }
    public List<Uri> getImagesData(Context context) {
        Exception e;
        List<Uri> imagesList = new ArrayList();
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("png");
        String mimeType1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpg");
        String orderBy = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC";
        Cursor cur = context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"_data", "bucket_display_name"}, "mime_type IN(?,?)", new String[]{mimeType, mimeType1}, orderBy);
        if (cur != null) {
            if (cur.getCount() > 0) {
                ArrayList<Uri> temp;
                ArrayList<Uri> temp2 = null;
                while (cur.moveToNext()) {
                    try
                    {
                        String data = cur.getString(cur.getColumnIndex("_data"));
                        int bucketColumn = cur.getColumnIndex("bucket_display_name");
                        imagesList.add(Uri.parse(data));
                        String folder2 = cur.getString(bucketColumn);
                        String folder = folder2.substring(0, 1).toUpperCase() + folder2.substring(1);
                        if (this.hashMap.containsKey(folder)) {
                            ((List) this.hashMap.get(folder)).add(Uri.parse(data));
                            temp = temp2;
                        } else {
                            temp = new ArrayList();
                            try {
                                temp.add(Uri.parse(data));
                                this.hashMap.put(folder, temp);
                            } catch (Exception e2) {
                                e = e2;
                            }
                        }
                        temp2 = temp;
                    } catch (Exception e3) {
                        e = e3;
                        temp = temp2;
                    }
                }
                this.hashMap.put("All Photos", imagesList);
                temp = temp2;
            }
            cur.close();
        }
        return imagesList;
    }
    public void updateAdapterView() {
        this.mImagesAdapter.notifyDataSetChanged();
    }
    class StringDateComparator implements Comparator<String> {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        public int compare(String lhs, String rhs)
        {
            try {
                return dateFormat.parse(lhs).compareTo(dateFormat.parse(rhs));
            }
            catch (Exception e){
                return 0;
            }
        }
    }

}

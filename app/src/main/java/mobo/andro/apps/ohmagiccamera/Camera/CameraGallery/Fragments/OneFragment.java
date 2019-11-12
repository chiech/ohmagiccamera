package mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.Fragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

import mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.Adapters.BucketsAdapter;
import mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.AlbumCustomData;
import mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.OpenGalleryItem;
import mobo.andro.apps.ohmagiccamera.R;

import static mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.OpenGalleryItem.temp_data_open_gallery;

public class OneFragment extends Fragment {
    private RecyclerView recyclerView;
    private BucketsAdapter mAdapter;
    private final String[] projection = new String[]{ MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATA };
    private final String[] projection2 = new String[]{MediaStore.Images.Media.DISPLAY_NAME,MediaStore.Images.Media.DATA };
    private List<String> bucketNames= new ArrayList<>();
    private List<String> bitmapList=new ArrayList<>();
    private List<Integer> bucketSizes=new ArrayList<>();
    public static List<String> imagesList= new ArrayList<>();
    public static List<Boolean> selected=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Bucket names reloaded
        bitmapList.clear();
        imagesList.clear();
        bucketNames.clear();
        getPicBuckets();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.gallery_fragment_one, container, false);
        recyclerView = v.findViewById(R.id.recycler_view);
        populateRecyclerView();
        return v;
    }
    private void populateRecyclerView()
    {
        mAdapter = new BucketsAdapter(bucketNames,bitmapList,bucketSizes,getContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position)
            {
                getPictures(bucketNames.get(position));
                OpenGalleryItem.selected_folder_name=bucketNames.get(position);
                ArrayList<ArrayList<AlbumCustomData>> temp_data=new ArrayList<ArrayList<AlbumCustomData>>();
                ArrayList<Integer> temp_date=new ArrayList<Integer>();
                ArrayList<AlbumCustomData> albumCustomData=new ArrayList<AlbumCustomData>();

                for(int i=0;i<imagesList.size();i++)
                {
                    File file = new File(imagesList.get(i));
                    Date lastModDate = new Date(file.lastModified());
                    String[] str = lastModDate.toString().split(" ");
                    temp_date.add(Integer.parseInt(str[2]));
                    albumCustomData.add(new AlbumCustomData(imagesList.get(i),str[str.length - 1],str[1],Integer.parseInt(str[2]),false));
                }

                Log.e("temp_date_before"," : "+temp_date);
                LinkedHashSet<Integer> listToSet = new LinkedHashSet<Integer>(temp_date);
//                List<String> listWithoutDuplicates = new ArrayList<String>(listToSet);
                temp_date.clear();
                temp_date.addAll(listToSet);

                Log.e("temp_date_after"," : "+temp_date);

                NavigableMap<Integer,List<AlbumCustomData>> objectsByYear = new TreeMap<Integer,List<AlbumCustomData>>();
                for (AlbumCustomData obj : albumCustomData)
                {
                    List<AlbumCustomData> yearList = objectsByYear.get(obj.getDate());
                    if (yearList == null)
                    {
                        objectsByYear.put(obj.getDate(), yearList = new ArrayList<AlbumCustomData>());
                    }
                    yearList.add(obj);
                }
                for(int j=0;j<temp_date.size();j++)
                {
                    ArrayList<AlbumCustomData> temp=new ArrayList<AlbumCustomData>();
                    temp.addAll(objectsByYear.get(temp_date.get(j)));
                    temp_data.add(temp);
                }
                temp_data_open_gallery.clear();
                temp_data_open_gallery=temp_data;
                Intent intent=new Intent(getContext(), OpenGalleryItem.class);
                startActivity(intent);
            }
            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        mAdapter.notifyDataSetChanged();
    }
    public void getPicBuckets() {
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,null, null, MediaStore.Images.Media.DATE_ADDED);
        ArrayList<String> bucketNamesTEMP = new ArrayList<>(cursor.getCount());
        ArrayList<String> bitmapListTEMP = new ArrayList<>(cursor.getCount());

        HashSet<String> albumSet = new HashSet<>();
        File file;
        if (cursor.moveToLast()) {
            do {
                if (Thread.interrupted()) {
                    return;
                }
                String album = cursor.getString(cursor.getColumnIndex(projection[0]));
                String image = cursor.getString(cursor.getColumnIndex(projection[1]));
                file = new File(image);

                if (file.exists() && !albumSet.contains(album))
                {
                    bucketNamesTEMP.add(album);
                    bitmapListTEMP.add(image);
                    albumSet.add(album);
                }
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        if (bucketNamesTEMP == null) {
            bucketNames = new ArrayList<>();
        }
        bucketNames.clear();
        bitmapList.clear();
        bucketSizes.clear();
        bucketNames.addAll(bucketNamesTEMP);
//        bitmapList.addAll(bitmapListTEMP);

        Collections.sort(bucketNames, String.CASE_INSENSITIVE_ORDER);
        for(int i=0;i<bucketNames.size();i++)
        {
            bitmapList.add(getPicturesBitmap(bucketNames.get(i)));
            Cursor cursor_size = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection2,MediaStore.Images.Media.BUCKET_DISPLAY_NAME+" =?",new String[]{bucketNames.get(i)},MediaStore.Images.Media.DATE_ADDED);
            bucketSizes.add(cursor_size.getCount());
            cursor_size.close();
        }
    }
    public String getPicturesBitmap(String bucket) {
        String temp=null;
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection2,MediaStore.Images.Media.BUCKET_DISPLAY_NAME+" =?",new String[]{bucket},MediaStore.Images.Media.DATE_ADDED);
        ArrayList<String> imagesTEMP = new ArrayList<>(cursor.getCount());
        HashSet<String> albumSet = new HashSet<>();
        File file;
        if (cursor.moveToLast()) {
            do {
                if (Thread.interrupted()) {
                    return temp;
                }
                String path = cursor.getString(cursor.getColumnIndex(projection2[1]));
                file = new File(path);
                if (file.exists() && !albumSet.contains(path)) {
                    temp=file.toString();
                    break;
                }
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        return temp;
    }
    public void getPictures(String bucket) {
        selected.clear();
        Cursor cursor = getContext().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection2,MediaStore.Images.Media.BUCKET_DISPLAY_NAME+" =?",new String[]{bucket},MediaStore.Images.Media.DATE_ADDED);
        ArrayList<String> imagesTEMP = new ArrayList<>(cursor.getCount());
        HashSet<String> albumSet = new HashSet<>();
        File file;
        if (cursor.moveToLast()) {
            do {
                if (Thread.interrupted()) {
                    return;
                }
                String path = cursor.getString(cursor.getColumnIndex(projection2[1]));
                file = new File(path);
                if (file.exists() && !albumSet.contains(path)) {
                    imagesTEMP.add(path);
                    albumSet.add(path);
                    selected.add(false);
                }
            } while (cursor.moveToPrevious());
        }
        cursor.close();
        if (imagesTEMP == null) {
            imagesTEMP = new ArrayList<>();
        }
        imagesList.clear();
        imagesList.addAll(imagesTEMP);
    }
    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}




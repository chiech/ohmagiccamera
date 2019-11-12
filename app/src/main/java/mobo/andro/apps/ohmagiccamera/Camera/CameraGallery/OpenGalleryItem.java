package mobo.andro.apps.ohmagiccamera.Camera.CameraGallery;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.Adapters.OpenGalleryItemAdapter;
import mobo.andro.apps.ohmagiccamera.CustomAds;
import mobo.andro.apps.ohmagiccamera.R;

public class OpenGalleryItem extends Activity
{
    public static ArrayList<ArrayList<AlbumCustomData>> temp_data_open_gallery=new ArrayList<ArrayList<AlbumCustomData>>();
    RecyclerView recyclerView;
    public static String selected_folder_name="";
    public static int height,width;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_open_gallery_item);

        LinearLayout adContainer=(LinearLayout) findViewById(R.id.adContainer);
        CustomAds.facebookAdBanner(OpenGalleryItem.this,adContainer);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height =displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        TextView headertext=(TextView)findViewById(R.id.headertext);
        headertext.setText(selected_folder_name);
        ImageView btn_close=(ImageView)findViewById(R.id.btn_close);
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        OpenGalleryItemAdapter openGalleryItemAdapter=new OpenGalleryItemAdapter(temp_data_open_gallery,OpenGalleryItem.this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.getItemAnimator().setChangeDuration(0);
        recyclerView.setAdapter(openGalleryItemAdapter);

    }
}

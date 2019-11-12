package mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.net.URLConnection;
import java.util.ArrayList;

import mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.AlbumCustomData;
import mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.OpenGalleryItem;
import mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.PlayVideo;
import mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.ShowImage;
import mobo.andro.apps.ohmagiccamera.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class OpenGalleryItemSubAdapter extends RecyclerView.Adapter<OpenGalleryItemSubAdapter.MyViewHolder>
{
    private ArrayList<AlbumCustomData> album_custom_data_arraylist;
    private Context context;
    int h;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView thumb;
        public ImageView check;
        public LinearLayout thumb_parent;
        public MyViewHolder(View view) {
            super(view);
            thumb = (ImageView) view.findViewById(R.id.thumb);
            check=(ImageView)view.findViewById(R.id.check);
            thumb_parent=(LinearLayout)view.findViewById(R.id.thumb_parent);
            h= OpenGalleryItem.width*33/100;
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(h,h);
            thumb_parent.setLayoutParams(params);
        }
    }

    public OpenGalleryItemSubAdapter(ArrayList<AlbumCustomData> album_custom_data_arraylist, Context context) {
        this.album_custom_data_arraylist=album_custom_data_arraylist;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_gallery_sub_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        if(album_custom_data_arraylist.get(position).get_is_selected())
        {
            holder.check.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.check.setVisibility(View.GONE);
        }
        try {
            Glide.with(context).load("file://"+album_custom_data_arraylist.get(position).getPath()).apply(new RequestOptions().override(h,h).centerCrop().dontAnimate().skipMemoryCache(true)).transition(withCrossFade()).into(holder.thumb);
        }catch (Exception e){
            Log.e("exception"," : "+e);
        }
        holder.thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(isVideoFile(album_custom_data_arraylist.get(position).getPath()))
                {
                    PlayVideo.selected_video_path=album_custom_data_arraylist.get(position).getPath();
                    Intent intent=new Intent(context,PlayVideo.class);
                    context.startActivity(intent);
                    Log.e("selected_image"," : "+album_custom_data_arraylist.get(position).getPath());
                }
                else
                {
                    ShowImage.selected_image_path=album_custom_data_arraylist.get(position).getPath();
                    Intent intent=new Intent(context,ShowImage.class);
                    context.startActivity(intent);
                    Log.e("selected_image"," : "+album_custom_data_arraylist.get(position).getPath());
                }
            }
        });
    }


    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    public static boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }
    @Override
    public int getItemCount() {
        return album_custom_data_arraylist.size();
    }
}


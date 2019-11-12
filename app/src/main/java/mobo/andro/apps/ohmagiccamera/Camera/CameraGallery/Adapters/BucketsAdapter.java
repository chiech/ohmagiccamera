package mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.Adapters;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import mobo.andro.apps.ohmagiccamera.R;

public class BucketsAdapter extends RecyclerView.Adapter<BucketsAdapter.MyViewHolder>{
    private List<String> bucketNames,bitmapList;
    private List<Integer> bucketCount;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,count;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail=(ImageView) view.findViewById(R.id.image);
        }
    }

    public BucketsAdapter(List<String> bucketNames,List<String> bitmapList,List<Integer> bitmapCount,Context context) {
        this.bucketNames=bucketNames;
        this.bitmapList = bitmapList;
        this.bucketCount=bitmapCount;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_album_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        bucketNames.get(position);
        holder.title.setText(bucketNames.get(position));
        holder.count.setText(""+bucketCount.get(position));
        Glide.with(context).load("file://"+bitmapList.get(position)).apply(new RequestOptions().override(300,300).centerCrop()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return bucketNames.size();
    }
}


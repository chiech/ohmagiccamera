package mobo.andro.apps.ohmagiccamera.Camera;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import mobo.andro.apps.ohmagiccamera.R;

public class AspectRatioAdapter extends RecyclerView.Adapter<AspectRatioAdapter.MyViewHolder> {
    private Context mContext;
    private Integer mImagesList[];
    int width;

    public AspectRatioAdapter(Context context, Integer[] imageList, int width) {
        mContext = context;
        this.width=width;
        this.mImagesList = imageList;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.aspect_grid_image, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.imageView.setImageResource(mImagesList[position]);
    }
    @Override
    public int getItemCount() {
        return mImagesList.length;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        LinearLayout linearLayout_base;

        public MyViewHolder(View view) {
            super(view);
            linearLayout_base=(LinearLayout)view.findViewById(R.id.base);
            LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(width,width);
            linearLayout_base.setLayoutParams(params);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }

}

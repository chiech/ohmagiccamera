package mobo.andro.apps.ohmagiccamera.CollageMaker;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import mobo.andro.apps.ohmagiccamera.R;

public class RecyclerResAdapter extends Adapter<RecyclerResAdapter.ViewHolder> {
    public Context ctx;
    private final int[] iconDataset;
    private boolean isUnlocked = false;
    private OnItemClickListener listener;
    /* renamed from: v */
    private View f17v = null;

    public interface OnItemClickListener {
        void onImageClick(int i, int i2);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageView imageView1;
        public ImageView imageView2;
        public TextView txt;

        public ViewHolder(View v) {
            super(v);
            this.txt = (TextView) v.findViewById(R.id.txt);
            this.imageView = (ImageView) v.findViewById(R.id.imageView);
            this.imageView1 = (ImageView) v.findViewById(R.id.imageView1);
            this.imageView2 = (ImageView) v.findViewById(R.id.imageView2);
            this.imageView1.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    RecyclerResAdapter.this.listener.onImageClick(ViewHolder.this.getLayoutPosition(), RecyclerResAdapter.this.iconDataset[ViewHolder.this.getLayoutPosition()]);
                }
            });
        }
    }

    public RecyclerResAdapter(Context c, int[] icImg, boolean isUnlocked) {
        this.ctx = c;
        this.iconDataset = icImg;
        this.isUnlocked = isUnlocked;
        setHasStableIds(true);
    }

    public void setListner(OnItemClickListener l) {
        this.listener = l;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.f17v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_list_item, parent, false);
        return new ViewHolder(this.f17v);
    }

    public void setUnlocked(boolean b) {
        this.isUnlocked = b;
        notifyDataSetChanged();
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txt.setText(String.valueOf(position + 1));
        if (position > 7) {
            if (this.isUnlocked) {
                holder.imageView2.setVisibility(View.GONE);
            } else {
                holder.imageView2.setVisibility(View.VISIBLE);
            }
        }
        Glide.with(this.ctx).load(Integer.valueOf(this.iconDataset[position])).apply(new RequestOptions().centerCrop()).thumbnail(0.8f).into(holder.imageView1);
    }

    public int getItemCount() {
        return this.iconDataset.length;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getItemViewType(int position) {
        return position;
    }
}

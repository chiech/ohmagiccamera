package mobo.andro.apps.ohmagiccamera.CollageMaker.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Random;

import mobo.andro.apps.ohmagiccamera.R;

public class StickerResAdapter extends Adapter<StickerResAdapter.ViewHolder> {
    public Context ctx;
    private final int[] iconDataset;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onImageClick(int i, int i2);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            this.imageView = (ImageView) v.findViewById(R.id.imageView1);
            this.imageView .setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    StickerResAdapter.this.listener.onImageClick(ViewHolder.this.getLayoutPosition() + 1, StickerResAdapter.this.iconDataset[ViewHolder.this.getLayoutPosition()]);
                }
            });

/*            this.imageView.setOnClickListener(new OnClickListener(StickerResAdapter.this) {
                public void onClick(View v) {
                    StickerResAdapter.this.listener.onImageClick(ViewHolder.this.getLayoutPosition() + 1, StickerResAdapter.this.iconDataset[ViewHolder.this.getLayoutPosition()]);
                }
            });
  */      }
    }

    public StickerResAdapter(Context c, int[] icImg) {
        this.ctx = c;
        this.iconDataset = icImg;
    }

    public void setListner(OnItemClickListener l) {
        this.listener = l;
    }

    public void shuffleEffects(int curId) {
        int tempId = new Random().nextInt(19);
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.effect_list_item, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(this.ctx).load(Integer.valueOf(this.iconDataset[position])).thumbnail(0.8f).into(holder.imageView);
    }

    public int getItemCount() {
        return this.iconDataset.length;
    }
}

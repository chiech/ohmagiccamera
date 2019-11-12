package mobo.andro.apps.ohmagiccamera.CollageMaker.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import mobo.andro.apps.ohmagiccamera.R;

public class CollageIconAdapter extends Adapter<CollageIconAdapter.ViewHolder> {
    private Context context;
    private int[] iconImages;
    private OnItemClickListener listener;
    private int selected_position = 500;

    public interface OnItemClickListener {
        void onImageClick(int i, int i2);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        RelativeLayout rl_item;

        public ViewHolder(View view) {
            super(view);
            this.rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
            this.iconImageView = (ImageView) view.findViewById(R.id.item_image);
        }
    }

    public CollageIconAdapter(Context context, int[] makeUpEditImage) {
        this.context = context;
        this.iconImages = makeUpEditImage;
    }

    public void setOnItemClickListner(OnItemClickListener l) {
        this.listener = l;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getItemCount() {
        return this.iconImages.length;
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.iconImageView.setImageResource(this.iconImages[position]);
        if (this.selected_position == position) {
            viewHolder.iconImageView.setColorFilter(-1);
        } else {
            viewHolder.iconImageView.setColorFilter(0);
        }
        viewHolder.iconImageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CollageIconAdapter.this.listener.onImageClick(position, CollageIconAdapter.this.iconImages[position]);
            }
        });
    }

    public void setSelected(int position) {
        this.selected_position = position;
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.collage_recycler_item, parent, false));
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);
        return viewHolder;
    }

    public int getItemViewType(int position) {
        return position;
    }
}

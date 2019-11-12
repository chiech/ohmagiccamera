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
import android.widget.TextView;

import mobo.andro.apps.ohmagiccamera.R;

public class BackgroundsAdapter extends Adapter<BackgroundsAdapter.ViewHolder> {
    private int[] bgNames;
    private Context context;
    private int[] iconImages;
    private OnItemClickListener listener;
    private int selected_position = 500;

    public interface OnItemClickListener {
        void onImageClick(int i, int i2);

        void onImageReClick(int i, int i2);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bg_name;
        ImageView item_image;
        RelativeLayout rl_item;

        public ViewHolder(View view) {
            super(view);
            this.rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
            this.item_image = (ImageView) view.findViewById(R.id.item_image);
            this.bg_name = (TextView) view.findViewById(R.id.bg_name);
        }
    }

    public BackgroundsAdapter(Context context, int[] iconArr, int[] bgNameArr) {
        this.context = context;
        this.iconImages = iconArr;
        this.bgNames = bgNameArr;
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
        viewHolder.item_image.setImageResource(this.iconImages[position]);
        viewHolder.bg_name.setText(this.context.getResources().getString(this.bgNames[position]));
        if (this.selected_position == position) {
            viewHolder.rl_item.setBackgroundColor(-1);
            viewHolder.bg_name.setTextColor(-1);
        } else {
            viewHolder.rl_item.setBackgroundColor(0);
            viewHolder.bg_name.setTextColor(-7829368);
        }
        viewHolder.item_image.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (BackgroundsAdapter.this.selected_position != position) {
                    BackgroundsAdapter.this.listener.onImageClick(position, BackgroundsAdapter.this.iconImages[position]);
                } else {
                    BackgroundsAdapter.this.listener.onImageReClick(position, BackgroundsAdapter.this.iconImages[position]);
                }
            }
        });
    }

    public void setSelected(int position) {
        this.selected_position = position;
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.backgrounds_recycler_item, parent, false));
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);
        return viewHolder;
    }

    public int getItemViewType(int position) {
        return position;
    }
}

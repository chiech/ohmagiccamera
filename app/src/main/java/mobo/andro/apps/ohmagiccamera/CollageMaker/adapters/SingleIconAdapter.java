package mobo.andro.apps.ohmagiccamera.CollageMaker.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mobo.andro.apps.ohmagiccamera.R;

public class SingleIconAdapter extends Adapter<SingleIconAdapter.ViewHolder> {
    private Context context;
    private int[] icNames;
    private int[] iconImages;
    private OnItemClickListener listener;
    private int selected_position = 500;

    public interface OnItemClickListener {
        void onImageClick(int i, int i2);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ic_name;
        ImageView iconImageView;

        public ViewHolder(View view) {
            super(view);
            this.iconImageView = (ImageView) view.findViewById(R.id.item_image);
            this.ic_name = (TextView) view.findViewById(R.id.ic_name);
        }
    }

    public SingleIconAdapter(Context context, int[] makeUpEditImage, int[] icnames) {
        this.context = context;
        this.iconImages = makeUpEditImage;
        this.icNames = icnames;
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
        viewHolder.ic_name.setText(this.context.getResources().getString(this.icNames[position]));
        if (this.selected_position == position) {
            viewHolder.iconImageView.setColorFilter(-1);
            viewHolder.ic_name.setTextColor(-1);
        } else {
            viewHolder.iconImageView.setColorFilter(0);
            viewHolder.ic_name.setTextColor(-7829368);
        }
        viewHolder.iconImageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SingleIconAdapter.this.listener.onImageClick(position, SingleIconAdapter.this.iconImages[position]);
            }
        });
    }

    public void setSelected(int position) {
        this.selected_position = position;
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.icon_recycler_item, parent, false));
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);
        return viewHolder;
    }

    public int getItemViewType(int position) {
        return position;
    }
}

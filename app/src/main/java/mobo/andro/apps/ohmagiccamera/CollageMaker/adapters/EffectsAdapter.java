package mobo.andro.apps.ohmagiccamera.CollageMaker.adapters;

import android.content.Context;
import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mobo.andro.apps.ohmagiccamera.CollageMaker.Constants;
import mobo.andro.apps.ohmagiccamera.R;

public class EffectsAdapter extends Adapter<EffectsAdapter.ViewHolder> {
    private Context context;
    private int[] iconImages;
    private OnItemClickListener listener;
    private int selected_position = 500;
    private int selected_position_progress = 50;

    public interface OnItemClickListener {
        void onImageClick(int i, int i2);

        void onImageReClick(int i, int i2);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView effect_name;
        ImageView item_image;
        TextView progress_text;

        public ViewHolder(View view) {
            super(view);
            this.item_image = (ImageView) view.findViewById(R.id.item_image);
            this.progress_text = (TextView) view.findViewById(R.id.progress_text);
            this.effect_name = (TextView) view.findViewById(R.id.tone_name);
        }
    }

    public EffectsAdapter(Context context, int[] iconArr) {
        this.context = context;
        this.iconImages = iconArr;
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
        viewHolder.effect_name.setText(this.context.getResources().getString(Constants.filterName(position)));
        if (this.selected_position == position) {
            viewHolder.item_image.setColorFilter(Color.parseColor("#96000000"));
            if (this.selected_position != 0) {
                viewHolder.progress_text.setText(String.valueOf(this.selected_position_progress));
                viewHolder.progress_text.setVisibility(View.VISIBLE);
            } else {
                viewHolder.progress_text.setVisibility(View.GONE);
            }
            viewHolder.effect_name.setTextColor(-1);
        } else {
            viewHolder.item_image.setColorFilter(0);
            viewHolder.progress_text.setVisibility(View.GONE);
            viewHolder.effect_name.setTextColor(-7829368);
        }
        viewHolder.item_image.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (EffectsAdapter.this.selected_position != position) {
                    EffectsAdapter.this.selected_position_progress = 50;
                    EffectsAdapter.this.listener.onImageClick(position, EffectsAdapter.this.iconImages[position]);
                    return;
                }
                EffectsAdapter.this.listener.onImageReClick(position, EffectsAdapter.this.iconImages[position]);
            }
        });
    }

    public void setSelected(int position) {
        this.selected_position = position;
        notifyDataSetChanged();
    }

    public int getSelected_position_progress(int position) {
        if (this.selected_position == position) {
            return this.selected_position_progress;
        }
        return 50;
    }

    public void setSelected_position_progress(int selected_position_progress) {
        this.selected_position_progress = selected_position_progress;
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.effects_recycler_item, parent, false));
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);
        return viewHolder;
    }

    public int getItemViewType(int position) {
        return position;
    }
}

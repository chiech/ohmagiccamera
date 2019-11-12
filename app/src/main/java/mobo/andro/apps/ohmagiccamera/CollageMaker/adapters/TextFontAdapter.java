package mobo.andro.apps.ohmagiccamera.CollageMaker.adapters;

import android.content.Context;
import android.graphics.Typeface;

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

public class TextFontAdapter extends Adapter<TextFontAdapter.ViewHolder> {
    private Context context;
    private String[] fontNames;
    private OnItemClickListener listener;
    private int selected_position = 500;

    public interface OnItemClickListener {
        void onImageClick(int i, String str);

        void onImageReClick(int i, String str);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView item_image;
        TextView item_text;
        RelativeLayout rl_item;

        public ViewHolder(View view) {
            super(view);
            this.rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
            this.item_image = (ImageView) view.findViewById(R.id.item_image);
            this.item_text = (TextView) view.findViewById(R.id.item_text);
        }
    }

    public TextFontAdapter(Context context, String[] fontNameArr) {
        this.context = context;
        this.fontNames = fontNameArr;
    }

    public void setOnItemClickListner(OnItemClickListener l) {
        this.listener = l;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getItemCount() {
        return this.fontNames.length;
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String fontName = this.fontNames[position];
        if (this.selected_position == position) {
            viewHolder.rl_item.setBackgroundResource(R.drawable.white_rect_border);
            viewHolder.item_image.setImageResource(R.drawable.btn_cp);
            viewHolder.item_text.setVisibility(View.GONE);
        } else {
            viewHolder.rl_item.setBackgroundResource(R.drawable.gray_rect_border);
            viewHolder.item_image.setImageResource(0);
            viewHolder.item_text.setText(fontName.substring(0, fontName.indexOf(46)));
            viewHolder.item_text.setTypeface(Typeface.createFromAsset(this.context.getAssets(), fontName));
            viewHolder.item_text.setVisibility(View.VISIBLE);
        }
        viewHolder.item_image.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (TextFontAdapter.this.selected_position != position) {
                    TextFontAdapter.this.listener.onImageClick(position, TextFontAdapter.this.fontNames[position]);
                } else {
                    TextFontAdapter.this.listener.onImageReClick(position, TextFontAdapter.this.fontNames[position]);
                }
            }
        });
    }

    public void setSelected(int position) {
        this.selected_position = position;
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.text_recycler_item, parent, false));
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);
        return viewHolder;
    }

    public int getItemViewType(int position) {
        return position;
    }
}

package mobo.andro.apps.ohmagiccamera.Camera;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;
import mobo.andro.apps.ohmagiccamera.R;

import static mobo.andro.apps.ohmagiccamera.Camera.CameraDemoActivity.selected_aspect_ratio;


public class FilterAdapter extends Adapter<FilterAdapter.FilterHolder> {
    public Integer[] buttonImage = new Integer[0];
    private Context context;
    private int selected = SensorControler.DELEY_DURATION;
    int height;

    class FilterHolder extends ViewHolder {
        TextView filterName;
        FrameLayout filterRoot;
        ImageView imageViewDone;
        CircleImageView thumbImage;
        FrameLayout thumbSelected;
        View thumbSelected_bg;

        public FilterHolder(View itemView) {
            super(itemView);
        }
    }

    public FilterAdapter(Context context, Integer[] buttonImage, int selected,int height) {
        this.context = context;
        this.buttonImage = buttonImage;
        this.selected = selected;
        this.height=height;
    }

    public void setIntPos(int pos) {
        this.selected = pos;
        notifyItemChanged(pos);
        notifyDataSetChanged();
    }

    public FilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.filter_item_layout, parent, false);
        FilterHolder viewHolder = new FilterHolder(view);

        viewHolder.thumbImage = (CircleImageView) view.findViewById(R.id.filter_thumb_image);
        FrameLayout.LayoutParams filter_thumb_image_param=new FrameLayout.LayoutParams(height*70/100,height*70/100);
        viewHolder.thumbImage.setLayoutParams(filter_thumb_image_param);

        viewHolder.filterName = (TextView) view.findViewById(R.id.filter_thumb_name);
        LinearLayout.LayoutParams filterName_param=new LinearLayout.LayoutParams(height*80/100,height*25/100);
        viewHolder.filterName.setLayoutParams(filterName_param);

        viewHolder.filterRoot = (FrameLayout) view.findViewById(R.id.filter_root);
        LinearLayout.LayoutParams filter_root_param=new LinearLayout.LayoutParams(height*80/100,height);
        viewHolder.filterRoot.setLayoutParams(filter_root_param);

        viewHolder.thumbSelected = (FrameLayout) view.findViewById(R.id.filter_thumb_selected);
        viewHolder.thumbSelected_bg = view.findViewById(R.id.filter_thumb_selected_bg);
        FrameLayout.LayoutParams thumbSelected_bg_param=new FrameLayout.LayoutParams(height*70/100,height*70/100);
        viewHolder.thumbSelected_bg.setLayoutParams(thumbSelected_bg_param);

        viewHolder.imageViewDone = (ImageView) view.findViewById(R.id.filter_thumb_selected_icon);
        return viewHolder;
    }

    public void onBindViewHolder(FilterHolder holder, final int position) {
        try {
            Glide.with(this.context).load(this.buttonImage[position]).into(holder.thumbImage);
//            holder.imageViewDone.setBackgroundResource(R.drawable.seekable_selected_ic);
            holder.filterName.setText(filterName1(position));

            if(selected_aspect_ratio==0)
            {
                holder.filterName.setTextColor(context.getResources().getColor(R.color.color_white));
            }
            else
            {
                holder.filterName.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark2));
            }

            if (position == this.selected) {
                holder.thumbSelected.setVisibility(View.VISIBLE);
                holder.thumbSelected_bg.setBackgroundResource(R.drawable.circle_shape);
//                holder.thumbSelected_bg.setBackgroundColor(ContextCompat.getColor(this.context,R.color.accent_material_dark));
                holder.thumbSelected_bg.setAlpha(0.7f);
            } else {
                holder.thumbSelected.setVisibility(View.GONE);
            }
            holder.filterRoot.setOnClickListener(new OnClickListener() {
                public void onClick(View v)
                {
                    FilterAdapter.this.notifyItemChanged(FilterAdapter.this.selected);
                    FilterAdapter.this.selected = position;
                    FilterAdapter.this.notifyItemChanged(FilterAdapter.this.selected);
                }
            });
        } catch (Exception e) {
        }
    }

    public int getItemCount() {
        return this.buttonImage == null ? 0 : this.buttonImage.length;
    }

    public int FilterType2Color(int pos) {
        switch (pos) {
        }
        return R.color.black_new;
    }

    public static int filterName1(int pos) {
        switch (pos) {
            case 1:
                return R.string.sala;
            case 2:
                return R.string.dawa;
            case 3:
                return R.string.pica;
            case 4:
                return R.string.rouge;
            case 5:
                return R.string.tuse;
            case 6:
                return R.string.versa;
            case 7:
                return R.string.slide;
            case 8:
                return R.string.bluzzer;
            case 9:
                return R.string.crush;
            case 10:
                return R.string.f2;
            case 11:
                return R.string.elset;
            case 12:
                return R.string.s1933;
            case 13:
                return R.string.koralle;
            case 14:
                return R.string.prism;
            case 15:
                return R.string.tri;
            case 16:
                return R.string.x1;
            case 17:
                return R.string.x2;
            case 18:
                return R.string.x3;
            case 19:
                return R.string.x4;
            case 20:
                return R.string.wind;
            case 21:
                return R.string.s1895;
            case 22:
                return R.string.mono;
            case 23:
                return R.string.leaf;
            case 24:
                return R.string.x5;
            case 25:
                return R.string.x6;
            case 26:
                return R.string.x7;
            case 27:
                return R.string.light;
            case 28:
                return R.string.dark;
            case 29:
                return R.string.wow;
            case 30:
                return R.string.lemo;
            case 31:
                return R.string.Newage;
            case 32:
                return R.string.Crescent;
            case 33:
                return R.string.Gloom;
            case 34:
                return R.string.Hippie;
            case 35:
                return R.string.Coffee;
            case 36:
                return R.string.Nostalgia;
            case 37:
                return R.string.filter_sunrise;
            case 38:
                return R.string.Matrix;
            case 39:
                return R.string.Memory;
            case 40:
                return R.string.Noir;
            case 41:
                return R.string.Eiffeln;
            case 42:
                return R.string.Eiffel;
            case 43:
                return R.string.Fresh1;
            case 44:
                return R.string.bolly;
            case 45:
                return R.string.color;
            case 46:
                return R.string.sunset;
            case 47:
                return R.string.music;
            case 48:
                return R.string.star;
            case 49:
                return R.string.rough;
            case 50:
                return R.string.sun;
            case 51:
                return R.string.rangoli;
            case 52:
                return R.string.flash;
            case 53:
                return R.string.bubble;
            case 54:
                return R.string.star12;
            case 55:
                return R.string.x11;
            default:
                return R.string.none;
        }
    }
}

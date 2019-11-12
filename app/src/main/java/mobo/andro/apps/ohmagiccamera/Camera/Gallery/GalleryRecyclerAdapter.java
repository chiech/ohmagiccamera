package mobo.andro.apps.ohmagiccamera.Camera.Gallery;

import android.content.Context;
import android.net.Uri;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import mobo.andro.apps.ohmagiccamera.R;

public class GalleryRecyclerAdapter extends Adapter<GalleryRecyclerAdapter.ViewHolder>
{
    private Context context;
    List<String> foldersName = null;
    List<Integer> foldersSize = null;
    HashMap<String, List<Uri>> hashMap = null;
    private OnItemClickListener listener;
    private int selected_position = 500;

    public interface OnItemClickListener {
        void onImageClick(int i, String str);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ic_name;
        ImageView iconImageView;
        LinearLayout selector_parent;
        public ViewHolder(View view) {
            super(view);
            this.iconImageView = (ImageView) view.findViewById(R.id.item_image);
            this.ic_name = (TextView) view.findViewById(R.id.ic_name);
            this.selector_parent=(LinearLayout)view.findViewById(R.id.selector_parent);
        }
    }

    public GalleryRecyclerAdapter(Context context, HashMap<String, List<Uri>> hm) {
        this.context = context;
        this.hashMap = hm;
        this.foldersName = new ArrayList(hm.keySet());
        Collections.sort(this.foldersName);
        if (this.foldersName.contains("All Photos")) {
            this.foldersName.remove("All Photos");
            this.foldersName.add(0, "All Photos");
        }
    }

    public void setOnItemClickListner(OnItemClickListener l) {
        this.listener = l;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getItemCount() {
        return this.foldersName.size();
    }

    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Glide.with(this.context).load(new File(((Uri) ((List) this.hashMap.get(this.foldersName.get(position))).get(0)).getPath())).apply(new RequestOptions().centerCrop()).into(viewHolder.iconImageView);
        viewHolder.ic_name.setText((CharSequence) this.foldersName.get(position));

        if (this.selected_position == position)
        {
            viewHolder.ic_name.setTextColor(context.getResources().getColor(R.color.material_deep_teal_200));

        } else {
            viewHolder.ic_name.setTextColor(context.getResources().getColor(R.color.colorGray));
        }
        viewHolder.selector_parent.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                GalleryRecyclerAdapter.this.listener.onImageClick(position, (String) GalleryRecyclerAdapter.this.foldersName.get(position));
            }
        });
    }

    public void setSelected(int position) {
        this.selected_position = position;
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.camera_folder_recycler_item, parent, false));
        parent.setId(position);
        parent.setFocusable(false);
        parent.setFocusableInTouchMode(false);
        return viewHolder;
    }

    public int getItemViewType(int position) {
        return position;
    }
}

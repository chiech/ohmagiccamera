package mobo.andro.apps.ohmagiccamera.textmodule;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobo.andro.apps.ohmagiccamera.R;

public class AssetsGrid extends BaseAdapter {
    private final String[] Imageid;
    private Context mContext;
    int selPos = -1;

    public class ViewHolder {
        RelativeLayout layItem;
        TextView txtView;
    }

    public AssetsGrid(Context c, String[] imageid) {
        this.mContext = c;
        this.Imageid = imageid;
    }

    public int getCount() {
        return this.Imageid.length;
    }

    public Object getItem(int position) {
        return this.Imageid[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.libtext_grid_assets, null);
            holder = new ViewHolder();
            holder.txtView = (TextView) convertView.findViewById(R.id.grid_text);
            holder.layItem = (RelativeLayout) convertView.findViewById(R.id.layItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtView.setTypeface(Typeface.createFromAsset(this.mContext.getAssets(), this.Imageid[position]));
        holder.layItem.setBackgroundColor(0);
        if (this.selPos >= 0 && position == this.selPos) {
            holder.layItem.setBackgroundColor(-7829368);
        }
        return convertView;
    }

    public void setSelected(int posotion) {
        this.selPos = posotion;
        notifyDataSetChanged();
    }
}

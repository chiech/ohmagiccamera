package mobo.andro.apps.ohmagiccamera.textmodule;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import mobo.andro.apps.ohmagiccamera.R;

public class ColorListAdapter extends BaseAdapter {
    private final String[] colorIdArr;
    private Context mContext;

    public class ViewHolder {
        ImageView imageView;
    }

    public ColorListAdapter(Context c, String[] colorIdArr) {
        this.mContext = c;
        this.colorIdArr = colorIdArr;
    }

    public int getCount() {
        return this.colorIdArr.length;
    }

    public Object getItem(int position) {
        return Integer.valueOf(Color.parseColor(this.colorIdArr[position]));
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.libtext_btxt_lst_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.grid_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setBackgroundColor(Color.parseColor(this.colorIdArr[position]));
        return convertView;
    }
}

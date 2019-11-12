package com.irisstudio.demo;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.irisstudio.demo.view.SquareFrameLayoutList;
import com.irisstudio.demo.view.SquareImageViewList;
import java.util.List;

import mobo.andro.apps.ohmagiccamera.R;

public class HoriontalListAdapter extends ArrayAdapter<StickerInfo> {
    Context context;

    class ViewHolder {
        SquareImageViewList mThumbnail;
        SquareFrameLayoutList root;
        Uri uri;

        public ViewHolder(View view) {
            this.root = (SquareFrameLayoutList) view.findViewById(R.id.root);
            this.mThumbnail = (SquareImageViewList) view.findViewById(R.id.thumbnail_image);
        }
    }

    public HoriontalListAdapter(Context context, List<StickerInfo> images) {
        super(context, 0, images);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.horizontal_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.i("testings", "Uri " + ((StickerInfo) getItem(position)).getIMAGE_PATH());
        Uri mUri = Uri.parse(((StickerInfo) getItem(position)).getIMAGE_PATH());
        if (holder.uri == null || !holder.uri.equals(mUri)) {
//            Glide.with(this.context).load(mUri.toString()).thumbnail(0.5f).dontAnimate().skipMemoryCache(true).centerCrop().placeholder(R.drawable.sticker_loading).error(R.drawable.sticker_no_image).into(holder.mThumbnail);
            holder.uri = mUri;
        }
        return convertView;
    }
}

package mobo.andro.apps.ohmagiccamera.Camera.Gallery;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import androidx.core.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import mobo.andro.apps.ohmagiccamera.CollageMaker.CustomSquareFrameLayout;
import mobo.andro.apps.ohmagiccamera.R;

public class ImageGalleryAdapter extends ArrayAdapter<Uri> {
    Context context;
    private ImageGalleryActivity mGalleryActivity = null;

    class ViewHolder {
        ImageView mThumbnail;
        CustomSquareFrameLayout root;
        Uri uri;

        public ViewHolder(View view) {
            this.root = (CustomSquareFrameLayout) view.findViewById(R.id.root);
            this.mThumbnail = (ImageView) view.findViewById(R.id.thumbnail_image);
        }
    }

    public ImageGalleryAdapter(Context context, List<Uri> images) {
        super(context, 0, images);
        this.context = context;
        if (context instanceof ImageGalleryActivity) {
            this.mGalleryActivity = (ImageGalleryActivity) context;
        }
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Drawable drawable = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.camera_picker_grid_item_gallery_thumbnail, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Uri mUri = (Uri) getItem(position);
        boolean isSelected = this.mGalleryActivity.containsImage(mUri);
        if (holder.root instanceof FrameLayout) {
            FrameLayout frameLayout = holder.root;
            if (isSelected) {
                drawable = ResourcesCompat.getDrawable(this.context.getResources(), R.drawable.gallery_photo_selected, null);
            }
            frameLayout.setForeground(drawable);
        }
        if (holder.uri == null || !holder.uri.equals(mUri)) {
            Glide.with(this.context).load(mUri.toString()).apply(new RequestOptions().centerCrop()).thumbnail(0.1f).into(holder.mThumbnail);
            holder.uri = mUri;
        }
        return convertView;
    }
}

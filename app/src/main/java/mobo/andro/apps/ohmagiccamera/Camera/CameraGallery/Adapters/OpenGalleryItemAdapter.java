package mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.Adapters;

import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mobo.andro.apps.ohmagiccamera.Camera.CameraGallery.AlbumCustomData;
import mobo.andro.apps.ohmagiccamera.R;

public class OpenGalleryItemAdapter extends RecyclerView.Adapter<OpenGalleryItemAdapter.MyViewHolder>
{
    private ArrayList<ArrayList<AlbumCustomData>> album_custom_data_arraylist;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title,select_all;
        public RecyclerView recyclerView_item;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.date);
            select_all = (TextView) view.findViewById(R.id.select_all);
            recyclerView_item=(RecyclerView)view.findViewById(R.id.recycler_view_item);
        }
    }

    public OpenGalleryItemAdapter(ArrayList<ArrayList<AlbumCustomData>> album_custom_data_arraylist, Context context) {
        this.album_custom_data_arraylist=album_custom_data_arraylist;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.open_gallery_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        holder.title.setText("date");
        int i=album_custom_data_arraylist.get(position).get(0).getDate();
        holder.title.setText(album_custom_data_arraylist.get(position).get(0).getYear()+"."+album_custom_data_arraylist.get(position).get(0).getMonth()+"."+i);
        final OpenGalleryItemSubAdapter openGalleryItemAdapter=new OpenGalleryItemSubAdapter(album_custom_data_arraylist.get(position),context);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(context, 3);
        holder.recyclerView_item.setLayoutManager(mLayoutManager);
        holder.recyclerView_item.getItemAnimator().setChangeDuration(0);
        holder.recyclerView_item.setAdapter(openGalleryItemAdapter);

        holder.select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                /*
                for(int i=0;i<album_custom_data_arraylist.size();i++)
                {
                    album_custom_data_arraylist.get(position).get(i).setIs_selected(true);
                    String path=album_custom_data_arraylist.get(position).get(i).getPath();
                    boolean is_exist=false;
                    for(int j=0;j<OpenGalleryItem.selected_images.size();j++)
                    {
                        if(path.equals(OpenGalleryItem.selected_images.get(j)))
                        {
                            is_exist=true;
                            break;
                        }
                        else
                        {
                            is_exist=false;
                        }
                    }
                    if(is_exist==false)
                    {
                        OpenGalleryItem.selected_images.add(album_custom_data_arraylist.get(position).get(i).getPath());
                    }
                }
                openGalleryItemAdapter.notifyDataSetChanged();
                */
            }
        });
    }
    @Override
    public int getItemCount() {
        return album_custom_data_arraylist.size();
    }
}


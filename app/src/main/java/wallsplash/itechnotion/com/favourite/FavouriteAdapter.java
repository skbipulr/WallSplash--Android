package wallsplash.itechnotion.com.favourite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import wallsplash.itechnotion.com.bean.FavouriteBean;
import wallsplash.itechnotion.com.wallsplash.R;

public class FavouriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    Context c;
    private LayoutInflater inflater;
    ArrayList<FavouriteBean> exploreList = new ArrayList<>();
    OnCategorybyidSelectedListner onCategorybyidSelectedListner;
    FavouriteBean exploreBean;



    public FavouriteAdapter(final Context c, ArrayList<FavouriteBean> favouriteBeans) {
        this.c = c;
        this.exploreList = favouriteBeans;

    }

    public void setOnCategorybyidSelectedListner(OnCategorybyidSelectedListner onCategorybyidSelectedListner) {

        this.onCategorybyidSelectedListner = onCategorybyidSelectedListner;
    }

    public interface OnCategorybyidSelectedListner {
        void setOnCategorybyidSelatedListner(int position, FavouriteBean favouriteBean);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_explorephotosbyid, parent, false);
            return new MyViewholder(view);


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //holder.tvTrendingName.setText(Html.fromHtml(trendingList.get(position).getTitle()));

        if (holder instanceof MyViewholder) {
            final MyViewholder myHolder = (MyViewholder) holder;
            Glide.with(c).load(exploreList.get(position).getUrl())
                    .thumbnail(0.5f)
                    .placeholder(R.drawable.ic_placeholder_photos)
                    .error(R.drawable.ic_placeholder_photos)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(myHolder.imgview);
         /*   Picasso.get()
                    .load(exploreList.get(position).getUrl())
                    .into(myHolder.imgview);*/
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                //  private RecyclerView rvevenment;
                @Override
                public void onClick(View view) {
                    onCategorybyidSelectedListner.setOnCategorybyidSelatedListner(position, exploreList.get(position));

                }
            });
        }




    }

    @Override
    public int getItemCount() {
        return exploreList.size();
    }


    public class MyViewholder extends RecyclerView.ViewHolder {
        ImageView imgview;


        public MyViewholder(View itemView) {
            super(itemView);
            imgview = (ImageView) itemView.findViewById(R.id.imgview);

        }
    }



}
package com.haui.huantd.vfmmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by huand on 02/10/18.
 */

public class PostListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "PostListAdapter";
    private RequestOptions options = new RequestOptions();
    private List<Product> list;
    private Context mContext;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private ILoadMore loadMore;
    private boolean isLoading;

    public PostListAdapter(RecyclerView recyclerView, List<Product> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
        options.centerCrop();
        options.placeholder(R.drawable.spinner_background);
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (loadMore != null)
                        loadMore.onLoadMore();
                    isLoading = true;
                }

            }
        });
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_post, parent, false);
            return new ItemPost(itemView);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemPost) {
            try {
                Product product = list.get(position);
                ItemPost viewHolder = (ItemPost) holder;
                viewHolder.tvName.setText(product.getTieuDe());
                viewHolder.tvPrice.setText(product.getGia() + "Đ");
                String thoiGian = Util.getThoiGian(product.getThoiGian());
                String huyen = "null";
                if (product.getHuyen().equals("")) {
                    huyen = product.getTinh();
                } else {
                    huyen = product.getHuyen();
                }
                viewHolder.tvInfo.setText(thoiGian + " | " + huyen);
                Glide.with(mContext).load(product.getUrlImage()).apply(options).into(viewHolder.img);
            } catch (Exception e) {
                Log.e(TAG, "onBindViewHolder: " + e.toString());
            }
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void setLoadMore(ILoadMore loadMore) {
        this.loadMore = loadMore;
    }

    public void setLoaded() {
        isLoading = false;
    }


    class ItemPost extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        TextView tvPrice;
        TextView tvInfo;
        ImageView img;
        Button btnXacNhan;

        public ItemPost(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvInfo = itemView.findViewById(R.id.tv_info);
            img = itemView.findViewById(R.id.img_image);
            btnXacNhan = itemView.findViewById(R.id.btn_xac_nhan);
            btnXacNhan.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            final String idPost = list.get(getLayoutPosition()).getId();
            Log.e(TAG, idPost);
            final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReference();
            databaseReference.child(Constants.NON_CONFIRM_POST).child(idPost).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Product product = dataSnapshot.getValue(Product.class);
                    FirebaseDatabase.getInstance()
                            .getReference().child(Constants.CONFIRM_POST).child(idPost).setValue(product);
                    FirebaseDatabase.getInstance()
                            .getReference().child(Constants.NON_CONFIRM_POST).child(idPost).removeValue();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            Log.e(TAG, "onClick: " + list.get(getLayoutPosition()).getId());
            list.remove(getLayoutPosition());
            notifyDataSetChanged();
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {

        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    public interface ILoadMore {
        void onLoadMore();
    }


}



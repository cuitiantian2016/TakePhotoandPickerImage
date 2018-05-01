package com.docwei.cameraphotodemo.album;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.docwei.cameraphotodemo.R;

import java.util.List;

public class SelectPhotosAdapter extends RecyclerView.Adapter<SelectPhotosAdapter.ViewHolder> implements View.OnClickListener {
    private     Context         mContext;
    private     List<AlbumInfo> mAlbums;
    private int lastPosition=-1;
    private RequestOptions  mRequestOptions;
    public SelectPhotosAdapter(Context context, List<AlbumInfo> albums) {
        mContext = context;
        mAlbums = albums;
        mRequestOptions = new RequestOptions().placeholder(R.drawable.img_default)
                                              .error(R.drawable.img_fail);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(mContext).inflate(R.layout.dialog_recycler_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        holder.itemView.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int position) {
        AlbumInfo albumInfo=mAlbums.get(position);
        viewholder.mIv_select.setEnabled(albumInfo.isSelect());
        if(albumInfo.isSelect()){
            lastPosition=position;
        }
        Glide.with(mContext)
             .load(albumInfo.getFirstPhoto())
             .apply(mRequestOptions)
             .into(viewholder.mIv_first);
        try {
            viewholder.mTv_album_count.setText(String.valueOf(albumInfo.getPhotoCounts()));
        }catch (Exception e){
            viewholder.mTv_album_count.setText("0张");
        }
        viewholder.mTv_album_name.setText(albumInfo.getAlbumName());
        viewholder.itemView.setTag(position);

    }
     public void notifyDataChange(int lastPosition,int currentPosition){
        if(lastPosition==-1){
            return;
        }
        if(lastPosition!=currentPosition) {
            mAlbums.get(lastPosition).setSelect(false);
            mAlbums.get(currentPosition).setSelect(true);
            notifyItemChanged(lastPosition);
            notifyItemChanged(currentPosition);
        }
     }
    @Override
    public int getItemCount() {
        return mAlbums.size();
    }

    @Override
    public void onClick(View v) {
        int position= (int) v.getTag();
        if(mOnItemClickListener!=null){
            mOnItemClickListener.clickItem(lastPosition,position,mAlbums.get(position));
        }
    }

    static class ViewHolder  extends RecyclerView.ViewHolder{
        private final ImageView mIv_first;
        private final TextView mTv_album_name;
        private final TextView mTv_album_count;
        private final ImageView mIv_select;

        public ViewHolder(View itemView) {
            super(itemView);
            mIv_first = itemView.findViewById(R.id.iv_first);
            mTv_album_name = itemView.findViewById(R.id.tv_album_name);
            mTv_album_count = itemView.findViewById(R.id.tv_album_count);
            mIv_select = itemView.findViewById(R.id.iv_select);
        }
    }
    public OnItemClickListener mOnItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener=listener;
    }
   public interface  OnItemClickListener{
         void clickItem(int lastPosition,int currentPosition,AlbumInfo info);
   }
}

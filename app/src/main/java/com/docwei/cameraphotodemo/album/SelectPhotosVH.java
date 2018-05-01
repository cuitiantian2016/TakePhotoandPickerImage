package com.docwei.cameraphotodemo.album;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.docwei.cameraphotodemo.R;
import com.docwei.cameraphotodemo.album.ImageBean;
import com.docwei.cameraphotodemo.dialog.DialogPlus;
import com.docwei.cameraphotodemo.dialog.Holder;

import java.util.List;


/**
 * Created by git on 2018/4/13.
 */

public class SelectPhotosVH implements Holder {
    private Context mContext;

    private View mContentContainer;
    private List<AlbumInfo> list;

    public SelectPhotosVH(Context context, List<AlbumInfo> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public View getInflatedView(LayoutInflater inflater, DialogPlus dialogPlus) {
       mContentContainer = inflater.inflate(R.layout.dialog_select_photo, null);
       init(mContentContainer,dialogPlus);
       return mContentContainer;
    }

    private void init(View view, final DialogPlus dialogPlus) {
        final RecyclerView        recyclerView =view.findViewById(R.id.recycler_select);
        final SelectPhotosAdapter adapter      =new SelectPhotosAdapter(mContext, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SelectPhotosAdapter.OnItemClickListener() {
            @Override
            public void clickItem(int lastPosition, final int currentPosition, AlbumInfo info) {

                adapter.notifyDataChange(lastPosition,currentPosition);
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogPlus.dismiss();
                        if(mSelectAlbumListener!=null){
                            mSelectAlbumListener.selectAlbum(list.get(currentPosition));
                        }
                    }
                },500);

            }
        });

    }
    public ISelectAlbumListener mSelectAlbumListener;
    public void setSelectAlbumListener(ISelectAlbumListener selectAlbumListener){
        mSelectAlbumListener=selectAlbumListener;
    }
   public interface ISelectAlbumListener{
        void selectAlbum(AlbumInfo albumInfo);
   }


}

package com.DLUT.ThunderFire.Sys.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.DLUT.ThunderFire.Sys.R;
import com.DLUT.ThunderFire.Sys.bean.ImageBean;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * GridView的适配器
 */
public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<ImageBean> listitem;
    private  OnImageClickListener onImageClickListener;

    public GridViewAdapter(Context context) {
        this.context = context;
    }

    public GridViewAdapter(Context context, List<ImageBean> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    public List<ImageBean> getListitem() {
        return listitem;
    }

    public void setListitem(List<ImageBean> listitem) {
        this.listitem = listitem;
    }

    @Override
    public int getCount() {
        return listitem.size();
    }

    @Override
    public Bitmap getItem(int position) {
        return listitem.get(position).getImage();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.pic_items,null);
        }
        ImageView imageView = convertView.findViewById(R.id.image);
        FloatingActionButton signButton = convertView.findViewById(R.id.sign);
        signButton.setBackgroundColor(Color.red(255));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onImageClickListener.onImageClick(position);
            }
        });
        ViewGroup.LayoutParams params;
        params = imageView.getLayoutParams();
        params.height = 418;
        params.width = 500;
        imageView.setLayoutParams(params);
        ImageBean image = listitem.get(position);
        imageView.setImageBitmap(image.getImage());
        if( image.getState()){
            imageView.setAlpha(85);
        }
        return convertView;
    }



    public  void setOnImageClickListener(OnImageClickListener onImageClickListener){
        this.onImageClickListener = onImageClickListener;
    }

    /**
     * 监听回调接口
     */
    public interface OnImageClickListener {
        void onImageClick(int position);
    }


}

package com.DLUT.ThunderFire.Sys.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.DLUT.ThunderFire.Sys.R;
import com.DLUT.ThunderFire.Sys.adapter.GridViewAdapter;
import com.DLUT.ThunderFire.Sys.base.BaseActivity;
import com.DLUT.ThunderFire.Sys.bean.ImageBean;
import com.DLUT.ThunderFire.Sys.contract.ShowPicContract;
import com.DLUT.ThunderFire.Sys.presenter.ShowPicPresenter;
import com.DLUT.ThunderFire.Sys.utils.FileUtils;
import com.DLUT.ThunderFire.Sys.view.MyGridView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 展示页面
 */
public class ShowPicActivity extends BaseActivity<ShowPicPresenter> implements ShowPicContract.ShowPicView {
    @BindView(R.id.pic_grid)
    MyGridView gridView;
    @BindView(R.id.next)
    Button nextButton;
    @BindView(R.id.last)
    Button lastButton;
    @BindView(R.id.exit)
    ImageButton exitButton;

    private GridViewAdapter adapter;
    private List<ImageBean> picList =new ArrayList<>(); //图片列表
    private Context context;
    private final int COUNT = 6;//每页显示的图片数目
    private final float WIDTH = 500;
    private final float HEIGHT = 418;
    public   int time = 0;
    private List<String> imagePath;


    @Override
    protected ShowPicPresenter loadPresenter() {
        return new ShowPicPresenter();
    }

    @Override
    protected void initData() {

       getImagePath();
        getAllImages();
        getItemList(new ArrayList<>());
    }

    /**
     * 获取文件夹下的所有图片路径
     */
    private  void getImagePath(){
        File imageDir = new File(Environment.getExternalStorageDirectory(),"src"); //读取摄像机中的图片存储到该文件夹下
        /**
         * 判断路径是否存在
         */
        if (!imageDir.exists()){
            imageDir.mkdirs();

        }
        this.imagePath = FileUtils.getFilesAllNames(imageDir.getAbsolutePath());

    }

    @Override
    protected void initListener() {
        //下一批
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (time >= picList.size()/COUNT - 1){
                    nextButton.setBackgroundColor(getResources().getColor(R.color.buttonRed));
                }
                time += 1;
                lastButton.setBackgroundColor(getResources().getColor(R.color.buttonGreen));
               getItemList(new ArrayList<>());

            }
        });
        //上一批
        lastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(time > 0){
                    time -= 1;
                    nextButton.setBackgroundColor(getResources().getColor(R.color.buttonGreen));

                }
                if (time <= 0){
                    lastButton.setBackgroundColor(getResources().getColor(R.color.buttonRed));
                }

                getItemList(new ArrayList<>());
            }
        });

        //退出对话框
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExitDialog exitDialog = new ExitDialog(ShowPicActivity.this);
                exitDialog.setTitle("提示");
                exitDialog .setMessage("确定退出应用吗？");
                exitDialog.setYesOnclickListener("确定", new ExitDialog.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        exitDialog.dismiss();
                        finish();
                    }
                });
                exitDialog.setNoOnclickListener("取消", new ExitDialog.onNoOnclickListener() {
                    @Override
                    public void onNoClick() {
                        exitDialog.dismiss();
                    }
                });
                exitDialog.show();
            }
        });

        adapter.setOnImageClickListener(new GridViewAdapter.OnImageClickListener() {
            @Override
            public void onImageClick(int position) {
                int cur = time * COUNT + position;
                picList.get(cur).setState(true);
                saveImage(context,picList.get(cur).getImage());
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyStoragePermissions(this);
        //mhander.post(run);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        this.context = ShowPicActivity.this;
        this.adapter = new GridViewAdapter(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.show_pic;
    }

    @Override
    protected void otherViewClick(View view) {
    }


    @Override
    public void getItemList( List<Bitmap> itemList) {

        getLocalPicture(time);


    }

    /**
     * 实现图片缩放
     * @param bitmap
     * @return
     */
    private  Bitmap scalePicture(Bitmap bitmap){
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        float scaleW = WIDTH/w;
        float scaleH =  HEIGHT/h;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleW, scaleH); // 长和宽放大缩小的比例
        return Bitmap.createBitmap(bitmap,0,0,w,h,matrix,true); //实现图片缩放
    }

    /**
     * 加载所有图片
     */
    private void getAllImages(){

        for (int i = 0; i < imagePath.size(); i++){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath.get(i));
            this.picList.add(new ImageBean(scalePicture(bitmap),false));
        }
    }

    /**
     * 获取当前页数据
     * @param index
     */

    private  void getLocalPicture(int index){
        if (index > picList.size() / COUNT){
            index = picList.size() / COUNT ;
            time = index;
        }
        if(picList.size() == 0){
            Toast.makeText(context,"没有数据",Toast.LENGTH_SHORT).show();
            return;
        }
        int begin =  index * COUNT;
        int end = begin + COUNT;
        if (end >= picList.size()-1){
            end = picList.size() ;
            Toast.makeText(context,"最后一批数据了",Toast.LENGTH_SHORT).show();
        }
       setAdapter(begin,end);

    }

    private  void setAdapter(int begin,int end){
        adapter.setListitem(picList.subList(begin,end));
        gridView.setAdapter(adapter);

    }

    /***
     * 保存图片到本地
     * @param context
     * @param bitmap
     */
    public static void saveImage(Context context, Bitmap bitmap){
        File imageDir = new File(Environment.getExternalStorageDirectory(),"test");
        /**
         * 判断路径是否存在
         */
        if (!imageDir.exists()){
            imageDir.mkdirs();
        }
        String fileName = System.currentTimeMillis()+".jpg";

        File image = new File(imageDir,fileName);
        if(image.exists()){
           System.out.println("图片存在");
        }
        try {

            FileOutputStream fos = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
            if(bitmap == null){
                Toast.makeText(context,"图片不存在",Toast.LENGTH_SHORT).show();
                return;
            }
            fos.flush();
            fos.close();
            Toast.makeText(context,"图片保存在："+ image.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        }catch (FileNotFoundException e){
            Toast.makeText(context,"保存失败！",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    /**
     * 获取存储权限
     */
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "   "};


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission_write = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission_write != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
            //检测是否有读的权限
            int permission_read = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.READ_EXTERNAL_STORAGE");
            if (permission_read != PackageManager.PERMISSION_GRANTED) {
                // 没有读的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 实现自动刷新
     * @param itemList
     */

//    private Handler mhander = new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            super.handleMessage(msg);
//
//        }
//    };


//    /**
//     * 设置系统时间
//     */
//    private Runnable run = new Runnable() {
//        @Override
//        public void run() {
//
//            //注释部分是实现网络图片获取，还有点问题
////        String path= "148299.html";
////        mPresenter.showItemList(path);
//            Count += 1;
//
//            if(Count % 2 == 0){
//                getItemListRefresh(new ArrayList<>());
//            }else{
//                getItemList(new ArrayList<>());
//            }
//
//            mhander.postDelayed(run, 2000);
//
//        }
//    };



}

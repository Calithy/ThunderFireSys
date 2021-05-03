package com.DLUT.ThunderFire.Sys.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *图片保存的工具类
 */
public class FileUtils {
    /**
     * 为日后开发使用
     * 判断指定路径是否存在
     * @param fileName
     * @return
     */
    static boolean fileIsExist(String fileName){
        File file = new File(fileName);
        if (file.exists())
            return true;
        else{
            //file.mkdirs() 创建文件夹的意思
            return file.mkdirs();
        }
    }

    public  static List<String> getFilesAllNames(String path){
        //传入指定文件夹的路径
        File  file = new File(path);
        File[] files = file.listFiles();
        List<String> imagePaths = new ArrayList<>();
        for (int i = 0; i < files.length;i++){
            if(checkIsImageFile(files[i].getPath())){
                imagePaths.add(files[i].getPath());
            }
        }
        return imagePaths;
    }

    /**
       * 判断是否是照片
      */
    public static boolean checkIsImageFile(String fName){
        boolean isImageFile = false;
         //获取拓展名
        String fileEnd = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
        if(fileEnd.equals("jpg") || fileEnd.equals("png") || fileEnd.equals("gif")
                || fileEnd.equals("jpeg")|| fileEnd.equals("bmp")){
            isImageFile = true;
        }else{
            isImageFile = false;
        }
        return isImageFile;
    }
}

package com.mirkowu.fuckgoods.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.mirkowu.fuckgoods.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/10/17 0017.
 */

public class FileUtil {
    public static final String ROOTPATH = "LovesGarden";
    public static String SAVEFILEPATH_RING = ROOTPATH + "/ring";

    public static File getRingCacheDir() {
        File RootDir;
        String status = Environment.getExternalStorageState();
        //判断是否有SD卡
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            //   if (RootDir != null) return RootDir;
            RootDir = new File(Environment.getExternalStorageDirectory(), SAVEFILEPATH_RING);
            if (!RootDir.exists()) RootDir.mkdirs();
            return RootDir;
        } else {
            ToastUtil.s(R.string.no_found_sdcard);
        }
        return null;

    }

    //判断文件是否存在
    public static boolean fileIsExists(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 复制assets文件 到sd卡
     *
     * @param context
     */
    public static void copyFileOrDir(Context context, String oldpath, String newpath) {
        //检测sdcard、
        File file = getRingCacheDir();
        if (file == null) {
            return;
        }
        AssetManager assetManager = context.getAssets();
        String assets[] = null;
        try {
            assets = assetManager.list(oldpath);
            if (assets.length == 0) {//如果是文件
                //复制前判断是否存在
                if (!fileIsExists(newpath) && oldpath.trim().toLowerCase().endsWith(".mp3"))//若不存在则复制
                    copyFile(context, oldpath, newpath);
            } else {//如果是文件夹
                File dir = new File(newpath);
                if (!dir.exists())
                    dir.mkdir();
                for (int i = 0; i < assets.length; ++i) {
                    copyFileOrDir(context, TextUtils.isEmpty(oldpath) ? assets[i] : oldpath + "/" + assets[i], newpath + "/" + assets[i]);
                }
            }
        } catch (IOException ex) {
            Log.e("tag--copyFileOrDir", "I/O Exception", ex);
        }
    }

    public static void copyFile(Context context, String oldpath, String newpath) {
        Log.e("tag--copyfilepath", oldpath + "-------|||||||||-------" + newpath);
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = context.getAssets().open(oldpath);
            out = new FileOutputStream(new File(newpath));

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            out.flush();
            in.close();
            in = null;
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("tag--copyfile", e.getMessage());
            e.printStackTrace();
        }
    }


    // 获取当前目录下所有的mp4文件
    public static List<String> getRingFileName(File file) {
        List<String> vecFile = new ArrayList<>();
        // File file = new File(fileAbsolutePath);
        File[] subFile = file.listFiles();

        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                // 判断是否为MP3结尾
                if (filename.trim().toLowerCase().endsWith(".mp3")) {
                    vecFile.add(filename);
                }
            }
        }
        return vecFile;
    }


}

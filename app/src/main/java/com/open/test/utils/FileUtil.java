package com.open.test.utils;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by long on 2016/9/5.
 */

public class FileUtil {

    public static byte[] readFile(String filePath)
    {
        try
        {
            if (isFileExist(filePath))
            {
                FileInputStream fi = new FileInputStream(filePath);
                return readFileStream(fi);
            }
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] readFileStream(InputStream in)
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try
        {
            byte[] b = new byte[in.available()];
            int length = 0;
            while ((length = in.read(b)) != -1)
            {
                os.write(b, 0, length);
            }
            b = os.toByteArray();
            return b;

        } catch (IOException e)
        {
            e.printStackTrace();
        }finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean isFileExist(String filePath)
    {
        File file = new File(filePath);
        return file.exists();
    }

    public static boolean writeFile(String destFilePath, InputStream in)
    {
        try
        {
            if (!createFile(destFilePath))
            {
                return false;
            }
            FileOutputStream fos = new FileOutputStream(destFilePath);
            int readCount = 0;
            int len = 1024;
            byte[] buffer = new byte[len];
            while ((readCount = in.read(buffer)) != -1)
            {
                fos.write(buffer, 0, readCount);
            }
            fos.flush();
            if (null != fos)
            {
                fos.close();
                fos = null;
            }
            if (null != in)
            {
                in.close();
                in = null;
            }
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean createFile(String filePath)
    {
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                if (!file.getParentFile().exists())
                {
                    file.getParentFile().mkdirs();
                }

                return file.createNewFile();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 删除一个文件
     * @param filePath 要删除的文件路径名
     * @return true if this file was deleted, false otherwise
     */
    public static boolean deleteFile(String filePath)
    {
        File file=new File(filePath);
        if(file.exists())
        {
            return file.delete();
        }
        return false;
    }

    //-------------------------------------------------
    public static boolean appendPushMessage(Context mContext , String message)
    {
        if(null != message){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);
            String fileName = SDCardUtil.getDiskCacheDir(mContext) + String.format("push/push_%s.txt",dateString);
            byte[] msgBytes = message.getBytes();
            FileUtil.appendFile(fileName,msgBytes,0,msgBytes.length);
        }
        return true;
    }

    public static boolean appendServerMessage(Context mContext , String message)
    {
        if(null != message){
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = formatter.format(date);
            String fileName = SDCardUtil.getDiskCacheDir(mContext) + String.format("server/server_%s.txt",dateString);
            byte[] msgBytes = message.getBytes();
            FileUtil.appendFile(fileName,msgBytes,0,msgBytes.length);
        }
        return true;
    }

    public static boolean appendFile(String filename,byte[]data,int dataPos,int dataLength)
    {
        try {

            createFile(filename);

            RandomAccessFile rf= new RandomAccessFile(filename, "rw");
            rf.seek(rf.length());
            rf.write(data, dataPos, dataLength);
            if(rf!=null)
            {
                rf.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

}

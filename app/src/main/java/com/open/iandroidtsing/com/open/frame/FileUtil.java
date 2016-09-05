package com.open.iandroidtsing.com.open.frame;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
}

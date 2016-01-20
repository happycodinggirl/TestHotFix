package cn.plu.testhotfix;

import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lily on 16-1-19.
 */
public class HttpDownloader {

    FileDownloadListener fileDownloadListener;



    private URL url = null;


    /**
     * 根据URL下载文件,前提是这个文件当中的内容是文本,函数的返回值就是文本当中的内容
     * 1.创建一个URL对象
     * 2.通过URL对象,创建一个HttpURLConnection对象
     * 3.得到InputStream
     * 4.从InputStream当中读取数据
     * @param urlStr
     * @return
     */
    public String download(String urlStr,File destFile){
        StringBuffer sb = new StringBuffer();
        BufferedOutputStream   bufferedOutputStream=null;
        try {
         bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(destFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int readed = 0;
        BufferedReader buffer = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
            buffer = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            while( (readed = buffer.read())!=0){
               // sb.append(line);
                bufferedOutputStream.write(readed);
            }
            if (fileDownloadListener!=null){
                fileDownloadListener.onFileDownLoaded();
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try {
                buffer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public void setFileDownloadListener(FileDownloadListener fileDownloadListener) {
        this.fileDownloadListener = fileDownloadListener;
    }


    public  File downLoadFile(String httpUrl,File file) {
        // TODO Auto-generated method stub

        try {
            URL url = new URL(httpUrl);

            HttpURLConnection conn = (HttpURLConnection) url
                    .openConnection();
            InputStream is = conn.getInputStream();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[256];
            conn.connect();
            double count = 0;
            if (conn.getResponseCode() >= 400) {
//                                  Toast.makeText(DownFile.this, "连接超时", Toast.LENGTH_SHORT)
//                                                  .show();
                Log.i("time", "time exceed");
            } else {

                while (count <= 100) {
                    if (is != null) {
                        int numRead = is.read(buf);
                        if (numRead <= 0) {
                            if (fileDownloadListener != null) {
                                fileDownloadListener.onFileDownLoaded();
                            }
                            break;
                        } else {
                            fos.write(buf, 0, numRead);
                        }
                    } else {
                        break;
                    }
                }


            }
            conn.disconnect();
            fos.close();
            is.close();

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        Log.v("PLU", "--MalformedURLException");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.v("PLU","----file not found exception");

        } catch (IOException e) {
            e.printStackTrace();
            Log.v("PLU","----IOEXCEPTION");
        }
        return file;
    }

    /**
     *
     * @param urlStr
     * @param path
     * @param fileName
     * @return
     *      -1:文件下载出错
     *       0:文件下载成功
     *       1:文件已经存在
     */
    public int downFile(String urlStr, String path, String fileName){
        InputStream inputStream = null;
        try {
            FileUtils fileUtils = new FileUtils();

            if(fileUtils.isFileExist(path + fileName)){
                return 1;
            } else {
                inputStream = getInputStreamFromURL(urlStr);
                File resultFile = fileUtils.write2SDFromInput(path, fileName, inputStream);
                if(resultFile == null){
                    return -1;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        finally{
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 根据URL得到输入流
     * @param urlStr
     * @return
     */
    public InputStream getInputStreamFromURL(String urlStr) {
        HttpURLConnection urlConn = null;
        InputStream inputStream = null;
        try {
            url = new URL(urlStr);
            urlConn = (HttpURLConnection)url.openConnection();
            inputStream = urlConn.getInputStream();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inputStream;
    }

    public interface FileDownloadListener{
        void onFileDownLoaded();
    }
}

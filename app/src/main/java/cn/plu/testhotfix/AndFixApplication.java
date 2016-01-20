package cn.plu.testhotfix;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import com.alipay.euler.andfix.patch.PatchManager;

import java.io.IOException;

/**
 * Created by lily on 16-1-19.
 */
public class AndFixApplication extends Application {
    private static final String APATCH_PATH = "/lily.apatch";
    private PatchManager patchManager;
    @Override
    public void onCreate() {
        super.onCreate();
      patchManager = new PatchManager(this);
        patchManager.init("1.0");
        Log.v("PLU", "-----application onCreate");
        // load patch
        patchManager.loadPatch();
       /* File sdcardFile=Environment.getExternalStorageDirectory();
       // final File patchFile=new File(sdcardFile,"new.patch");
       // String patchFileString = patchFile.getAbsolutePath();
        String patchFileString = sdcardFile.getAbsolutePath()+APATCH_PATH;
        Log.e("TAG", "patch file is " + patchFileString);
        try {
            patchManager.addPatch(patchFileString);
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("PLU","-----IOException");
        }*/

        final HttpDownloader downloader = new HttpDownloader();
        File sdcardFile=Environment.getExternalStorageDirectory();
        final File patchFile=new File(sdcardFile,"lily1.apatch");
        if (!patchFile.exists()){
            try {
                patchFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                Log.v("PLU","---create new file success");
            }
        }else{
            Log.v("PLU","-----FILE EXSIT");
            String patchFileString = patchFile.getAbsolutePath();
            Log.e("TAG", "patch file is " + patchFileString);
            try {
                patchManager.addPatch(patchFileString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        /*new Thread(new Runnable() {
            @Override
            public void run() {
                downloader.setFileDownloadListener(new HttpDownloader.FileDownloadListener() {
                    @Override
                    public void onFileDownLoaded() {
                        Log.v("PLU","---onFileDownloaded");
                        try {
                            // .apatch file path
                            String patchFileString = patchFile.getAbsolutePath();
                            Log.e("TAG", "patch file is " + patchFileString);
                            patchManager.addPatch(patchFileString);
                        } catch (IOException e) {
                            Log.v("PLU","---patch manager addPatch fail");
                        }
                    }
                });
                downloader.downLoadFile("http://file.bmob.cn/M02/53/E1/oYYBAFafE4KAKPGoAAALzVfip-8.apatch", patchFile);

            }
        }).start();*/

        //System.out.println(lrc);

        // add patch at runtime

    }
}

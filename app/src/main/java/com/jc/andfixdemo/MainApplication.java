package com.jc.andfixdemo;

import android.app.Application;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.alipay.euler.andfix.patch.Patch;
import com.alipay.euler.andfix.patch.PatchManager;
import com.alipay.euler.andfix.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by solar on 2016/7/8.
 */
public class MainApplication extends Application {

    private static final String TAG = "MainApplication";

    private static final String APATCH_PATH = "/out.apatch";

    private static final String DIR = "apatch";//补丁文件夹

    public PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mPatchManager = new PatchManager(this);
        mPatchManager.init("1.0");
        mPatchManager.loadPatch();

        String patchFileString = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + APATCH_PATH;
        Toast.makeText(this, "out.apatch是否存在:"+new File(patchFileString).exists(), Toast.LENGTH_SHORT).show();

        try {
            ////注意在清单文件中得加 文件读写 权限
            mPatchManager.addPatch(patchFileString);
            File f = new File(this.getFilesDir(), DIR + APATCH_PATH);
            if (f.exists()) {
                boolean result = new File(patchFileString).delete();
                if (!result)
                    Log.e(TAG, patchFileString + " delete fail");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

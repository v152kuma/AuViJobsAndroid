package com.auvi.util;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;

import com.auvi.firebaseNotification.CSNotificationManager;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by ashokkateshiya on 23/02/18.
 *
 *
 */

public class CSImageUtils {

    public static File downloadImage(AppCompatActivity context, ImageView iv, String imageName) {
        if(!TextUtils.isEmpty(imageName)) {
            BitmapDrawable draw = (BitmapDrawable) iv.getDrawable();
            Bitmap bitmap = draw.getBitmap();
            try {
                FileOutputStream outStream = null;
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/Camera");
                File outFile = new File(dir, imageName);
                if (!fileExist(dir, imageName)) {
                    outFile = new File(dir, imageName);
                    outStream = new FileOutputStream(outFile);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                }
                new CSNotificationManager(context).notifySuccess(context);
                return outFile;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean fileExist(File directory, String fileName) {
        File file = new File(directory, fileName);
        return file.exists();
    }

}

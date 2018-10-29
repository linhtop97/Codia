package com.example.myteam.codia.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;

public class ImageUtils {

    public static Bitmap base64toImage(String encodeImage) {
        byte[] decodedString = Base64.decode(encodeImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    public static String BitMapToString(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] b = baos.toByteArray();

        String temp = null;

        try {

            System.gc();

            temp = Base64.encodeToString(b, Base64.DEFAULT);

        } catch (Exception e) {

            e.printStackTrace();

        } catch (OutOfMemoryError e) {

            baos = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            b = baos.toByteArray();

            temp = Base64.encodeToString(b, Base64.DEFAULT);

            Log.e("EWN", "Out of memory error catched");

        }

        return temp;

    }
}

package com.exigency.exigencycare.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

public class ImageOperationUtil {

    private final int IMG_WIDTH = 600;
    private final int IMG_HEIGHT = 800;

    private Activity mActivity;
    private Bitmap mBitmap;

    private static final String APP_NAME = "DroozoApp";
    public static final String Application_Directory = Environment.getExternalStorageDirectory() + "/" + APP_NAME;
    public static String CAPTURED_IMAGE_PATH = Application_Directory + "/"+ "123.png";

    public ImageOperationUtil(Activity activity) {
        mActivity = activity;
        initializeDir();
    }

    public Bitmap getBitmapFromPath(String filepath) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        mBitmap = loadBitmap(
                filepath,
                getCameraPhotoOrientation(mActivity.getApplicationContext(),
                        filepath), IMG_WIDTH, IMG_HEIGHT);
        return mBitmap;
    }

    // Create this function save all the images in sd card folder
    public void saveBitmaptoCard(Bitmap bitmap, String imageName) {

        File tempfile = null;

        try {

            tempfile.createNewFile();
        } catch (IOException e) {
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 0, bos);

        byte[] bitmapdata = bos.toByteArray();
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(tempfile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bitmapdata);

            fos.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * initializeDir
     **/
    private void initializeDir() {

        try {

            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {

                Log.d("Application Directory ", Application_Directory);

                File AppDirectory = new File(Application_Directory);

                /**
                 * Check If Application Working directory exists or not. If
                 * exists then ok , otherwise it will create a Directory.
                 * */
                if (!AppDirectory.exists()) {

                    AppDirectory.mkdirs();
                }

            }

        } catch (Exception e) {

            Log.e("getImageFromSdCard", e.toString());
        }
    }

    private Bitmap loadBitmap(String path, int orientation,
                              final int targetWidth, final int targetHeight) {

        Bitmap bitmap = null;

        try {

            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);

            // Adjust extents
            int sourceWidth, sourceHeight;
            if (orientation == 90 || orientation == 270) {

                sourceWidth = options.outHeight;
                sourceHeight = options.outWidth;

            } else {

                sourceWidth = options.outWidth;
                sourceHeight = options.outHeight;
            }

            // Calculate the maximum required scaling ratio if required and load
            // the bitmap
            if (sourceWidth > targetWidth || sourceHeight > targetHeight) {

                float widthRatio = (float) sourceWidth / (float) targetWidth;
                float heightRatio = (float) sourceHeight / (float) targetHeight;
                float maxRatio = Math.max(widthRatio, heightRatio);

                options.inJustDecodeBounds = false;
                options.inSampleSize = (int) maxRatio;

                bitmap = BitmapFactory.decodeFile(path, options);

            } else {

                bitmap = BitmapFactory.decodeFile(path);
            }

            // Rotate the bitmap if required
            if (orientation > 0) {

                Matrix matrix = new Matrix();
                matrix.postRotate(orientation);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), matrix, true);

            }

            // Re-scale the bitmap if necessary
            sourceWidth = bitmap.getWidth();
            sourceHeight = bitmap.getHeight();

            if (sourceWidth != targetWidth || sourceHeight != targetHeight) {

                float widthRatio = (float) sourceWidth / (float) targetWidth;
                float heightRatio = (float) sourceHeight / (float) targetHeight;
                float maxRatio = Math.max(widthRatio, heightRatio);

                sourceWidth = (int) ((float) sourceWidth / maxRatio);
                sourceHeight = (int) ((float) sourceHeight / maxRatio);
                bitmap = Bitmap.createScaledBitmap(bitmap, sourceWidth,
                        sourceHeight, true);
            }
        } catch (Exception e) {

            Log.e("In LoadBitmap ", e.toString());
        }
        return bitmap;
    }

    private int getCameraPhotoOrientation(Context context, String imagePath) {

        int rotate = 0;

        try {

            File imageFile = new File(imagePath);
            ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }

            Log.v("ORIENTATION", "Exif orientation: " + orientation);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rotate;
    }

    public static String BitMapTo_Base64_String(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}

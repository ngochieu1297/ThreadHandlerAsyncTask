package com.example.admin.threadhandlerasynctask;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class PhotoStorage extends AsyncTask<String, Void, List<Photo>> {
    private static final String TAG = "PhotoStorage";
    public static final String[] EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif"};

    private OnLoadDataSuccess mOnLoadDataSuccess;

    public void setOnLoadDataSuccess(OnLoadDataSuccess onLoadDataSuccess) {
        mOnLoadDataSuccess = onLoadDataSuccess;
    }

    public static List<Photo> loadPhotos(String path) {
        List<Photo> photos = new ArrayList<>();
        File directory = new File(path);
        File[] files = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                for (String extension : EXTENSIONS) {
                    if (file.getName().toLowerCase().endsWith(extension)) return true;
                }
                return false;
            }
        });
        for (File file : files) {
            photos.add(new Photo(file.getName(), file.getPath()));
        }

        return photos;
    }

    @Override
    protected List<Photo> doInBackground(String... strings) {
        Log.d(TAG, "doInBackground: " + loadPhotos(strings[0]).size());
        return loadPhotos(strings[0]);
    }

    @Override
    protected void onPostExecute(List<Photo> photos) {
        mOnLoadDataSuccess.onSuccess(photos);
        super.onPostExecute(photos);
    }
}

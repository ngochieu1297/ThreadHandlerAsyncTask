package com.example.admin.threadhandlerasynctask;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnLoadDataSuccess {
    public static final String DEFAULT_PATH = "storage/emulated/0/DCIM/Screenshots";
    private RecyclerView mRecyclerPhotos;
    private List<Photo> mPhotos = new ArrayList<>();
    private PhotoAdapter mPhotoAdapter;
    public static final int REQUEST_READ_EXTERNAL = 1;
    public static int width;
    public static final int spanCount = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        loadData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_EXTERNAL:
                checkResult(grantResults);
                break;
            default:
                break;

        }
    }

    private void checkResult(int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_DENIED) {
            PhotoStorage.loadPhotos(DEFAULT_PATH);
        } else {
            loadData();
        }
    }

    private void loadData() {
        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, permissions[0])
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, REQUEST_READ_EXTERNAL);
        } else {
            PhotoStorage photoStorage = new PhotoStorage();
            photoStorage.setOnLoadDataSuccess(this);
            photoStorage.execute(DEFAULT_PATH);
        }
    }

    private void initView() {
        mRecyclerPhotos = findViewById(R.id.recycler_view_photo);
        mPhotoAdapter = new PhotoAdapter(mPhotos);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),
                spanCount, GridLayoutManager.VERTICAL, false);
        mRecyclerPhotos.setLayoutManager(gridLayoutManager);
        mRecyclerPhotos.setAdapter(mPhotoAdapter);
        width = getWindowManager().getDefaultDisplay().getWidth() / 2;
    }

    @Override
    public void onSuccess(List<Photo> photos) {
        mPhotos.addAll(photos);
    }
}

package com.example.admin.threadhandlerasynctask;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private List<Photo> mPhotos;
    public static final int mPhotoHeight = 800;

    public PhotoAdapter(List<Photo> photos) {
        mPhotos = photos;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_photo, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        holder.setPhotos(mPhotos.get(position));
    }

    @Override
    public int getItemCount() {
        return mPhotos.size() > 0 ? mPhotos.size() : 0;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view_photo);
        }

        private void setPhotos(Photo photo) {
            Glide.with(itemView.getContext())
                    .load(photo.getUrl())
                    .apply(new RequestOptions().override(MainActivity.width / 2, mPhotoHeight))
                    .into(mImageView);
        }
    }
}

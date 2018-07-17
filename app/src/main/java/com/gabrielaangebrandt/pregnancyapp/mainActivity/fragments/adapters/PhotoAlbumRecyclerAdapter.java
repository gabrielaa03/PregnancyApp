package com.gabrielaangebrandt.pregnancyapp.mainActivity.fragments.adapters;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gabrielaangebrandt.pregnancyapp.R;
import com.gabrielaangebrandt.pregnancyapp.models.data_models.PhotoShare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoAlbumRecyclerAdapter extends RecyclerView.Adapter<PhotoAlbumRecyclerAdapter.PhotoAlbumViewHolder> {
    private List<PhotoShare> images = new ArrayList<>();

    public PhotoAlbumRecyclerAdapter(List<PhotoShare> imageMaps) {
        images = imageMaps;
    }

    @Override
    public PhotoAlbumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false);
        return new PhotoAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoAlbumViewHolder holder, int position) {
        PhotoShare photoShare = images.get(position);
        holder.username.setText(photoShare.getComment());
        byte[] decodedByteArray = android.util.Base64.decode(photoShare.getImage(), Base64.DEFAULT);
        holder.photo.setImageBitmap(BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class PhotoAlbumViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ib_photo)
        ImageButton photo;

        @BindView(R.id.tv_username)
        TextView username;

        @BindView(R.id.ib_like)
        ImageButton like;

        PhotoAlbumViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick
        public void onClick() {
        }
    }
}

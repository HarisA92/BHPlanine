package com.example.user.graduationproject.Bjelasnica.ViewPager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.graduationproject.Bjelasnica.Utils.GalleryImageHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageViewer extends PagerAdapter {
    private Context context;
    private ArrayList<GalleryImageHolder> list;

    public ImageViewer(Context context, ArrayList<GalleryImageHolder> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        GalleryImageHolder galleryImageHolder = list.get(position);
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //imageView.setImageResource(Integer.parseInt(list.get(position).getImageId()));
        /*Picasso.with(context)
                .load(list.get(position))
                .resize(width, height)
                .centerCrop()
                .into(imageView);*/
        Picasso.with(context).load(galleryImageHolder.getImageId()).into(imageView);
        container.addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

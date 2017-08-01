package com.benayah.app.trackmypet.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.benayah.app.trackmypet.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by User on 31-07-2017.
 */

public class ViewPagerAdapter extends PagerAdapter {

    Context context;
    ArrayList<String> selectedImageList;
    private DisplayImageOptions options;

    public ViewPagerAdapter(Context context, ArrayList<String> selectedImageList)
    {
        this.context = context;
        this.selectedImageList = selectedImageList;

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .resetViewBeforeLoading(true).cacheOnDisk(true)
                .considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return selectedImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View removableView = (View) object;
        container.removeView(removableView);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_viewpager_layout,container,false);
        ImageView selectedImage = (ImageView) view.findViewById(R.id.selected_image);
        ImageLoader.getInstance().displayImage("file://" + selectedImageList.get(position), selectedImage, options);

        container.addView(view);
        return view;


    }
}

package com.benayah.app.trackmypet.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.benayah.app.trackmypet.CustomGallery_Activity;
import com.benayah.app.trackmypet.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;


public class GridView_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> imageUrls;
    private SparseBooleanArray mSparseBooleanArray;//Variable to store selected Images
    private ArrayList<String> mTempArry = new ArrayList();
    private DisplayImageOptions options;
    private ImageLoader imageLoader;
    //private boolean isCustomGalleryActivity;//Variable to check if gridview is to setup for Custom Gallery or not

    public GridView_Adapter(Context context, ArrayList<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        //this.isCustomGalleryActivity = isCustomGalleryActivity;
        mSparseBooleanArray = new SparseBooleanArray();


        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .resetViewBeforeLoading(true).cacheOnDisk(true)
                .considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        mTempArry.clear();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    //Method to return selected Images
    public ArrayList<String> getCheckedItems() {
        //ArrayList<String> mTempArry = new ArrayList<String>();
        //Log.d("mTempArry ",mTempArry.size()+"");
        /*String path = null;
        for (int i = 0; i < imageUrls.size(); i++) {
            if (mSparseBooleanArray.get(i)) {
                //mTempArry.add(imageUrls.get(i));
                path = imageUrls.get(i);
                return path;
            }
        }*/
        for (int i = 0; i < mTempArry.size(); i++) {
            System.out.println(mTempArry);
        }



        return mTempArry;
    }



    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int i) {
        return imageUrls.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = inflater.inflate(R.layout.customgridview_item, viewGroup, false);//Inflate layout

         final ImageView mCheckBox = (ImageView) view.findViewById(R.id.select_checkbox);
         ImageView imageView = (ImageView) view.findViewById(R.id.gallery_image_view);
         mCheckBox.setVisibility(View.GONE);

        //If Context is MainActivity then hide checkbox
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if(mCheckBox.getVisibility() == View.VISIBLE)
                {
                    mCheckBox.setVisibility(View.GONE);

                }*/
                //System.out.println("position = "+position+" &&"+"mTempArry.size() = "+mTempArry.size());


                if(mCheckBox.getVisibility() == View.GONE)
                {


                    if(mTempArry.size() < 4)
                    {
                        mCheckBox.setVisibility(View.VISIBLE);
                        mTempArry.add(imageUrls.get(position));
                        //System.out.println("mTempArry.size() = "+mTempArry.size());
                    }
                    else
                    {
                        Toast.makeText(context,"Sorry..You can select only maximum of four images", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    String uri = imageUrls.get(position);

                    mTempArry.remove(uri);
                    mCheckBox.setVisibility(View.GONE);
                }

                ((CustomGallery_Activity) context).showSelectButton();//call custom gallery activity method
            }
        });
        imageLoader.displayImage("file://" + imageUrls.get(position), imageView, options);//Load Images over ImageView


        return view;
    }


}

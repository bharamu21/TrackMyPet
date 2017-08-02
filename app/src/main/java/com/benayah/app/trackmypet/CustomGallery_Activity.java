package com.benayah.app.trackmypet;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.benayah.app.trackmypet.Adapters.GridView_Adapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by SONU on 31/10/15.
 */
public class CustomGallery_Activity extends AppCompatActivity implements View.OnClickListener {
    private  Button selectImages;
    private  GridView galleryImagesGridView;
    private  ArrayList<String> galleryImageUrls;
    private  GridView_Adapter imagesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customgallery_activity);
        initViews();
        setListeners();
        fetchGalleryImages();
        setUpGridView();
    }

    //Init all views
    private void initViews() {
        selectImages = (Button) findViewById(R.id.selectImagesBtn);
        galleryImagesGridView = (GridView) findViewById(R.id.galleryImagesGridView);

    }

    //fetch all images from gallery
    private void fetchGalleryImages() {
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};//get all columns of type images
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;//order data by date
        Cursor imagecursor = managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                null, orderBy + " DESC");//get all data in Cursor by sorting in DESC order

        galleryImageUrls = new ArrayList<String>();//Init array


        //Loop to cursor count
        for (int i = 0; i < imagecursor.getCount(); i++) {
            imagecursor.moveToPosition(i);
            int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);//get column index
            System.out.println("Array path = "+i+" , "+ imagecursor.getString(dataColumnIndex));
            galleryImageUrls.add(imagecursor.getString(dataColumnIndex));//get Image from column index

        }


    }

    //Set Up GridView method
    private void setUpGridView() {
        imagesAdapter = new GridView_Adapter(CustomGallery_Activity.this, galleryImageUrls);
        galleryImagesGridView.setAdapter(imagesAdapter);

        //convertToBase64(galleryImageUrls.get(0));
    }

    //Set Listeners method
    private void setListeners() {
        selectImages.setOnClickListener(this);
    }


    //Show hide select button if images are selected or deselected
    public void showSelectButton() {

        ArrayList<String> selectedItems = imagesAdapter.getCheckedItems();
        //Log.d("i = ",selectedItems.size()+"");
        if (selectedItems.size() > 0) {

            selectImages.setText(selectedItems.size() + " - Images Selected");
            selectImages.setVisibility(View.VISIBLE);
            //Log.d("i = ",i+"");



        } else {
            selectImages.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.selectImagesBtn:

                //When button is clicked then fill array with selected images
                ArrayList<String> selectedItems = imagesAdapter.getCheckedItems();

                //Send back result to MainActivity with selected images
                Intent intent = new Intent();
                intent.putExtra(LostPetDetailsActivity.CustomGalleryIntentKey, selectedItems.toString());//Convert Array into string to pass data
                setResult(RESULT_OK, intent);//Set result OK
                finish();//finish activity
                break;

        }

    }
}

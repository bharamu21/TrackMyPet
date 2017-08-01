package com.benayah.app.trackmypet;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.benayah.app.trackmypet.Adapters.ViewPagerAdapter;
import com.benayah.app.trackmypet.POJO.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Benayah on 23/5/2017.
 */

public class LostPetDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout mLostPetName,mLostPetType,mLostPetBreed,mLostPetAge,mLostPetSex,mLostPetColour,mLostPetAdditionalInfo,
            mOwnerName,mOwnerPermanentAddress,mOwnerPhoneNumber,mOwnerAlternatePhoneNumber,mPetLostPlaceAddress;
    LinearLayout mGoBackToMapPage;
    Spinner mLostPetSexList;
    Button mLostPetDetailsSubmitBtn;
    ImageView mPetImage;
    ViewPager viewPager;
    LinearLayout ll_dots;
    String[] mSpinnerArray = new String[]{"Male","Female"};
    private TextView[] dots;
    int page_position = 0;
    List<String> selectedImages;
    LinearLayout addPetImage;
    //FrameLayout mAddPetImage;
    ImageView imageView;
    Button mOpenCustomGalleryBtn;

    Activity activity;

    private static final int CustomGallerySelectId = 1;//Set Intent Id
    public static final String CustomGalleryIntentKey = "ImageArray";//Set Intent Key Valuep

    MapView mMapView;

    GoogleMap mGoogleMap;
    double latitude,longitude;
    Marker marker ;

    TextView mLostPetAddress;
    String lostPetAddress;
    Place placeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        setContentView(R.layout.lost_pet_description);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.map);
        setTitle("Enter pet details");

        activity = LostPetDetailsActivity.this;

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            placeDetails = (Place) bundle.getSerializable("place_details");

            Log.d("place_details",placeDetails.getPlaceAddress()+","+placeDetails.getLatitude()+","+placeDetails.getLongitude());

        }

        mLostPetName = (TextInputLayout) findViewById(R.id.lost_pet_name_wrapper);
        mLostPetName.setHint(getResources().getString(R.string.pet_name_hint));

        mPetImage = (ImageView) findViewById(R.id.pet_image);

        mLostPetType = (TextInputLayout) findViewById(R.id.lost_pet_type_wrapper);
        mLostPetType.setHint(getResources().getString(R.string.pet_type_hint));

        mLostPetBreed = (TextInputLayout) findViewById(R.id.lost_pet_breed_wrapper);
        mLostPetBreed.setHint(getResources().getString(R.string.pet_breed_hint));

        mLostPetAge = (TextInputLayout) findViewById(R.id.lost_pet_age_wrapper);
        mLostPetAge.setHint(getResources().getString(R.string.pet_age_hint));

        /*mLostPetSex = (TextInputLayout) findViewById(R.id.lost_pet_sex_wrapper);
        mLostPetSex.setHint(getResources().getString(R.string.lost_pet_sex_hint));*/

        mLostPetColour = (TextInputLayout) findViewById(R.id.lost_pet_colour_wrapper);
        mLostPetColour.setHint(getResources().getString(R.string.pet_colour_hint));

        mLostPetAdditionalInfo = (TextInputLayout) findViewById(R.id.lost_pet_additional_info_wrapper);
        mLostPetAdditionalInfo.setHint(getResources().getString(R.string.pet_info_hint));

        mOwnerName = (TextInputLayout) findViewById(R.id.lost_pet_owner_name_wrapper);
        mOwnerName.setHint(getResources().getString(R.string.owner_name));

        mPetLostPlaceAddress = (TextInputLayout) findViewById(R.id.pet_lost_place_address_wrapper);
        mPetLostPlaceAddress.setHint(getResources().getString(R.string.pet_lost_address));
        mPetLostPlaceAddress.getEditText().setText(placeDetails.getPlaceAddress());

        mOwnerPermanentAddress = (TextInputLayout) findViewById(R.id.lost_pet_owner_address_wrapper);
        mOwnerPermanentAddress.setHint(getResources().getString(R.string.permanent_address));

        mOwnerPhoneNumber = (TextInputLayout) findViewById(R.id.lost_pet_owner_phone_no_wrapper);
        mOwnerPhoneNumber.setHint(getResources().getString(R.string.owner_mobile_no));

        mOwnerAlternatePhoneNumber = (TextInputLayout) findViewById(R.id.lost_pet_owner_alternate_phone_no_wrapper);
        mOwnerAlternatePhoneNumber.setHint(getResources().getString(R.string.owner_alternate_mobile_no));

        /*mGoBackToMapPage = (LinearLayout) findViewById(R.id.go_back_to_map);
        mGoBackToMapPage.setOnClickListener(this);*/

        mLostPetDetailsSubmitBtn = (Button) findViewById(R.id.lost_pet_submit_btn);
        mLostPetDetailsSubmitBtn.setOnClickListener(this);

        mOpenCustomGalleryBtn = (Button) findViewById(R.id.open_custom_gallery);
        mOpenCustomGalleryBtn.setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.add_image_view);
        viewPager = (ViewPager) findViewById(R.id.viewpager);



        ll_dots = (LinearLayout) findViewById(R.id.ll_dots);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        mLostPetSexList = (Spinner) findViewById(R.id.lost_pet_sex_list);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,R.layout.simple_spinner_list, mSpinnerArray);
        mLostPetSexList.setAdapter(spinnerAdapter);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id)
        {
            case R.id.go_back_to_map:
                this.finish();
                break;

            case R.id.lost_pet_submit_btn:

                String petName = mLostPetName.getEditText().getText().toString().trim();
                System.out.println("petName = "+petName);
                validateFields();

                break;
            case R.id.open_custom_gallery:
                imageView.setVisibility(View.GONE);
                //Start Custom Gallery Activity by passing intent id
                startActivityForResult(new Intent(LostPetDetailsActivity.this, CustomGallery_Activity.class), CustomGallerySelectId);
                break;
        }
    }


    public void validateFields()
    {
        String petName = mLostPetName.getEditText().getText().toString();
        String petType = mLostPetType.getEditText().getText().toString();
        String petbreed = mLostPetBreed.getEditText().getText().toString();
        String petAge = mLostPetAge.getEditText().getText().toString();
        String petColor = mLostPetColour.getEditText().getText().toString();
        String petAdditionalInfo = mLostPetAdditionalInfo.getEditText().getText().toString();
        String petOwnerName = mOwnerName.getEditText().getText().toString();
        String petlostPlaceAddress = mPetLostPlaceAddress.getEditText().getText().toString();
        String petOwnerPermanentAddress = mOwnerPermanentAddress.getEditText().getText().toString();
        String petOwnerMobileNo = mOwnerPhoneNumber.getEditText().getText().toString();
        String petOwnerAlternateMobileNo = mOwnerAlternatePhoneNumber.getEditText().getText().toString();
    }


    private void addBottomDots(int currentPage) {
        dots = new TextView[selectedImages.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#000000"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#aeaeaf"));
    }

    protected void onActivityResult(int requestcode, int resultcode,
                                    Intent imagereturnintent) {
        super.onActivityResult(requestcode, resultcode, imagereturnintent);
        switch (requestcode) {
            case CustomGallerySelectId:
                if (resultcode == RESULT_OK) {
                    String imagesArray = imagereturnintent.getStringExtra(CustomGalleryIntentKey);//get Intent data
                    //Convert string array into List by splitting by ',' and substring after '[' and before ']'
                    selectedImages = Arrays.asList(imagesArray.substring(1, imagesArray.length() - 1).split(", "));
                    Log.d("onActivityResult",imagesArray);
                    loadGridView(new ArrayList<String>(selectedImages));//call load gridview method by passing converted list into arrayList
                }
                break;

        }
    }

    //Load GridView
    private void loadGridView(ArrayList<String> imagesArray) {
        addBottomDots(0);
        ViewPagerAdapter adapter = new ViewPagerAdapter(LostPetDetailsActivity.this, imagesArray);
        viewPager.setAdapter(adapter);
    }

    //Read Shared Images
    private void getSharedImages() {

        //If Intent Action equals then proceed
        if (Intent.ACTION_SEND_MULTIPLE.equals(getIntent().getAction())
                && getIntent().hasExtra(Intent.EXTRA_STREAM)) {
            ArrayList<Parcelable> list =
                    getIntent().getParcelableArrayListExtra(Intent.EXTRA_STREAM);//get Parcelabe list
            ArrayList<String> selectedImages = new ArrayList<>();

            //Loop to all parcelable list
            for (Parcelable parcel : list) {
                Uri uri = (Uri) parcel;//get URI
                String sourcepath = getPath(uri);//Get Path of URI
                selectedImages.add(sourcepath);//add images to arraylist
                Log.d("getSharedImages",sourcepath);
            }
            loadGridView(selectedImages);//call load gridview
        }
    }


    //get actual path of uri
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon action bar is clicked; go to parent activity
                this.finish();
                return true;

            /*case R.id.action_settings:
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

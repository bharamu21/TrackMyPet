package com.benayah.app.trackmypet;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

/**
 * Created by Benayah on 23/5/2017.
 */

public class FoundPetDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    TextInputLayout mFoundPetName,mFoundPetType,mFoundPetBreed,mFoundPetAge,mFoundPetColour,mFoundPetAdditionalInfo,mFounderName,
                    mFounderPermanentAddress,mFounderPhoneNumber,mFounderAlternatePhoneNumber;
    LinearLayout mGoBackToMapPage;
    Spinner mFoundPetSexList;
    Button mFoundPetDetailsSubmitBtn;
    String[] mSpinnerArray = new String[]{"Male","Female"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        setContentView(R.layout.found_pet_descrption);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Buy Pet");

        mFoundPetName = (TextInputLayout) findViewById(R.id.found_pet_name_wrapper);
        mFoundPetName.setHint(getResources().getString(R.string.found_pet_name_hint));

        mFoundPetType = (TextInputLayout) findViewById(R.id.found_pet_type_wrapper);
        mFoundPetType.setHint(getResources().getString(R.string.pet_type_hint));

        mFoundPetBreed = (TextInputLayout) findViewById(R.id.found_pet_breed_wrapper);
        mFoundPetBreed.setHint(getResources().getString(R.string.pet_breed_hint));

        mFoundPetAge = (TextInputLayout) findViewById(R.id.found_pet_age_wrapper);
        mFoundPetAge.setHint(getResources().getString(R.string.pet_age_hint));


        mFoundPetColour = (TextInputLayout) findViewById(R.id.found_pet_colour_wrapper);
        mFoundPetColour.setHint(getResources().getString(R.string.pet_colour_hint));

        mFoundPetAdditionalInfo = (TextInputLayout) findViewById(R.id.found_pet_additional_info_wrapper);
        mFoundPetAdditionalInfo.setHint(getResources().getString(R.string.pet_info_hint));

        mFounderName = (TextInputLayout) findViewById(R.id.found_pet_founder_name_wrapper);
        mFounderName.setHint(getResources().getString(R.string.founder_name));

        mFounderPermanentAddress = (TextInputLayout) findViewById(R.id.found_pet_founder_address_wrapper);
        mFounderPermanentAddress.setHint(getResources().getString(R.string.permanent_address));

        mFounderPhoneNumber = (TextInputLayout) findViewById(R.id.found_pet_founder_phone_no_wrapper);
        mFounderPhoneNumber.setHint(getResources().getString(R.string.founder_mobile_no));

        mFounderAlternatePhoneNumber = (TextInputLayout) findViewById(R.id.found_pet_founder_alternate_phone_no_wrapper);
        mFounderAlternatePhoneNumber.setHint(getResources().getString(R.string.founder_alternate_mobile_no));

        mGoBackToMapPage = (LinearLayout) findViewById(R.id.go_back_to_map);
        mGoBackToMapPage.setOnClickListener(this);

        mFoundPetDetailsSubmitBtn = (Button) findViewById(R.id.found_pet_submit_btn);
        mFoundPetDetailsSubmitBtn.setOnClickListener(this);

        mFoundPetSexList = (Spinner) findViewById(R.id.found_pet_sex_list);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,R.layout.simple_spinner_list, mSpinnerArray);
        mFoundPetSexList.setAdapter(spinnerAdapter);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id)
        {
            case R.id.go_back_to_map:
                this.finish();
                break;
        }
    }
}

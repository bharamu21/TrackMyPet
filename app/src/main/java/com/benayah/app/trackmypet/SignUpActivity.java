package com.benayah.app.trackmypet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.benayah.app.trackmypet.Api.SignUpApi;
import com.benayah.app.trackmypet.POJO.SignUpUserDetails;
import com.benayah.app.trackmypet.Utils.CommonAppMember;
import com.benayah.app.trackmypet.Utils.Constants;
import com.benayah.app.trackmypet.Utils.PreferanceHandler;
import com.benayah.app.trackmypet.Utils.WebService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Benayah on 10/5/2017.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    TextInputLayout mUserSignUpEmailWrapper,mUserSignUpPasswordWrapper,mSignUpUserNameWrapper,mSignUpConfirmPasswordWrapper;

    Button mSignUpBtn;
    CheckBox mAgreeTermsAndConditionCheckBox;

    private JSONObject mJsonResponse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.sign_up_screen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //View view = inflater.inflate(R.layout.sign_up_screen,container,false);
        setTitle("Sign Up");
        mUserSignUpEmailWrapper = (TextInputLayout) findViewById(R.id.user_email_wrapper);

        mUserSignUpEmailWrapper.setHint(getString(R.string.sign_up_email));

        mUserSignUpPasswordWrapper = (TextInputLayout) findViewById(R.id.user_password_wrapper);
        mUserSignUpPasswordWrapper.setHint(getString(R.string.sign_up_password));

        mSignUpUserNameWrapper = (TextInputLayout) findViewById(R.id.user_name_wrapper);
        mSignUpUserNameWrapper.setHint(getString(R.string.sign_up_user_name));

        mSignUpConfirmPasswordWrapper = (TextInputLayout) findViewById(R.id.sign_up_confirm_password_wrapper);
        mSignUpConfirmPasswordWrapper.setHint(getString(R.string.sign_up_confirm_password));

        mAgreeTermsAndConditionCheckBox = (CheckBox) findViewById(R.id.terms_condition_check_box);

        mSignUpBtn = (Button) findViewById(R.id.sign_up_button);
        mSignUpBtn.setOnClickListener(this);
        mAgreeTermsAndConditionCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CommonAppMember.hideSoftKeyboard(SignUpActivity.this,mAgreeTermsAndConditionCheckBox);
            }
        });

    }


    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id)
        {
            case R.id.sign_up_button:
                CommonAppMember.hideSoftKeyboard(SignUpActivity.this,view);
                 validateSignUpData();
            break;


        }

    }

    private void validateSignUpData() {
        String userName,userEmailId,userPassword,userConfirmPassword;

        userName = mSignUpUserNameWrapper.getEditText().getText().toString();
        userEmailId = mUserSignUpEmailWrapper.getEditText().getText().toString();
        userPassword = mUserSignUpPasswordWrapper.getEditText().getText().toString();
        userConfirmPassword = mSignUpConfirmPasswordWrapper.getEditText().getText().toString();

        if(userName == null || userName.isEmpty())
        {
            mSignUpUserNameWrapper.getEditText().setError(getResources().getString(R.string.sign_up_user_name_error));
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.sign_up_user_name_error),Toast.LENGTH_SHORT).show();
        }
        else if(userName.length() < 3)
        {
            mSignUpUserNameWrapper.getEditText().setError(getResources().getString(R.string.sign_up_user_name_length_error));
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.sign_up_user_name_length_error),Toast.LENGTH_SHORT).show();
        }
        else if(userEmailId == null || userEmailId.isEmpty())
        {
            mUserSignUpEmailWrapper.getEditText().setError(getResources().getString(R.string.email_error));
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.email_error),Toast.LENGTH_SHORT).show();
        }
        else if(!userEmailId.matches(Constants.EMAILPATTERN))
        {
            mUserSignUpEmailWrapper.getEditText().setError(getResources().getString(R.string.email_error));
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.email_error),Toast.LENGTH_SHORT).show();
        }
        else if(userPassword == null || userPassword.isEmpty())
        {
            mUserSignUpPasswordWrapper.getEditText().setError(getResources().getString(R.string.password_error));
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.password_error),Toast.LENGTH_SHORT).show();
        }
        else if(userPassword.length() < 6)
        {
            mUserSignUpPasswordWrapper.getEditText().setError(getResources().getString(R.string.sign_up_password_length_error));
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.sign_up_password_length_error),Toast.LENGTH_SHORT).show();
        }
        else if(userConfirmPassword == null || userConfirmPassword.isEmpty())
        {
            mSignUpConfirmPasswordWrapper.getEditText().setError(getResources().getString(R.string.sign_up_confirm_password_error));
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.sign_up_confirm_password_error),Toast.LENGTH_SHORT).show();
        }
        else if(!userPassword.equals(userConfirmPassword))
        {
            mSignUpConfirmPasswordWrapper.getEditText().setError(getResources().getString(R.string.password_mismatch_error));
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.password_mismatch_error),Toast.LENGTH_SHORT).show();
        }
        else if(!mAgreeTermsAndConditionCheckBox.isChecked())
        {
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.sign_up_agree_terms_error),Toast.LENGTH_SHORT).show();
        }
        else
        {
            SignUpUserDetails userDetails = new SignUpUserDetails(userName,userEmailId,userPassword);
            //signUpAsyncTask(userDetails);
            new SignUpAsyncTask().execute(userDetails);
        }


    }

    private void signUpAsyncTask(SignUpUserDetails userDetails) {

        SignUpApi.getInstance().callResponse(userDetails, new Callback<JSONObject>() {
            @Override
            public void success(JSONObject jsonObject, Response response) {
                String jsonResponse = String.valueOf(jsonObject);

                try {
                    if(jsonResponse != null)
                    {
                        mJsonResponse = new JSONObject(jsonResponse);

                        int httpCode = mJsonResponse.getInt("httpCode");
                        String message = mJsonResponse.getString("message");

                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(),"failure",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class SignUpAsyncTask extends AsyncTask<SignUpUserDetails,String,String>
    {
        ProgressDialog dialog;
        JSONObject response;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(SignUpActivity.this);
            dialog.setMessage("please wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(SignUpUserDetails... signUpUserDetailses) {
            SignUpUserDetails userDetails = signUpUserDetailses[0];
            response = WebService.submitUser(userDetails.getUserName(),userDetails.getEmail(),userDetails.getPassword());

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(dialog.isShowing())
            {
                dialog.dismiss();
            }

            if(response != null)
            {
                String jsonResponse = String.valueOf(response);
                try {
                    mJsonResponse = new JSONObject(jsonResponse);

                    int httpcode = mJsonResponse.getInt("httpCode");
                    String message = mJsonResponse.getString("message");

                    Log.d("httpCode",httpcode+"");

                    if(httpcode == 200)
                    {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.sign_up_success_message),
                                Toast.LENGTH_LONG).show();
                        //JSONArray mDetails =
                        JSONObject details = mJsonResponse.getJSONObject("detail");
                        //String name = details.getString("user_id");
                        //System.out.println("user_id = "+name);
                        //PreferanceHandler.getInstance(SignUpActivity.this).setUserName(details.getString("username"));
                        PreferanceHandler.getInstance(SignUpActivity.this).setUserId(details.getString("user_id"));
                        PreferanceHandler.getInstance(SignUpActivity.this).setUserLoginStatus(true);

                        Intent homeIntent = new Intent(SignUpActivity.this,MainActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(homeIntent);
                        //PreferanceHandler.getInstance(SignUpActivity.this).setPassword(details.getString("username"));
                        //PreferanceHandler.getInstance(SignUpActivity.this).set(details.getString("username"));
                    }
                }
                catch (JSONException ex)
                {
                    ex.printStackTrace();
                }

            }
        }
    }
}
//cupa
//TSPCA
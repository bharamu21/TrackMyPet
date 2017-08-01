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
import android.widget.TextView;
import android.widget.Toast;

import com.benayah.app.trackmypet.Api.SignInApi;
import com.benayah.app.trackmypet.POJO.SignInDetails;
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
 * Created by Benayah on 11/5/2017.
 */

public class SignInActivity extends AppCompatActivity implements View.OnClickListener{
    TextInputLayout mUserSignInEmailWrapper,mUserSignInPasswordWrapper;
    Button mSignInBtn;
    TextView mUserRegister,mForgotPassword,mErrorMessage;

    private JSONObject mJsonResponse;

    @Override
     public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)‌​;


        setContentView(R.layout.sign_in_screen);
        setTitle("Sign In");


        mUserSignInEmailWrapper = (TextInputLayout) findViewById(R.id.user_email_wrapper);
        mUserSignInEmailWrapper.setHint(getString(R.string.sign_up_email));

        mUserSignInPasswordWrapper = (TextInputLayout) findViewById(R.id.user_password_wrapper);
        mUserSignInPasswordWrapper.setHint(getString(R.string.sign_up_password));

        mUserRegister = (TextView) findViewById(R.id.register);
        mUserRegister.setOnClickListener(this);

        mForgotPassword = (TextView) findViewById(R.id.forgot_password);
        mForgotPassword.setOnClickListener(this);

        mSignInBtn = (Button) findViewById(R.id.sign_in_button);
        mSignInBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id)
        {
            case R.id.register:

                Intent registerIntent = new Intent(SignInActivity.this,SignUpActivity.class);
                startActivity(registerIntent);
                break;

            case R.id.sign_in_button:
                CommonAppMember.hideSoftKeyboard(SignInActivity.this,view);
                 validateSignInData();
                break;
        }

    }

    private void validateSignInData() {

        String signInEmaiID,signInPassword;

        signInEmaiID = mUserSignInEmailWrapper.getEditText().getText().toString();
        signInPassword = mUserSignInPasswordWrapper.getEditText().getText().toString();

        if(signInEmaiID == null || signInEmaiID.isEmpty())
        {
            mUserSignInEmailWrapper.getEditText().setError(getResources().getString(R.string.email_error));
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.email_error),Toast.LENGTH_SHORT).show();
        }
        else if(!signInEmaiID.matches(Constants.EMAILPATTERN))
        {
            mUserSignInEmailWrapper.getEditText().setError(getResources().getString(R.string.email_error));
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.email_error),Toast.LENGTH_SHORT).show();
        }
        else if(signInPassword == null || signInPassword.isEmpty())
        {
            mUserSignInPasswordWrapper.getEditText().setError(getResources().getString(R.string.password_error));
            Toast.makeText(getApplicationContext(),getResources().getString(R.string.password_error),Toast.LENGTH_SHORT).show();
        }
        else
        {
            new SignInAsyncTask().execute(new SignInDetails(signInEmaiID,signInPassword));
        }

    }

    public class SignInAsyncTask extends AsyncTask<SignInDetails,String,String>
    {
        ProgressDialog dialog;
        JSONObject response;
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(SignInActivity.this);
            dialog.setMessage("please wait..");
            dialog.show();
        }

        @Override
        protected String doInBackground(SignInDetails... signInUserDetailses) {

            SignInDetails signInDetails = signInUserDetailses[0];
            response = WebService.userSignIn(signInDetails.getSignInEmail(),signInDetails.getSignInPassword());

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
                        Toast.makeText(getApplicationContext(),message,
                                Toast.LENGTH_SHORT).show();
                        JSONArray mDetails = mJsonResponse.getJSONArray("detail");
                        JSONObject details = mDetails.getJSONObject(0);
                        PreferanceHandler.getInstance(SignInActivity.this).setUserName(details.getString("email"));
                        PreferanceHandler.getInstance(SignInActivity.this).setUserLoginStatus(true);

                        Intent homeIntent = new Intent(SignInActivity.this,MainActivity.class);

                        startActivity(homeIntent);
                        Toast.makeText(SignInActivity.this,message,Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(SignInActivity.this,message,Toast.LENGTH_SHORT).show();
                    }
                }
                catch (JSONException ex)
                {
                    ex.printStackTrace();
                }

            }
        }
    }
    /*public void signInAsyncTask(String mLoginEmail,String mLoginPassword)
    {
        SignInApi.getSignInApiInstance().callBackResponse(mLoginEmail, mLoginPassword, new Callback<JSONObject>() {
            @Override
            public void success(JSONObject jsonObject, Response response) {

                String mResponse = String.valueOf(jsonObject);

               try
               {
                   if(mResponse != null)
                   {
                       JSONObject mJsonObject = new JSONObject(mResponse);
                       int httpCode = mJsonObject.getInt("httpCode");
                       String message = mJsonObject.getString("message");
                       if(httpCode == 200)
                       {
                           Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                       }
                       else
                       {
                           Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
                       }
                   }
               }
               catch (JSONException ex)
               {
                   ex.printStackTrace();
               }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_LONG).show();
            }
        });
    }*/
    /*@Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }*/


}

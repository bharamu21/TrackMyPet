package com.benayah.app.trackmypet.Api;

import com.benayah.app.trackmypet.POJO.SignUpUserDetails;
import com.benayah.app.trackmypet.Utils.ApiClient;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Benayah on 30/6/2017.
 */

public class SignUpApi {

        private static SignUpApi signUpApiInstance;

    public static SignUpApi getInstance()
    {
        if(signUpApiInstance == null)
        {
            signUpApiInstance = new SignUpApi();
        }
        return signUpApiInstance;
    }


    public void callResponse(SignUpUserDetails userDetails, Callback<JSONObject> mCallBack)
    {
        mySignUpApi mySignUpApi = ApiClient.getInstance().getApiBuilder().create(mySignUpApi.class);
        mySignUpApi.mySignInApi(userDetails.getUserName(),userDetails.getEmail(),userDetails.getPassword(),mCallBack);
    }

    public interface mySignUpApi {
        @POST("/signup.php")
        void mySignInApi(@Query("username") String userName, @Query("email") String userEmail,
                         @Query("password") String password , Callback<JSONObject> mCallback);
    }


}

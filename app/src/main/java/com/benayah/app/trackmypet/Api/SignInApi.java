package com.benayah.app.trackmypet.Api;

import com.benayah.app.trackmypet.Utils.ApiClient;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Benayah on 4/7/2017.
 */

public class SignInApi {

    private static SignInApi signInApi;

    public static SignInApi getSignInApiInstance()
    {
        if(signInApi == null)
        {
            signInApi = new SignInApi();
        }
        return signInApi;
    }

    public void callBackResponse(String email,String password,Callback<JSONObject> mResponse)
    {
        mSignInApi mSignInApi = ApiClient.getInstance().getApiBuilder().create(SignInApi.mSignInApi.class);
        mSignInApi.mSignInApi(email,password,mResponse);
    }
    public interface mSignInApi
    {
        @POST("/login.php")
        void mSignInApi(@Query("email") String loginEmail, @Query("password") String loginPassword,
                        Callback<JSONObject> mCallBack);
    }
}

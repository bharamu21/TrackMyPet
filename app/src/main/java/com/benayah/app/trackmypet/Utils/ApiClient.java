package com.benayah.app.trackmypet.Utils;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by Benayah on 30/6/2017.
 */

public class ApiClient {

    public static ApiClient apiClientInstance;
    private RestAdapter ApiBuilder;


    private ApiClient()
    {

    }
    
    public static ApiClient getInstance()
    {
            synchronized (ApiClient.class)
            {
                if(apiClientInstance == null)
                {
                    apiClientInstance = new ApiClient();
                }
            }
        apiClientInstance.configRetrofit();
        return apiClientInstance;
    }

    private void configRetrofit() {

        OkHttpClient mOkHttp = new OkHttpClient();
        mOkHttp.setReadTimeout(150, TimeUnit.SECONDS);
        mOkHttp.setConnectTimeout(150, TimeUnit.SECONDS);
        ApiBuilder = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setClient(new OkClient(mOkHttp))
                .setEndpoint(Constants.URL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String msg) {
                        String[] blacklist = {"Access-Control", "Cache-Control", "Connection", "Content-Type", "Keep-Alive", "Pragma", "Server", "Vary", "X-Powered-By"};
                        for (String bString : blacklist) {
                            if (msg.startsWith(bString)) {
                                return;
                            }
                        }
                        System.out.println("Res Retrofit response : " + msg);

                    }
                })
                .build();
    }


    public void setApiBuilder(RestAdapter apiBuilder) {
        ApiBuilder = apiBuilder;
    }

    public RestAdapter getApiBuilder() {
        return ApiBuilder;
    }
}

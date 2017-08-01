package com.benayah.app.trackmypet.Utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benayah on 30/6/2017.
 */

public class WebService {

    private static JSONObject responceJson;
    static InputStream is = null;

     public static JSONObject submitUser(String displayname,String mEmail,String password){

        try {
            HttpResponse mResponse;
            // Interface for HTTP Client
            HttpClient httpClient = new DefaultHttpClient();
            // Context for executing the request
            HttpContext localContext = new BasicHttpContext();
            HttpParams httpParameters = new BasicHttpParams();
            String url = Constants.URL + "/user_signup";

            System.out.println("Res url : " +url);

            // Makes the request to the orgin server
            HttpPost httpPost = new HttpPost(url);
            // Instantaiting the list of type namevalue pair
            List<NameValuePair> Details = new ArrayList<NameValuePair>();
            // Adding the entries in to the list
            Details.add(new BasicNameValuePair("username", displayname));
            Details.add(new BasicNameValuePair("email", mEmail));
            Details.add(new BasicNameValuePair("password", password));



            // Set the timeout in milliseconds until a connection is
            // established.
            // The default value is zero, that means the timeout is not
            // used.
            int timeoutConnection = 50000;
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT)
            // in milliseconds which is the timeout for waiting for
            // data.
            int timeoutSocket = 10000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            // Hands the entity to the request
            httpPost.setEntity(new UrlEncodedFormEntity(Details));
            // Execute the request using the given context
            mResponse = httpClient.execute(httpPost, localContext);
            /*HttpEntity httpEntity = mResponse.getEntity();
            is = httpEntity.getContent();*/
            // mResponsetext = mResponse.getStatusLine().getStatusCode();
            String responseString = EntityUtils.toString(mResponse.getEntity());
            // Log.i("Response", "Response :" + responseString);
            System.out.println("Res Sign Up : " + responseString);
            responceJson = new JSONObject(responseString);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return responceJson;
    }

     public static JSONObject userSignIn(String mEmail,String password){

        try {
            HttpResponse mResponse;
            // Interface for HTTP Client
            HttpClient httpClient = new DefaultHttpClient();
            // Context for executing the request
            HttpContext localContext = new BasicHttpContext();
            HttpParams httpParameters = new BasicHttpParams();
            String url = Constants.URL + "/user_login";

            //System.out.println("Res url : " +url);

            // Makes the request to the orgin server
            HttpPost httpPost = new HttpPost(url);
            // Instantaiting the list of type namevalue pair
            List<NameValuePair> Details = new ArrayList<NameValuePair>();
            // Adding the entries in to the list
            Details.add(new BasicNameValuePair("email", mEmail));
            Details.add(new BasicNameValuePair("password", password));



            // Set the timeout in milliseconds until a connection is
            // established.
            // The default value is zero, that means the timeout is not
            // used.
            int timeoutConnection = 50000;
            HttpConnectionParams.setConnectionTimeout(httpParameters,
                    timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT)
            // in milliseconds which is the timeout for waiting for
            // data.
            int timeoutSocket = 10000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

            // Hands the entity to the request
            httpPost.setEntity(new UrlEncodedFormEntity(Details));
            // Execute the request using the given context
            mResponse = httpClient.execute(httpPost, localContext);
            /*HttpEntity httpEntity = mResponse.getEntity();
            is = httpEntity.getContent();*/
            // mResponsetext = mResponse.getStatusLine().getStatusCode();
            String responseString = EntityUtils.toString(mResponse.getEntity());
            // Log.i("Response", "Response :" + responseString);
            System.out.println("Res Sign in : " + responseString);
            responceJson = new JSONObject(responseString);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return responceJson;
    }
}

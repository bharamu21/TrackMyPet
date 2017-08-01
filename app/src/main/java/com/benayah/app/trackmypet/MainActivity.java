package com.benayah.app.trackmypet;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.benayah.app.trackmypet.Adapters.NavigationListAdapter;
import com.benayah.app.trackmypet.POJO.DrawerListItems;
import com.benayah.app.trackmypet.Utils.Constants;
import com.benayah.app.trackmypet.Utils.PreferanceHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String Tag = "MainActivity";
    DrawerLayout drawer;
    TypedArray navBarIcons;


    /*private CoverFlowAdapter adapter;*/
    ArrayList<DrawerListItems> drawerListItemsArrayList;
    NavigationListAdapter adapter;

    private TextView stickyView;
    private View heroImageView;
    private View stickyViewSpacer;
    AlertDialog alertDialog;

    ListView listView;
    TextView mSignUp,mSignIn,mHome,mLostPetList,mFoundPetList,mAboutApp,mSignOut;

    boolean isUserLoggedIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*getWindow().requestFeature(Window.FEATURE_NO_TITLE);*/
        /*getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);*/

        setContentView(R.layout.activity_main);


        setTitle("");


        checkPermissions();

        if(!isPlayServicesAvailable())
        {
            Toast.makeText(this, getResources().getString(R.string.google_play_update), Toast.LENGTH_LONG).show();
        }

        this.registerReceiver(mConnectivityReciever,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        listView = (ListView) findViewById(R.id.menu_list);
        heroImageView = findViewById(R.id.pet_app_image);
        stickyView = (TextView) findViewById(R.id.stickyView);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listHeader = inflater.inflate(R.layout.list_header, null);
        stickyViewSpacer = listHeader.findViewById(R.id.stickyViewPlaceholder);

        listView.addHeaderView(listHeader);

        isUserLoggedIn = PreferanceHandler.getInstance(MainActivity.this).getUserLoginStatus();

        setNavigationDrawer(true);

        adapter = new NavigationListAdapter(MainActivity.this,drawerListItemsArrayList);
        listView.setAdapter(adapter);
        listView.setActivated(true);

        listView.setOnItemClickListener(this);


        /* Handle list View scroll events */
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                /* Check if the first item is already reached to top.*/
                if (listView.getFirstVisiblePosition() == 0) {
                    View firstChild = listView.getChildAt(0);
                    int topY = 0;
                    if (firstChild != null) {
                        topY = firstChild.getTop();
                    }

                    int heroTopY = stickyViewSpacer.getTop();
                    stickyView.setY(Math.max(0, heroTopY + topY));

                    /* Set the image to scroll half of the amount that of ListView */
                    heroImageView.setY(topY);
                }
            }
        });





    }




    public void displayView(int id) {

        switch (id)
        {
            case 1:
                Intent signInIntent = new Intent(MainActivity.this,SignInActivity.class);
                // set the new task and clear flags
                //signInIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signInIntent);
                break;

            case 2:
                Intent bereavementIntent = new Intent(MainActivity.this,BereavementActivity.class);
                startActivity(bereavementIntent);
                break;

            case 3:
                Intent userForumIntent = new Intent(MainActivity.this,UserForumActivity.class);
                startActivity(userForumIntent);
                break;

            case 4:
                Intent lostPetIntent = new Intent(MainActivity.this,LostListActivity.class);
                startActivity(lostPetIntent);
                break;

            case 5:
                Intent foundPetIntent = new Intent(MainActivity.this,FoundListActivity.class);
                startActivity(foundPetIntent);
                break;

            case 6:
                Intent lostPetListIntent = new Intent(MainActivity.this,LostListActivity.class);
                startActivity(lostPetListIntent);
                break;

            case 7:
                Intent foundPetListIntent = new Intent(MainActivity.this,FoundListActivity.class);
                startActivity(foundPetListIntent);
                break;

            case 8:
                Intent userProfileIntent = new Intent(MainActivity.this,UserProfileActivity.class);
                startActivity(userProfileIntent);
                break;

            case 9:
                Intent signnIntent = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(signnIntent);
                break;

            case 10:
                Intent signUpIntent = new Intent(MainActivity.this,LostListActivity.class);
                startActivity(signUpIntent);
                break;

            case 11:
                Intent lostPetList1 = new Intent(MainActivity.this,LostListActivity.class);
                startActivity(lostPetList1);
                break;

            case 12:
                Intent lostPetList2 = new Intent(MainActivity.this,LostListActivity.class);
                startActivity(lostPetList2);
                break;

            case 13:
                Intent lostPetList3 = new Intent(MainActivity.this,LostListActivity.class);
                startActivity(lostPetList3);
                break;

            case 14:

                Intent lostPetList4 = new Intent(MainActivity.this,LostListActivity.class);
                startActivity(lostPetList4);
                break;






        }
    }


    public void displayViewWhenUserLoggedIn(int id) {

        switch (id)
        {
            case 1:
                Intent lostPetIntent = new Intent(MainActivity.this,MapActivity.class);
                // set the new task and clear flags
                //signInIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Bundle lostPetBundle = new Bundle();
                lostPetBundle.putString("screen_name","lost_pet");
                lostPetIntent.putExtras(lostPetBundle);
                startActivity(lostPetIntent);
                break;
            case 2:
                Intent foundPetIntent = new Intent(MainActivity.this,MapActivity.class);
                Bundle foundPetBundle = new Bundle();
                foundPetBundle.putString("screen_name","found_pet");
                foundPetIntent.putExtras(foundPetBundle);
                startActivity(foundPetIntent);
                break;

            case 3:
                Intent lostPetListIntent = new Intent(MainActivity.this,LostListActivity.class);
                startActivity(lostPetListIntent);
                break;

            case 4:
                Intent foundPetListIntent = new Intent(MainActivity.this,FoundListActivity.class);
                startActivity(foundPetListIntent);
                break;

            case 5:
                Intent petStoreIntent = new Intent(MainActivity.this,PetIndustryActivity.class);
                Bundle petStoreBundle = new Bundle();
                petStoreBundle.putString("activity_name","pet_store");
                petStoreIntent.putExtras(petStoreBundle);
                startActivity(petStoreIntent);
                break;

            case 6:
                Intent petClinicIntent = new Intent(MainActivity.this,PetIndustryActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("activity_name","veterinary_care");
                petClinicIntent.putExtras(bundle);
                startActivity(petClinicIntent);
                break;

            case 7:
                Intent mateForPetIntent = new Intent(MainActivity.this,MateForPetActivity.class);
                startActivity(mateForPetIntent);
                break;

            case 8:
                Intent userProfileIntent = new Intent(MainActivity.this,UserProfileActivity.class);
                startActivity(userProfileIntent);
                break;

            case 9:
                Intent signnIntent = new Intent(MainActivity.this,SignInActivity.class);
                startActivity(signnIntent);
                break;

            case 10:
                Intent signUpIntent = new Intent(MainActivity.this,LostListActivity.class);
                startActivity(signUpIntent);
                break;

            case 11:
                Intent lostPetList1 = new Intent(MainActivity.this,LostListActivity.class);
                startActivity(lostPetList1);
                break;

            case 12:
                Intent lostPetList2 = new Intent(MainActivity.this,LostListActivity.class);
                startActivity(lostPetList2);
                break;

            case 13:
                Intent lostPetList3 = new Intent(MainActivity.this,LostListActivity.class);
                startActivity(lostPetList3);
                break;

            case 14:

                Intent lostPetList4 = new Intent(MainActivity.this,LostListActivity.class);
                startActivity(lostPetList4);
                break;

            case 15:
                Intent lostPetList5 = new Intent(MainActivity.this,LostListActivity.class);
                startActivity(lostPetList5);
                break;

            case 16:
                Intent signOutActivity = new Intent(MainActivity.this,MainActivity.class);
                signOutActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signOutActivity);
                PreferanceHandler.getInstance(MainActivity.this).setUserLoginStatus(false);
            break;






        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //listView.setSelection(position);

        if(isUserLoggedIn)
        {
            displayViewWhenUserLoggedIn(position);
        }
        else
        {
            displayView(position);
        }
        Log.d("onItemClick",position+"");
    }


    private void setNavigationDrawer(boolean isHome) {

        drawerListItemsArrayList = new ArrayList<>();


        if(!isUserLoggedIn)
        {
            String[] navbarItems = getResources().getStringArray(R.array.navbar_list);
            TypedArray navbarItemsIcon = getResources().obtainTypedArray(R.array.navbar_list_images);
            //Log.d("navbarItems lenght",navbarItems.length+""+navbarItemsIcon.length());
            //System.out.println(navbarItems.length);
            for (int i = 0; i < navbarItems.length; i++) {
                //System.out.println(navbarItems[i]);
                DrawerListItems item = new DrawerListItems(navbarItemsIcon.getResourceId(i, -1), navbarItems[i]);
                drawerListItemsArrayList.add(item);
            }
        }
        else
        {
            String[] navbarItems = getResources().getStringArray(R.array.navbar_list_user_login);
            TypedArray navbarItemsIcon = getResources().obtainTypedArray(R.array.navbar_list_images_user_login);
            //Log.d("navbarItems lenght",navbarItems.length+""+navbarItemsIcon.length());
            //System.out.println(navbarItems.length);
            for (int i = 0; i < navbarItems.length; i++) {
                //System.out.println(navbarItems[i]);
                DrawerListItems item = new DrawerListItems(navbarItemsIcon.getResourceId(i, -1), navbarItems[i]);
                drawerListItemsArrayList.add(item);
            }
        }



        // Recycle the typed array
        //navbarItemsIcon.recycle();




        /*adapter = new CoverFlowAdapter(getApplicationContext(), listItems);
        coverFlow.setAdapter(adapter);*/
    }


    private boolean isPlayServicesAvailable()
    {
        GoogleApiAvailability gApi = GoogleApiAvailability.getInstance();
        int resultCode = gApi.isGooglePlayServicesAvailable(this);
            if(resultCode != ConnectionResult.SUCCESS)
            {
                if(gApi.isUserResolvableError(resultCode))
                {
                    gApi.getErrorDialog(this, resultCode,1).show();
                }
                else
                {
                    //Toast.makeText(this, getResources().getString(R.string.playservices_unrecoverable), Toast.LENGTH_LONG).show();
                    finish();
                }
                return false;
            }
            return true;
    }

    private void checkInternetConnectivity() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info =  connectivityManager.getActiveNetworkInfo();
        System.out.println("network info = "+info);

        if(info == null && !info.isConnected())
        {
            showAlertDialogBox();
        }


    }

    public void showAlertDialogBox() {

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        alertBuilder.setMessage(getResources().getString(R.string.internet_connectivity));

        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
            }
        });

        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(alertDialog != null && alertDialog.isShowing())
                {
                    alertDialog.dismiss();
                }
                finishAffinity();


            }
        });

        alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    BroadcastReceiver mConnectivityReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NetworkInfo currentNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            NetworkInfo otherNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_OTHER_NETWORK_INFO);

            if (currentNetworkInfo != null && currentNetworkInfo.isConnected()) {
                System.out.println("Cheking network state");
                //Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Not Connected", Toast.LENGTH_LONG).show();
                /*Intent intent1 = new Intent(Settings.ACTION_SETTINGS);*/
                showAlertDialogBox();
                //CommonAppMembers.showAlertDialog("Do you want to enable internet", intent1, MainActivity.this);
            }
        }
    };

    public boolean checkPermissions()
    {
        if((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
          &&(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED))
        {
            if((ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION))
              && ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_REQUEST_RESULT);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_EXTERNAL_STORAGE},Constants.MY_PERMISSIONS_REQUEST_RESULT);
            }
            return  false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode)
        {
            case Constants.MY_PERMISSIONS_REQUEST_RESULT:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0)
                {
                    Log.d("grantResults length",grantResults.length+"");
                    boolean locationPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccessPErmission = grantResults[1] == PackageManager.PERMISSION_GRANTED;



                }

                return;
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(alertDialog != null && alertDialog.isShowing())
        {
            alertDialog.dismiss();
        }
        //this.registerReceiver(mConnectivityReciever,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(alertDialog != null && alertDialog.isShowing())
        {
            alertDialog.dismiss();
        }
        unregisterReceiver(mConnectivityReciever);
    }


}

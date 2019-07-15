package com.example.vargasrt.kofccommunicare;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.vargasrt.kofccommunicare.Model.MyProfile;

import com.example.vargasrt.kofccommunicare.model.Messagebox;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView navigation;
    private Toolbar toolbar;
    IntentFilter intentFilter;
    public static final int DPM_ACTIVATION_REQUEST_CODE = 100;

    private ComponentName adminComponent;
    private DevicePolicyManager devicePolicyManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            toolbar.setTitle(item.getTitle());
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
            fab.hide();
            switch (item.getItemId()) {
                case R.id.navigation_announcement:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_product:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_textconnect:
                    viewPager.setCurrentItem(2);
                    fab.show();
                    return true;
//                case R.id.navigation_benefitcertificate:
//                    viewPager.setCurrentItem(3);
//                    fab.show();
//                    return true;
                case R.id.navigation_kcfapiportal:
                    viewPager.setCurrentItem(3);
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.snack_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_show_snack_bar:
                toolbar.setTitle(item.getTitle());
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
                fab.hide();
                viewPager.setCurrentItem(4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showSnackBar() {

        Snackbar.make(navigation, "Some text", Snackbar.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.view_pager);

        String imageSenderUrl = getURLForResource(R.drawable.ic_profile_face_48dp);

        /*
        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        adminComponent = new ComponentName(getPackageName(),getPackageName() + ".DeviceAdministrator");

        // Request device admin activation if not enabled.
        if (!devicePolicyManager.isAdminActive(adminComponent)) {

            Intent activateDeviceAdmin = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            activateDeviceAdmin.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent);
            startActivityForResult(activateDeviceAdmin, DPM_ACTIVATION_REQUEST_CODE);

        }
        */

        //intentFilter = new IntentFilter();
        //intentFilter.addAction("SMS_RECEIVED_ACTION");


        //BroadcastReceiver br = new MessageReceiver();
        //IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        //filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        /*
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        this.registerReceiver(new MessageReceiver(), filter);
        */

        KofCFragmentPageAdapter adapter = new KofCFragmentPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount() - 1);

        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        bindNavigationDrawer();
        initTitle();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        fab.hide();

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                switch (navigation.getSelectedItemId()){
                    case R.id.navigation_textconnect:
                        TabLayout tabLayout = findViewById(R.id.tabs_textconnect);
                        switch (tabLayout.getSelectedTabPosition()){
                            case 0:
                                //Snackbar.make(navigation, "New Text Connect - BC Inquiry", Snackbar.LENGTH_SHORT).show();
                                TextConnectChildFragment.getInstance().BCInquiry();
                                return;
                            case 1:
                                //Snackbar.make(navigation, "New Text Connect - Allowance", Snackbar.LENGTH_SHORT).show();
                                TextConnectChildFragment.getInstance().ALLWInquiry();
                                return;
                            case 2:
                                //Snackbar.make(navigation, "New Text Connect - Department", Snackbar.LENGTH_SHORT).show();
                                TextConnectChildFragment.getInstance().DeptInquiry();
                                return;
                        }
                        return;
//                    case R.id.navigation_benefitcertificate:
//                        Snackbar.make(navigation, "New Benefit Certificate", Snackbar.LENGTH_SHORT).show();
//                        return;
                }
            }
        });
    }

    private void initTitle() {
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                toolbar.setTitle(navigation.getMenu().getItem(0).getTitle());
            }
        });
    }


    private void bindNavigationDrawer() {

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                toolbar.setTitle(item.getTitle());
                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
                fab.hide();
                int id = item.getItemId();
                if (id == R.id.nav_profile) {
                    viewPager.setCurrentItem(5);
                    //fab.show();
                    //fab.setImageResource(R.drawable.ic_add_white_24dp);
                    //showToolSnackBar();
/*
                } else if (id == R.id.nav_settings) {
                    viewPager.setCurrentItem(7);
                    //showShareSnackBar();
                } else if (id == R.id.nav_downloads) {
                    viewPager.setCurrentItem(8);
                    //showSendSnackBar();
                } else if (id == R.id.nav_faq) {
                    viewPager.setCurrentItem(9);
                    //showGallerySnackBar();
*/
                } else if (id == R.id.nav_gj_willmann) {
                    viewPager.setCurrentItem(6);
                    //showGJWillmannBar();
                } else if (id == R.id.nav_mj_mcgivney) {
                    viewPager.setCurrentItem(7);
                    //showMJMcGivNeyBar();
                }
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }


        });
    }

    private void showSendSnackBar() {
        Snackbar.make(navigation, "Downloads", Snackbar.LENGTH_SHORT).show();

    }

    private void showGallerySnackBar() {
        Snackbar.make(navigation, "Frequently Ask Question", Snackbar.LENGTH_SHORT).show();

    }

    private void showToolSnackBar() {
        Snackbar.make(navigation, "My Profiles", Snackbar.LENGTH_SHORT).show();
    }

    private void showShareSnackBar() {
        Snackbar.make(navigation, "Settings", Snackbar.LENGTH_SHORT).show();
    }

    private void showMJMcGivNeyBar() {
        Snackbar.make(navigation, "Prayer for the Canonization of Fr. Michael J. McGivney", Snackbar.LENGTH_SHORT).show();
        }
    private void showGJWillmannBar() {
        Snackbar.make(navigation, "Prayer for the Cause of Fr. George J. Willmann, SJ", Snackbar.LENGTH_SHORT).show();
        }


//    private static class KofCFragmentPageAdapter extends FragmentPagerAdapter {
      private static class KofCFragmentPageAdapter extends FragmentPagerAdapter {

        public KofCFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return MainFragment.newInstance();
                case 1:
                    return ProductFragment.newInstance();
                case 2:
                    return TextConnectFragment.newInstance();
//                case 3:
//                    return BenefitCertificateFragment.newInstance();
                case 3:
                    return KCFAPIPortalFragment.newInstance();
                case 4:
                    return ShieldFragment.newInstance();
                case 5:
                    return MyProfilesFragment.newInstance();
/*
                case 7:
                    return SettingsFragment.newInstance();
                case 8:
                    return DownloadsFragment.newInstance();
                case 9:
                    return FrequentlyAskedQuestionsFragment.newInstance();
*/
                case 6:
                    return FrGeorgeJWillmannFragment.newInstance();
                case 7:
                    return FrMichaelJMcGivneyFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 8;
        }
    }

    public String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }

}

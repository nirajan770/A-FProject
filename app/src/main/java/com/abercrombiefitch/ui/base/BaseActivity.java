package com.abercrombiefitch.ui.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.abercrombiefitch.R;
import com.abercrombiefitch.util.Log;
import com.abercrombiefitch.util.PrefUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Base activity that implements the common functionality
 *
 * Created by Nirajan on 9/27/2015.
 */
public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    // Primary toolbar
    @Bind(R.id.toolbar)
    Toolbar mActionBarToolbar;

    // Navigation drawer:
    @Nullable
    @Bind(R.id.drawer)
    DrawerLayout mDrawerLayout;

    @Nullable
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private static final int NAVDRAWER_LAUNCH_DELAY = 250;
    private static final String STATE_SELECTED_ID = "selected_navigation_drawer_position";
    private int mCurrentSelectedId;
    private boolean mFromSavedInstanceState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize the Google Analytics Manager
        //AnalyticsManager.initializeAnalyticsTracker(getApplicationContext());

        // initialize the Shared Preferences
        PrefUtils.init(this);
        if (savedInstanceState != null) {
            mCurrentSelectedId = savedInstanceState.getInt(STATE_SELECTED_ID);
            Log.d(TAG, "current selection index: "+ mCurrentSelectedId);
            mFromSavedInstanceState = true;
        } else {
            mCurrentSelectedId = R.id.shop;
        }

        ActionBar ab = getSupportActionBar();
        if(ab != null)
            ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void setContentView(int layoutResId){
        super.setContentView(layoutResId);
        ButterKnife.bind(this);
        getActionBarToolbar();
    }

    /**
     * Retrieves the toolbar
     * @return Toolbar
     */
    protected Toolbar getActionBarToolbar() {
        if (mActionBarToolbar != null) {
            setSupportActionBar(mActionBarToolbar);
        }
        return mActionBarToolbar;
    }

    @Override
    protected void onResume(){
        super.onResume();
        // Recommended to check for Play Services on Activity's resume too
        checkPlayServices();
        // Register Bus provider
        //BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Always unregister when an object no longer should be on the bus.
        //BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onBackPressed(){
        if(mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawers();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState){
        super.onPostCreate(savedInstanceState);
        setupNavDrawer();
        //trySetupSwipeRefresh();
    }

    /**
     * Set up the navigation view layout and items
     */
    private void setupNavDrawer(){
        if (mDrawerLayout == null) {
            return;
        }
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout, mActionBarToolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        // Check if the drawer welcome preference is true or false
        if(!PrefUtils.isDrawerWelcomeDone(this) && !mFromSavedInstanceState ){
            mDrawerLayout.openDrawer(GravityCompat.START);
            PrefUtils.markDrawerWelcomeDone(this);
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        // Set up the navigation layout and items
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                final int id = menuItem.getItemId();
                mCurrentSelectedId = id;

                menuItem.setChecked(true);

                if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawers();
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        selectNavigationItem(id);
                    }
                }, NAVDRAWER_LAUNCH_DELAY);

                return true;
            }
        });
        // Set the current selected item
        mNavigationView.getMenu().findItem(mCurrentSelectedId).setChecked(true);

    }

    /**
     * Navigation Item select
     */
    private void selectNavigationItem(int id){
        switch(id){
            case R.id.shop:
                Log.i(TAG, "Drawer Menu Item Shop");

            case R.id.store_locator:
                Log.i(TAG, "Drawer Menu Item Store Locator");
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_ID, mCurrentSelectedId);
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices(){
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS){
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }




}

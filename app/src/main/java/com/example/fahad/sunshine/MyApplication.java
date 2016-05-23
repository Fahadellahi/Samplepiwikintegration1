package com.example.fahad.sunshine;

import android.app.Application;
import android.util.Log;

import org.piwik.sdk.Piwik;
import org.piwik.sdk.PiwikApplication;
import org.piwik.sdk.Tracker;

import java.net.MalformedURLException;

/**
 * Created by Fahad on 5/22/2016.
 */
public class MyApplication extends PiwikApplication {
    private Tracker mPiwikTracker;
    private static MyApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        initPiwik();
        mPiwikTracker.startNewSession();
        Log.d("MyApplication", "Starting new sesion");
    }

    @Override
    public String getTrackerUrl() {
        return "http://demo.piwik.org";
    }

    @Override
    public Integer getSiteId() {
        return 1;
    }

    /**
     * Gives you an all purpose thread-safe persisted Tracker object.
     *
     * @return a shared Tracker
     */
    public synchronized Tracker getTracker() {
        if (mPiwikTracker == null) {
            try {
                mPiwikTracker = getPiwik().newTracker(getTrackerUrl(), getSiteId());
            } catch (MalformedURLException e) {
                e.printStackTrace();
                throw new RuntimeException("Tracker URL was malformed.");
            }
        }
        return mPiwikTracker;
    }


    private void initPiwik() {
        // Print debug output when working on an app.
        getPiwik().setDebug(BuildConfig.DEBUG);

        // When working on an app we don't want to skew tracking results.
        getPiwik().setDryRun(BuildConfig.DEBUG);

        // If you want to set a specific userID other than the random UUID token, do it NOW to ensure all future actions use that token.
        // Changing it later will track new events as belonging to a different user.
        // String userEmail = ....preferences....getString
        // getTracker().setUserId(userEmail);

        // Track this app install, this will only trigger once per app version.
        // i.e. "http://com.piwik.demo:1/185DECB5CFE28FDB2F45887022D668B4"
        getTracker().trackAppDownload(this, Tracker.ExtraIdentifier.APK_CHECKSUM);
        // Alternative:
        // i.e. "http://com.piwik.demo:1/com.android.vending"
        // getTracker().trackAppDownload();
    }
}

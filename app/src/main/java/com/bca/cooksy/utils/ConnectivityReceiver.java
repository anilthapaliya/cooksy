package com.bca.cooksy.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.widget.Toast;

public class ConnectivityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = cm.getActiveNetwork();
        NetworkCapabilities caps = cm.getNetworkCapabilities(network);

        if (network == null || caps == null) {
            Toast.makeText(context, "App is offline.", Toast.LENGTH_LONG).show();
        }
        else if (caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
            Toast.makeText(context, "Device is connected to WiFi.", Toast.LENGTH_LONG).show();
        }
        else if (caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
            Toast.makeText(context, "Mobile data connected. Be careful.", Toast.LENGTH_LONG).show();
        }
    }

}

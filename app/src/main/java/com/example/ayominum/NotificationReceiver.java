package com.example.ayominum;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper.showNotification(
                context,
                "💧 Ayo Minum!",
                "Saatnya minum air agar tubuh tetap terhidrasi."
        );
    }
}
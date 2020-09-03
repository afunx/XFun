package com.ubtechinc.aimbothumming.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ubtechinc.aimbothumming.service.HummingService;
import com.ubtechinc.aimbothumming.utils.LogUtils;

public class HummingReceiver extends BroadcastReceiver {

    private static final String TAG = "HummingReceiver";

    private static final String BROADCAST_HUMMING_ACTION = "com.ubtechinc.aimbothumming.action.enable";

    private static final String BROADCAST_HUMMING_ENABLED = "humming_enabled";

    private static final String BROADCAST_ALARMING_ENABLED = "alarming_enabled";

    private static final boolean HUMMING_STATE_DEFAULT = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (BROADCAST_HUMMING_ACTION.equals(intent.getAction())) {
            if (intent.hasExtra(BROADCAST_HUMMING_ENABLED)) {
                boolean enabled = intent.getBooleanExtra(BROADCAST_HUMMING_ENABLED, HUMMING_STATE_DEFAULT);
                LogUtils.ii(TAG, "onReceive() BROADCAST_HUMMING_ACTION humming_enabled: " + enabled);
                HummingService.startService(context, HummingService.TYPE_HUMMING, enabled);

            } else if (intent.hasExtra(BROADCAST_ALARMING_ENABLED)) {
                boolean enabled = intent.getBooleanExtra(BROADCAST_ALARMING_ENABLED, HUMMING_STATE_DEFAULT);
                LogUtils.ii(TAG, "onReceive() BROADCAST_HUMMING_ACTION alarming_enabled: " + enabled);
                HummingService.startService(context, HummingService.TYPE_ALARMING, enabled);
            }


        }
    }
}

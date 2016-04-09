package com.seedotech.sdtaidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class SdtUtilsService extends Service {
    public SdtUtilsService() {
    }

    /**
     * ISdtUtils definition is below
     */
    private final ISdtUtils.Stub mBinder = new ISdtUtils.Stub() {
        @Override
        public int add(int num1, int num2) throws RemoteException {
            return (num1 + num2);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}

package com.zfy.component.basic.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * CreateAt : 2018/10/11
 * Describe : Service
 *
 * @author chendong
 */
public abstract class AppService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    protected abstract void init();

}

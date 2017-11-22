package soberich.magicdate7;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import soberich.magicdate7.di.components.DaggerIAppComponent;
import soberich.magicdate7.di.components.IAppComponent;

/**
 *
 * Created by soberich on 10/10/17.
 */

public class App extends Application implements HasActivityInjector {

    private static final String TAG = "WholeApp";

    @Inject
    public DispatchingAndroidInjector<Activity> dispatchingActivityInjector;

    private IAppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        DaggerIAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this);
    }

    //    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        Log.d(TAG, "attachBaseContext() called with: base = [" + base + "]");
//
//        app = this;
//
//        appComponent = DaggerIAppComponent
//                .builder()
//                .appModule(new AppModule(this))
//                .layersModule(new LayersModule())
//                .build();
//        appComponent.inject(this);
//    }

    public IAppComponent getAppComponent() {
        Log.d(TAG, "getAppComponent() called");
        return appComponent;
    }

    // FIXME: 10/1/17 DELETE no use
    /* no-op */
    public static App getApp() {
        return app;
    }
    private static App app;

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingActivityInjector;
    }
}
package soberich.magicdate7.di.modules;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import soberich.magicdate7.App;
import soberich.magicdate7.AppExecutors;
import soberich.magicdate7.di.components.IMainActivitySubComponent;
import soberich.magicdate7.di.components.IResEntriesListFragmentSubcomponent;

/**
 *
 * Created by soberich on 10/10/17.
 */

@Module(subcomponents = {
        IMainActivitySubComponent.class,
        IResEntriesListFragmentSubcomponent.class
})
public class AppModule {

    //private Context appContext;

/*    public AppModule(@NonNull Context context) {
        appContext = context;
    }*/

    @Provides
    @NonNull
    @Singleton
    Context provideContext(App app) {
        return app.getApplicationContext();
    }

    @Provides
    @NonNull
    @Singleton
    AppExecutors provideAppExecutors() {
        return new AppExecutors();
    }


    @Provides
    @NonNull
    @Singleton
    Application provideApp(App app) {
        return app;
    }
}

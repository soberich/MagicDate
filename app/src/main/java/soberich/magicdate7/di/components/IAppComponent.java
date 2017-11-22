package soberich.magicdate7.di.components;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import soberich.magicdate7.App;
import soberich.magicdate7.di.modules.AppModule;
import soberich.magicdate7.di.modules.BuildersModule;
import soberich.magicdate7.di.modules.LayersModule;
import soberich.magicdate7.viewmodel.ResEntriesViewModel;

/**
 *
 * Created by soberich on 10/10/17.
 */

@Component(modules = {
        AndroidSupportInjectionModule.class,
        AppModule.class,
        BuildersModule.class,
        LayersModule.class
})
@Singleton
public interface IAppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(App application);
        IAppComponent build();
    }

    void inject(App app);
    //void inject(ResEntriesListFragment resEntriesListFragment);
    void inject(ResEntriesViewModel resEntriesViewModel);


}

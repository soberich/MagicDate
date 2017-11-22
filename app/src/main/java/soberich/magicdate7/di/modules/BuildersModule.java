package soberich.magicdate7.di.modules;

import android.app.Activity;
import android.support.v4.app.Fragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.support.FragmentKey;
import dagger.multibindings.IntoMap;
import soberich.magicdate7.di.components.IMainActivitySubComponent;
import soberich.magicdate7.di.components.IResEntriesListFragmentSubcomponent;
import soberich.magicdate7.view.ui.resultlist.ResEntriesListFragment;
import soberich.magicdate7.view.ui.resultlist.ScrollingActivity;

/**
 *
 * Created by soberich on 10/10/17.
 */

@Module
public abstract class BuildersModule {

    @Binds
    @IntoMap
    @ActivityKey(ScrollingActivity.class)
    public abstract AndroidInjector.Factory<? extends Activity>
            bindMainActivityInjectorFactory(IMainActivitySubComponent.Builder builder);

    @Binds
    @IntoMap
    @FragmentKey(ResEntriesListFragment.class)
    abstract AndroidInjector.Factory<? extends Fragment>
            bindResEntriesListFragmentInjectorFactory(IResEntriesListFragmentSubcomponent.Builder builder);

}

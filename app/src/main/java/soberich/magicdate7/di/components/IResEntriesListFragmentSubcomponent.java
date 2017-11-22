package soberich.magicdate7.di.components;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import soberich.magicdate7.di.modules.ResEntriesListFragmentModule;
import soberich.magicdate7.view.ui.resultlist.ResEntriesListFragment;

/**
 *
 * Created by soberich on 10/10/17.
 */

@Subcomponent(modules = ResEntriesListFragmentModule.class)
public interface IResEntriesListFragmentSubcomponent extends AndroidInjector<ResEntriesListFragment> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<ResEntriesListFragment> {}
}

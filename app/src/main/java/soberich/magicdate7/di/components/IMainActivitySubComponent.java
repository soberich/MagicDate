package soberich.magicdate7.di.components;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import soberich.magicdate7.di.modules.MainActivityModule;
import soberich.magicdate7.view.ui.resultlist.ScrollingActivity;

/**
 *
 * Created by soberich on 10/10/17.
 */


@Subcomponent(modules = MainActivityModule.class)
public interface IMainActivitySubComponent extends AndroidInjector<ScrollingActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<ScrollingActivity> {}
}

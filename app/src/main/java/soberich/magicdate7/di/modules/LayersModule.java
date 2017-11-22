package soberich.magicdate7.di.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import soberich.magicdate7.App;
import soberich.magicdate7.AppExecutors;
import soberich.magicdate7.model.repo.ResEntryRepository;
import soberich.magicdate7.model.repo.local.DatabaseCreator;
import soberich.magicdate7.model.repo.local.ResEntriesDao;

/**
 *
 * Created by soberich on 10/11/17.
 */

@Module
public class LayersModule {

    @Provides
    @NonNull
    @Singleton
    public ResEntryRepository provideResEntryRepository(Context context, ResEntriesDao resEntriesDao, AppExecutors executors) {
        return new ResEntryRepository(context, resEntriesDao, executors);
    }

    @Provides
    @NonNull
    @Singleton
    DatabaseCreator provideDatabaseCreator(App app) {
        return DatabaseCreator.getInstance(app.getApplicationContext());
    }

    @Provides
    @NonNull
    @Singleton
    public ResEntriesDao provideResEntriesDao(DatabaseCreator databaseCreator) {
        return databaseCreator.getDatabase().getResEntriesDao();
    }

}

package soberich.magicdate7.viewmodel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import org.joda.time.LocalDate;

import java.util.List;

import javax.inject.Inject;

import soberich.magicdate7.App;
import soberich.magicdate7.model.repo.ResEntryRepository;
import soberich.magicdate7.model.repo.local.DatabaseCreator;
import soberich.magicdate7.view.viewobjects.ResEntry;

/**
 *
 * Created by soberich on 9/17/17.
 */

public class ResEntriesViewModel extends AndroidViewModel {

    private final LiveData<List<ResEntry>> mNewObservableResEntries;
    private final LiveData<List<ResEntry>> mAllObservableResEntries;
    private final MutableLiveData<LocalDate> mBirthDate = new MutableLiveData<>();

    @Inject public ResEntryRepository mResEntryRepository;
    @Inject public DatabaseCreator mDatabaseCreator;

    private static final MutableLiveData ABSENT;
    static {
        ABSENT = new MutableLiveData();
        //noinspection unchecked
        ABSENT.setValue(null);
    }



    public ResEntriesViewModel(@NonNull Application application/*,
                               @NonNull DatabaseCreator databaseCreator,
                               @NonNull ResEntryRepository resEntryRepository*/) {
        super(application);
//        this.mDatabaseCreator = databaseCreator;
//        this.mResEntryRepository = resEntryRepository;
        LiveData<Boolean> databaseCreated = mDatabaseCreator.isDatabaseCreated();


        ((App)application).getAppComponent().inject(this);

        mAllObservableResEntries =
                Transformations.switchMap(databaseCreated, isDbCreated -> {
                    if(!isDbCreated) return ABSENT;
                    return mResEntryRepository.getAllResEntries();
                });

        mNewObservableResEntries =
                Transformations.switchMap(mBirthDate, localDate -> {
                    if (localDate == null) return ABSENT;
                    return mResEntryRepository.getResEntry(localDate);
                });

        mDatabaseCreator.createDb(this.getApplication());
    }

    public List<ResEntry> getAllResEntries() { return mAllObservableResEntries.getValue(); }

    public LocalDate getBirthDate() { return mBirthDate.getValue(); }

    public LiveData<List<ResEntry>> getNewObservableResEntries() { return mNewObservableResEntries; }

    public void loadEntries(LocalDate localDate) { mBirthDate.setValue(localDate); }
}

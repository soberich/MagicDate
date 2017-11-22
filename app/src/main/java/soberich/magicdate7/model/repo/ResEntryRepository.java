package soberich.magicdate7.model.repo;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import soberich.magicdate7.AppExecutors;
import soberich.magicdate7.model.repo.local.ResEntriesDao;
import soberich.magicdate7.model.repo.remote.ExpandableListDataPump;
import soberich.magicdate7.view.viewobjects.ResEntry;

/**
 *
 * Created by soberich on 10/10/17.
 */

public class ResEntryRepository {

    private static final long REFRESH_DELAY = 60*60;

    private final AppExecutors mExecutors;
    private final Context mContext;
    private final ResEntriesDao mResEntriesDao;

    private MutableLiveData<List<ResEntry>> mAllPreviousResEntries = new MutableLiveData<>();
    private MutableLiveData<List<ResEntry>> mCurrentQueriedResEntries = new MutableLiveData<>();

    private static final MutableLiveData ABSENT;
    private static final MutableLiveData NETWORK_PRESENT;
    static {
        ABSENT = new MutableLiveData();
        NETWORK_PRESENT = new MutableLiveData<>();
        //noinspection unchecked
        ABSENT.setValue(null);
        NETWORK_PRESENT.setValue(Boolean.TRUE);
    }

    @Inject
    public ResEntryRepository(@NonNull final Context context,
                              @NonNull final ResEntriesDao resEntriesDao,
                              @NonNull final AppExecutors executors) {
        this.mContext = context;
        this.mResEntriesDao = resEntriesDao;
        this.mExecutors = executors;
    }

    public LiveData<List<ResEntry>> getAllResEntries() {
        refreshResEntries();
        mExecutors.diskIO().execute(
                () -> mAllPreviousResEntries.setValue(mResEntriesDao.getAllDbData())
        );

        return mAllPreviousResEntries;
    }

    public LiveData<List<ResEntry>> getResEntry(@Nullable final LocalDate localDate) {
        propagateNewQuery(localDate);
        mResEntriesDao.deleteByDate(localDate);
        return mCurrentQueriedResEntries;
    }

    private void propagateNewQuery(@Nullable final LocalDate localDate) {
        mExecutors.networkIO().execute(() -> {
            HashMap<String, List<String>> lastLoadedResultSet =
                    ExpandableListDataPump.getData(mContext, localDate);
            List<ResEntry> resList = new ArrayList<>(3);
            for (String key :
                    lastLoadedResultSet.keySet()) {
                ResEntry temp = new ResEntry();
                temp.setAddedToCal(false);
                temp.setResultDate(LocalDate.parse(key.substring(0, 10)));
                temp.setComment(key.substring(11, key.length() - 1));
                temp.setDescription(lastLoadedResultSet.get(key));
                resList.add(temp);
            }
            mResEntriesDao.saveResEntries(resList);
            mCurrentQueriedResEntries.setValue(resList);
        });
    }
    @SuppressWarnings("unchecked")
    private void refreshResEntries() {
        mExecutors.networkIO().execute(() -> {
            // FIXME: 10/10/17 Temporary stub method before backend is not ready!
            // FIXME: 10/10/17 IF NETWORK AVAILABLE USE RETROFIT
            final LocalDate[] previousQueriesBDates = mResEntriesDao.getPreviousQueriesBDates();
            final List<ResEntry> res = new ArrayList<>();
            mResEntriesDao.deleteAll();
            for (int i = 0; i < previousQueriesBDates.length; i++) {
                HashMap<String, List<String>> lastLoadedResultSet =
                        ExpandableListDataPump.getData(mContext, previousQueriesBDates[i]);
                final List<ResEntry> resList = new ArrayList<>(3);
                for (String key :
                        lastLoadedResultSet.keySet()) {
                    ResEntry temp = new ResEntry();
                    temp.setAddedToCal(false);
                    temp.setResultDate(LocalDate.parse(key.substring(0, 10)));
                    temp.setComment(key.substring(11, key.length() - 1));
                    temp.setDescription(lastLoadedResultSet.get(key));
                    resList.add(temp);
                }
                res.addAll(resList);
                mResEntriesDao.saveResEntries(resList);
            }
            mAllPreviousResEntries.setValue(res);
/*                    Transformations.switchMap(NETWORK_PRESENT*//*networkAvailable*//*, new Function<Boolean, LiveData<List<ResEntry>>>() {
                        @Override
                        public LiveData<List<ResEntry>> apply(Boolean isNetworkAbsent) {
                            if (Boolean.TRUE.equals(isNetworkAbsent)*//*!networkAvailable*//*) return ABSENT;
                            else {
                                final LocalDate[] previousQueriesBDates = mResEntriesDao.getPreviousQueriesBDates();
                                mResEntriesDao.deleteAll();
                                for (int i = 0; i < previousQueriesBDates.length; i++) {
                                    HashMap<String, List<String>> lastLoadedResultSet =
                                            ExpandableListDataPump.getData(mContext, previousQueriesBDates[i]);
                                    List<ResEntry> resList = new ArrayList<>(3);
                                    for (String key :
                                            lastLoadedResultSet.keySet()) {
                                        ResEntry temp = new ResEntry();
                                        temp.setAddedToCal(false);
                                        temp.setResultDate(LocalDate.parse(key.substring(0, 10)));
                                        temp.setComment(key.substring(11, key.length() - 1));
                                        temp.setDescription(lastLoadedResultSet.get(key));
                                        resList.add(temp);
                                    }
                                    mResEntriesDao.saveResEntries(resList);
                                }
                                MutableLiveData<List<ResEntry>> m = new MutableLiveData<>();
                                m.setValue(mResEntriesDao.getAllDbData());
                                return m;
                            }
                        }
                    });*/
        });
    }
}

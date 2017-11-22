package soberich.magicdate7.model.repo.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import org.joda.time.LocalDate;

import java.util.List;

import soberich.magicdate7.view.viewobjects.ResEntry;



/**
 *
 * Created by soberich on 10/10/17.
 */

@Dao
public interface ResEntriesDao {

    String TABLE_NAME = "res_entries";

    @Query("SELECT * FROM " + TABLE_NAME)
    List<ResEntry> getAllDbData();

    @Query("SELECT * FROM " + TABLE_NAME + " WHERE bDate LIKE :bDate")
    LiveData<List<ResEntry>> getCurrentRequestedDbData(LocalDate bDate);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveResEntries(List<ResEntry> resEntries);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveResEntry(ResEntry resEntry);

    @Delete
    void delete(ResEntry resEntry);

    @Query("DELETE FROM " + TABLE_NAME)
    void deleteAll();

    @Query("DELETE FROM " + TABLE_NAME + " WHERE bDate LIKE :bDate")
    void deleteByDate(LocalDate bDate);

    @Query("SELECT DISTINCT bDate FROM " + TABLE_NAME)
    LocalDate[] getPreviousQueriesBDates();

}

package soberich.magicdate7.model.repo.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import soberich.magicdate7.view.viewobjects.ResEntry;

/**
 *
 * Created by soberich on 10/10/17.
 */
@Database(entities = ResEntry.class, version = 1)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase  extends RoomDatabase {
    public static final String DATABASE_NAME = "magicdate.db";
    public abstract ResEntriesDao getResEntriesDao();
}

package rock.sinsuenios.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import rock.sinsuenios.data.database.converters.DateConverter
import rock.sinsuenios.data.database.dao.TracksDAO
import rock.sinsuenios.data.database.entities.Tracks

/**
 * Thank you
 * https://medium.com/mindorks/android-architecture-components-room-and-kotlin-f7b725c8d1d
 */
@Database(entities = arrayOf(Tracks::class), version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tracksDAO(): TracksDAO

    companion object {
        private var INSTANCE: AppDatabase? = null

        /**
         * Create a instance of database using room
         * @param context of activity
         * @return INSTANCE, which contain the implementation of AppDatabase
         */
        fun getAppDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java,
                            "SinSuenios.db")
                            .build()
                }

            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}

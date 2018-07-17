package rock.sinsuenios.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import rock.sinsuenios.data.database.entities.Tracks

@Dao
interface TracksDAO {

    @get:Query("SELECT track_table.* FROM track_table")
    val tracks: List<Tracks>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tracks: Tracks)

    @Delete
    fun delete(tracks: Tracks)

}

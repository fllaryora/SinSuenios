package rock.sinsuenios.data.database.dao

import android.arch.paging.DataSource
import android.arch.persistence.room.*
import rock.sinsuenios.data.database.entities.Disks

@Dao
interface DisksDAO {
    @Query("SELECT disk_table.* FROM disk_table")
    fun disks(): DataSource.Factory<Int,Disks>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(disks: Disks):Long

    @Delete
    fun delete(disks: Disks)
}
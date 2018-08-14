package rock.sinsuenios.data.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.v7.util.DiffUtil
import rock.sinsuenios.data.util.Constant
import java.util.Date


@Entity(tableName = Constant.DISK_TABLE)
data class Disks(
        /*@PrimaryKey(autoGenerate = true) @ColumnInfo(name = Constant.ID_FIELD) var id: Long?,*/
        @PrimaryKey @ColumnInfo(name = Constant.ID_FIELD) var id: Long,
        @ColumnInfo(name = Constant.DISK_NAME_FIELD) var diskName: String,
        @ColumnInfo(name = Constant.BEGIN_DATE_FIELD) var beginDate: Date,
        @ColumnInfo(name = Constant.END_DATE_FIELD) var endDate: Date,
        var lastRefresh: Date) {

    /**
     * Used in pagining
     */
    companion object {
        val diffUtil: DiffUtil.ItemCallback<Disks> = object : DiffUtil.ItemCallback<Disks>() {
            override fun areItemsTheSame(oldItem: Disks?, newItem: Disks?): Boolean {
                return oldItem?.id == newItem?.id
            }

            override fun areContentsTheSame(oldItem: Disks?, newItem: Disks?): Boolean {
                return oldItem == newItem
            }
        }
    }

}

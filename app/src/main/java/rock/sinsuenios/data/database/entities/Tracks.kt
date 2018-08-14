package rock.sinsuenios.data.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import android.support.v7.util.DiffUtil
import rock.sinsuenios.data.util.Constant
import java.util.Date



@Entity( tableName = Constant.TRACK_TABLE,
         foreignKeys = arrayOf(ForeignKey(entity = Disks::class,
           parentColumns = arrayOf(Constant.ID_FIELD),
           childColumns = arrayOf(Constant.DISK_ID_FIELD),
           onDelete = ForeignKey.CASCADE)))
data class Tracks(@PrimaryKey(autoGenerate = true)
                  @ColumnInfo(name = Constant.ID_FIELD) var id: Long?,
                  @ColumnInfo(name = Constant.DISK_ID_FIELD) var diskId: Long,
                  @ColumnInfo(name = Constant.TRACK_NAME_FIELD) var trackName: String,
                  @ColumnInfo(name = Constant.LYRIC_FIELD) var lyric: String,
                  @ColumnInfo(name = Constant.TRACK_SONG_FIELD) var trackSong: Int,
                  var lastRefresh: Date) {

    /**
     * Used in pagining
     */
    companion object {
        val diffUtil: DiffUtil.ItemCallback<Tracks> = object : DiffUtil.ItemCallback<Tracks>() {
            override fun areItemsTheSame(oldItem: Tracks?, newItem: Tracks?): Boolean {
                return oldItem?.id == newItem?.id
            }

            override fun areContentsTheSame(oldItem: Tracks?, newItem: Tracks?): Boolean {
                return oldItem == newItem
            }
        }
    }

}

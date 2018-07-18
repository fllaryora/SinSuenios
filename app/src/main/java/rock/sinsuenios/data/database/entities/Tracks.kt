package rock.sinsuenios.data.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.v7.util.DiffUtil
import java.util.*

@Entity(tableName = "track_table")
data class Tracks(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long?,

                  @ColumnInfo(name = "track_name") var trackName: String,
                  @ColumnInfo(name = "lyric") var lyric: String,
                  @ColumnInfo(name = "track_song") var trackSong: Int,
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

package rock.sinsuenios.data.database.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.Date

@Entity(tableName = "track_table")
class Tracks {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "track_name")
    var trackName: String? = null

    @ColumnInfo(name = "lyric")
    var lyric: String? = null

    //last updated
    var lastRefresh: Date? = null

    constructor(id: Long, lyric: String, lastRefresh: Date) {
        this.id = id
        this.lyric = lyric
        this.lastRefresh = lastRefresh
    }

    constructor() {}

}

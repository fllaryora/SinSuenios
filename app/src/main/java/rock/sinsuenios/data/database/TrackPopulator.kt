package rock.sinsuenios.data.database

import android.content.Context
import android.util.Log
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rock.sinsuenios.R
import rock.sinsuenios.data.database.entities.Tracks
import rock.sinsuenios.data.database.mapper.TrackMapper
import java.util.*

class TrackPopulator(context: Context) {

    private lateinit var mContext: Context

    private val TAG = TrackPopulator::class.java.name

    init {
        mContext = context
    }

    /**
     * This is the private constructor of java
     */
    companion object {
        fun with(context: Context): TrackPopulator {
            return TrackPopulator(context)
        }
    }

    private fun onDBPopulationSuccess() {
        Log.d(TAG, "Tracks inserted successfully")
    }

    private fun onDBPopulationFailure(t: Throwable) {
        Log.e(TAG, "Tracks failed to be inserted, error:" + t.message)
    }

    private fun getTrackList(): Array<Tracks> {
        val tracks = ArrayList<Tracks>()
        val trackNames : Array<String> = mContext.getResources().getStringArray(R.array.track_names)
        val trackLyrics = mContext.getResources().getStringArray(R.array.track_lyrics)

        for (i in trackNames.indices)
            tracks.add(Tracks(null, trackNames[i], trackLyrics[i], TrackMapper.getTrackResourceFrom(trackNames[i]).trackNumber, Date()) )
        return tracks.toTypedArray<Tracks>()
    }

    fun populateDB() {
        Completable.fromAction {
            getTrackList().forEach { track ->
                AppDatabase.getAppDatabase(mContext)!!.tracksDAO().insert(track)
            }
        }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onDBPopulationSuccess, this::onDBPopulationFailure)
    }
}
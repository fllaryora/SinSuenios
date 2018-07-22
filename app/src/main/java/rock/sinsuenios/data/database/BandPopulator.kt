package rock.sinsuenios.data.database

import android.content.Context
import android.util.Log
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rock.sinsuenios.R
import rock.sinsuenios.data.database.entities.Disks
import rock.sinsuenios.data.database.entities.Tracks
import rock.sinsuenios.data.database.mapper.TrackMapper
import java.util.*
import kotlin.collections.ArrayList

class BandPopulator(context: Context) {

    private lateinit var mContext: Context

    private val TAG = BandPopulator::class.java.name

    init {
        mContext = context
    }

    /**
     * This is the private constructor of java
     */
    companion object {
        fun with(context: Context): BandPopulator {
            return BandPopulator(context)
        }
    }

    private fun onDBPopulationSuccess() {
        Log.d(TAG, "Tracks inserted successfully")
    }

    private fun onDBPopulationFailure(t: Throwable) {
        Log.e(TAG, "Tracks failed to be inserted, error:" + t.message)
    }

    private fun getDiskList(): Array<Disks> {
        val disks = ArrayList<Disks>()
        val disksNames : Array<String> = mContext.getResources().getStringArray(R.array.disk_names)
        val diskBeginDate : List<Date> = mContext.getResources().getStringArray(R.array.begin_dates)
                .map { dateInMillis ->
                    Date(dateInMillis.toLong())
                }
        val diskEndDate : List<Date> = mContext.getResources().getStringArray(R.array.end_dates)
                .map { dateInMillis ->
                    Date(dateInMillis.toLong())
                }
        val diskIds : List<Long> = mContext.getResources().getStringArray(R.array.disk_ids)
                .map { idInString ->
                    idInString.toLong()
                }
        for (i in disksNames.indices){
           disks.add(Disks( diskIds[i], disksNames[i],diskBeginDate[i],diskEndDate[i], Date()))
        }

        return disks.toTypedArray<Disks>()
    }

    private fun getTrackList(): Array<Tracks> {
        val tracks = ArrayList<Tracks>()
        val trackNames : Array<String> = mContext.getResources().getStringArray(R.array.track_names)
        val trakDiskIds : List<Long> = mContext.getResources().getStringArray(R.array.trak_disk_ids)
                .map { idInString ->
                    idInString.toLong()
                }
        val trackLyrics = mContext.getResources().getStringArray(R.array.track_lyrics)

        for (i in trackNames.indices)
            tracks.add(Tracks(null, trakDiskIds[i], trackNames[i], trackLyrics[i], TrackMapper.getTrackResourceFrom(trackNames[i]).trackNumber, Date()) )
        return tracks.toTypedArray<Tracks>()
    }

    fun populateDB() {
        Completable.fromAction {
            getDiskList().forEach { disks: Disks ->
                AppDatabase.getAppDatabase(mContext)!!.disksDAO().insert(disks)
            }
            getTrackList().forEach { track ->
                AppDatabase.getAppDatabase(mContext)!!.tracksDAO().insert(track)
            }

        }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::onDBPopulationSuccess, this::onDBPopulationFailure)

    }
}
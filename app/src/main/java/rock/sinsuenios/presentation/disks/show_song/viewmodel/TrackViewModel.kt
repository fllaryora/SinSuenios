package rock.sinsuenios.presentation.disks.show_song.viewmodel

import android.arch.paging.PagedList
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import rock.sinsuenios.SinSueniosApplication
import rock.sinsuenios.data.database.AppDatabase
import rock.sinsuenios.data.database.entities.Tracks

class TrackViewModel : ViewModel() {

    internal val trackList: LiveData<PagedList<Tracks>>

    init {

        val tracksDAO = AppDatabase.getAppDatabase(SinSueniosApplication.applicationContext())!!.tracksDAO()
        val pagedListConfig : PagedList.Config = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(INITIAL_LOAD_KEY)
                .setPrefetchDistance(PREFETCH_DISTANCE)
                .setPageSize(PAGE_SIZE).build()
        trackList = LivePagedListBuilder( tracksDAO.tracks(), pagedListConfig).build()
    }

    companion object {
        private const val INITIAL_LOAD_KEY = 0
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 5
    }
}
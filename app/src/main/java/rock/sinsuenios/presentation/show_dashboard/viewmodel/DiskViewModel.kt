package rock.sinsuenios.presentation.show_dashboard.viewmodel

import android.arch.paging.PagedList
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import rock.sinsuenios.SinSueniosApplication
import rock.sinsuenios.data.database.AppDatabase
import rock.sinsuenios.data.database.entities.Disks

class DiskViewModel : ViewModel() {

    internal val disksList: LiveData<PagedList<Disks>>

    init {
        val disksDAO = AppDatabase.getAppDatabase(SinSueniosApplication.applicationContext())!!.disksDAO()
        val pagedListConfig : PagedList.Config = PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(INITIAL_LOAD_KEY)
                .setPrefetchDistance(PREFETCH_DISTANCE)
                .setPageSize(PAGE_SIZE).build()
        disksList = LivePagedListBuilder( disksDAO.disks(), pagedListConfig).build()
    }

    companion object {
        private const val INITIAL_LOAD_KEY = 0
        private const val PAGE_SIZE = 20
        private const val PREFETCH_DISTANCE = 5
    }
}
package rock.sinsuenios

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import rock.sinsuenios.data.database.BandPopulator


class SinSueniosApplication : Application(){

    init {
        instance = this
    }

    companion object {
        private lateinit var instance: SinSueniosApplication

        fun applicationContext() : Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        // initialize for any
        val context: Context = SinSueniosApplication.applicationContext()
        intiDB()
    }

    val KEY_IS_DB_POPULATED = "DB_IS_POPULATED"

    private fun intiDB() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        if (!preferences.getBoolean(KEY_IS_DB_POPULATED, false)) {
            BandPopulator.with(this).populateDB()
            preferences.edit().putBoolean(KEY_IS_DB_POPULATED, true).apply()
        }
    }

}
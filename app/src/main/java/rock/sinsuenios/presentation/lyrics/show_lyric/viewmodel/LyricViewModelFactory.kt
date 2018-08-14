package rock.sinsuenios.presentation.lyrics.show_lyric.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import rock.sinsuenios.data.music.MusicBackground

class LyricViewModelFactory constructor(private val musicBackground: MusicBackground): ViewModelProvider.Factory{
    /**
     * Creates a new instance of the given `Class`.
     *
     *
     *
     * @param modelClass a `Class` whose instance is requested
     * @param <T>        The type parameter for the ViewModel.
     * @return a newly created ViewModel
    </T> */
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LyricViewModel::class.java)) {
            return LyricViewModel(musicBackground) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
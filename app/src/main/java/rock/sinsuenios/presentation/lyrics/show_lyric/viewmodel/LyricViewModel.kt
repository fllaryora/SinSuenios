package rock.sinsuenios.presentation.lyrics.show_lyric.viewmodel;

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import rock.sinsuenios.data.exception.Failure
import rock.sinsuenios.data.music.MusicBackground
import rock.sinsuenios.data.util.Constant
import rock.sinsuenios.domain.interactor.UseCase
import rock.sinsuenios.domain.interactor.music.UpdateProgress
import rock.sinsuenios.domain.interactor.music.OnDestroy
import rock.sinsuenios.domain.interactor.music.OnPause
import rock.sinsuenios.domain.interactor.music.PlayPause
import rock.sinsuenios.domain.interactor.music.SeekTo
import rock.sinsuenios.domain.interactor.music.SetLoop
import rock.sinsuenios.presentation.lyrics.show_lyric.view.LyricView

class LyricViewModel(val musicBackground: MusicBackground) : ViewModel() {
    //https://proandroiddev.com/architecture-components-modelview-livedata-33d20bdcc4e9
    //never used (my hopes)
    var failure: MutableLiveData<Failure> = MutableLiveData()

    var lyric: MutableLiveData<LyricView> = MutableLiveData()

    private var updateProgress: UpdateProgress = UpdateProgress(musicBackground)
    private var mOnDestroy: OnDestroy = OnDestroy(musicBackground)
    private var mOnPause: OnPause = OnPause(musicBackground)
    private var mPlayPause: PlayPause = PlayPause(musicBackground)
    private var mSeekTo: SeekTo = SeekTo(musicBackground)
    private var mSetLoop: SetLoop = SetLoop(musicBackground)

    init {
        lyric.value = LyricView(0, 0, getFormattedTime(0), getFormattedTime(0),false,false)
    }

    private fun handleFailure(failure: Failure) {
        this.failure.value = failure
    }

    private fun getFormattedTime( mSec :Int):String {
        val secs:Int = mSec/ 1000
        val minutes : Int = secs / 60
        /* For java programmers: mod function (%) is deprecated because it can return negative values
         and that is considered wrong in kotlin. In the other hand rem is the remainder of the division
        and make sense return negative values */
        var seconds : Int = secs.rem(60)
        if (seconds < 0) seconds += 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun handleUpdateProgressResponse(hashMap: HashMap<String, Any>) {
        lyric.value?.let {
            it.totalTime = hashMap[Constant.MUSIC_TOTAL_TIME] as Int
            it.progress = mSecToProgressBar( hashMap[Constant.MUSIC_TOTAL_TIME] as Int ,
                    hashMap[Constant.MUSIC_CURRENT_TIME] as Int,
                    hashMap[Constant.UI_PROGRESSBAR_MAX_VALUE] as Int)
            it.remainingTimeText = getFormattedTime(hashMap[Constant.MUSIC_REMAINING_TIME] as Int)
            it.currentPosText = getFormattedTime(hashMap[Constant.MUSIC_CURRENT_TIME] as Int)
        }
        lyric.value = lyric.value
    }

    private fun handlePlayPauseResponse(isPlaying: Boolean) {
        lyric.value?.let {
            it.isPlaying = isPlaying
        }
        lyric.value = lyric.value
    }

    private fun handleLoopResponse(isLoop: Boolean) {
        lyric.value?.let {
            it.isLooping = isLoop
        }
        lyric.value = lyric.value
    }

    fun updateProgress(maxProgress:Int){
        updateProgress(UpdateProgress.Params(maxProgress)) { it.either(::handleFailure, ::handleUpdateProgressResponse) }
    }


    fun seekTo(progress: Int, maxProgress:Int) {
        lyric.value?.let {
            val mSec:Int = progressBarToMSec(it.totalTime, progress, maxProgress)
            mSeekTo(SeekTo.Params(mSec))
        }
        lyric.value = lyric.value
    }

    fun setLoop() =
            mSetLoop(UseCase.None()) { it.either(::handleFailure, ::handleLoopResponse) }

    fun playPause() =
            mPlayPause(UseCase.None()){ it.either(::handleFailure, ::handlePlayPauseResponse) }


    fun onPause() =
            mOnPause(UseCase.None())

    fun onDestroy() =
            mOnDestroy(UseCase.None())

    /**
     *
     * Change the progress X of a bar of Y max progress
     * to msec
     * @param progress current progres of seek bar
     * @param maxProgress max progress of seek bar
     * @param maxMSecTime max milliseconds of a track
     */
    private fun progressBarToMSec(maxMSecTime: Int, progress: Int, maxProgress: Int) :Int{
        if(maxProgress != 0 ){
            return (maxMSecTime* progress) / maxProgress
        }  else {
            return 0
        }
    }


    /**
     * Change the msec of track of maxMSecTime
     * to progress X of  a bar of Y max progress
     *
     * @param maxProgress max progress of seek bar
     * @param maxMSecTime max milliseconds of a track
     * @param currentMSec current milliseconds of a track
     */
    private fun mSecToProgressBar(maxMSecTime: Int, currentMSec: Int, maxProgress: Int):Int {
        return if(maxMSecTime != 0 ){ (maxProgress* currentMSec) / maxMSecTime   }
        else {   0   }

    }


}
package rock.sinsuenios.data.music

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import rock.sinsuenios.data.util.Constant

//@Suppress("DEPRECATION")
//setAudioStreamType is deprecated in api 26
//and offer setAudioAttributes(AudioAttributes)
//but AudioAttributes builder use min sdk 21
//so I suppress the deprecation
class MusicBackground(mContext : Context, mMusicResource :Int) {

    private var mMediaPlayer: MediaPlayer
    //call it in OnCreate
    init {
        mMediaPlayer = MediaPlayer.create(mContext, mMusicResource)
        mMediaPlayer.isLooping = false
        mMediaPlayer.seekTo(0)
        mMediaPlayer.setVolume(1.0f, 1.0f)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mMediaPlayer.setAudioAttributes( AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build())
        } else {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
        }
    }

    fun onPause() {
        if (mMediaPlayer.isPlaying) {
            mMediaPlayer.pause()
        }
    }

    fun onDestroy() {
        if (mMediaPlayer.isPlaying) {
            mMediaPlayer.stop()
        }
        mMediaPlayer.release()
    }

    //onResume or further
    fun playPause() : Boolean{
        if (mMediaPlayer.isPlaying) {
            mMediaPlayer.pause()
        } else {
            mMediaPlayer.start()
        }
        return mMediaPlayer.isPlaying
    }

    fun setLoop() : Boolean{
        var newBoolean : Boolean =  !mMediaPlayer.isLooping
        mMediaPlayer.isLooping = !mMediaPlayer.isLooping
        return newBoolean
    }


     fun getCurrentStatus(maxProgress:Int): HashMap<String, Any> {
         val status : HashMap<String, Any> = hashMapOf(
                 Constant.MUSIC_CURRENT_TIME        to  mMediaPlayer.currentPosition,
                 Constant.MUSIC_REMAINING_TIME      to (mMediaPlayer.duration - mMediaPlayer.currentPosition),
                 Constant.MUSIC_TOTAL_TIME          to  mMediaPlayer.duration,
                 Constant.UI_PROGRESSBAR_MAX_VALUE  to  maxProgress
         )
        return status
    }

    fun seekTo(msec: Int) {
        mMediaPlayer.seekTo(msec)
    }

}
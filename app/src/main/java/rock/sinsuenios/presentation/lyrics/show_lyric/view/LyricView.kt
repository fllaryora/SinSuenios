package rock.sinsuenios.presentation.lyrics.show_lyric.view

import android.os.Parcel
import rock.sinsuenios.presentation.platform.KParcelable
import rock.sinsuenios.presentation.platform.parcelableCreator
import rock.sinsuenios.presentation.platform.readBoolean
import rock.sinsuenios.presentation.platform.writeBoolean

data class LyricView(var progress : Int, var totalTime : Int,
                     var remainingTimeText : String,var currentPosText : String,
                     var isPlaying : Boolean, var isLooping : Boolean):
        KParcelable {
    companion object {
        @JvmField val CREATOR = parcelableCreator(
                ::LyricView)
    }

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readInt(),
            parcel.readString(),parcel.readString(),
            parcel.readBoolean(),parcel.readBoolean())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(progress)
            writeInt(totalTime)
            writeString(remainingTimeText)
            writeString(currentPosText)
            writeBoolean(isPlaying)
            writeBoolean(isLooping)
        }
    }

}
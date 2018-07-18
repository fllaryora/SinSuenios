package rock.sinsuenios.data.database.mapper

import rock.sinsuenios.R
import java.text.Normalizer

enum class TrackMapper(val trackNumber: Int, val trackResource: Int) {
    CICATRICES(1,R.raw.cicatrices);

    companion object {
        fun getTrackResourceFrom (track: String): TrackMapper {
            val trackSanitized: String = Normalizer.normalize(track, Normalizer.Form.NFD).toUpperCase()
            return TrackMapper.valueOf(trackSanitized)
        }

        fun getTrackResourceFrom (trackNumber: Int): TrackMapper? {
            return TrackMapper.values().find { it.trackNumber == trackNumber }
        }
    }
}
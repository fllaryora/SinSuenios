package rock.sinsuenios.data.database.mapper

import rock.sinsuenios.R
import java.text.Normalizer

enum class TrackMapper(val trackNumber: Int, val trackResource: Int) {
    CICATRICES(1,R.raw.cicatrices),
    MITOMANIAS(2,R.raw.mitomanias),
    CUANDO_EL_SOL_SE_VA(3,R.raw.sol),
    HERMOSO_SUENIO(4,R.raw.suenio),
    PENSANDO_EN_TI(5,R.raw.pensando),
    QUISIERA_SABER(6,R.raw.saber),
    TE_ESCRIBIRE(7,R.raw.escribire),
    TU_EFECTO(8,R.raw.efecto);

    companion object {
        fun getTrackResourceFrom (track: String): TrackMapper {

            return when(track){
                "Cicatrices" -> CICATRICES
                "Mitomanias para el corazón" -> MITOMANIAS
                "Cuando el sol se va" -> CUANDO_EL_SOL_SE_VA
                "Hermoso sueño" -> HERMOSO_SUENIO
                "Pensando en ti" -> PENSANDO_EN_TI
                "Quisiera saber" -> QUISIERA_SABER
                "Te escribiré" -> TE_ESCRIBIRE
                "Tu efecto" -> TU_EFECTO
                else -> throw IllegalArgumentException("Invalid track argument")
            }
        }

        fun getTrackResourceFrom (trackNumber: Int): TrackMapper? {
            return TrackMapper.values().find { it.trackNumber == trackNumber }
        }
    }
}
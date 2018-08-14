package rock.sinsuenios.domain.interactor.music

import rock.sinsuenios.data.exception.Failure
import rock.sinsuenios.data.functional.Either
import rock.sinsuenios.data.music.MusicBackground
import rock.sinsuenios.domain.interactor.UseCase

class SeekTo constructor(private val musicBackground : MusicBackground) : UseCase<UseCase.None, SeekTo.Params>(){
    override suspend fun run(params: Params): Either<Failure, None> {
        musicBackground.seekTo(params.mSec)
        return Either.Right(None())
    }
    data class Params(val mSec: Int)
}
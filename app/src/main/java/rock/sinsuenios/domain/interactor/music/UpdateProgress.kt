package rock.sinsuenios.domain.interactor.music

import rock.sinsuenios.data.exception.Failure
import rock.sinsuenios.data.functional.Either
import rock.sinsuenios.data.music.MusicBackground
import rock.sinsuenios.domain.interactor.UseCase

class UpdateProgress
    constructor(private val musicBackground : MusicBackground) :
        UseCase<HashMap<String, Any>, UpdateProgress.Params>() {
    override suspend fun run(params: Params): Either<Failure, HashMap<String, Any>> =
            Either.Right(musicBackground.getCurrentStatus(params.maxProgress))
    data class Params(val maxProgress: Int)

}
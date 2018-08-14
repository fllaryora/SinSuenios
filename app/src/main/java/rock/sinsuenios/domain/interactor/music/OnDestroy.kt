package rock.sinsuenios.domain.interactor.music

import rock.sinsuenios.data.exception.Failure
import rock.sinsuenios.data.functional.Either
import rock.sinsuenios.data.music.MusicBackground
import rock.sinsuenios.domain.interactor.UseCase

class OnDestroy constructor(private val musicBackground : MusicBackground) : UseCase<UseCase.None, UseCase.None>(){
    override suspend fun run(params: None): Either<Failure, None> {
        musicBackground.onDestroy()
        return Either.Right(None())
    }
}
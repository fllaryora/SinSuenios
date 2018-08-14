package rock.sinsuenios.data.exception

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure {
    class NetworkConnection: Failure()
    class ServerError: Failure()
    class ListNotAvailable : Failure()
    class NonExistentDetail : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure: Failure()
}
package rock.sinsuenios.data.database

import android.os.Handler
import android.os.Message

/**
 * It is in charge of put room operation in background
 * @author Francisco Llaryora
 * @version 1.0
 * @since 1.0
 */

class DatabaseHandler(val database: AppDatabase, private val callback: Callback?) : Handler.Callback {
    private lateinit var handler: Handler

    interface Callback {
        fun onDatabaseOperationFinished()
    }

    init {
        handler = Handler(this)
    }

    fun execute(body: Runnable) {
        Thread(Runnable {
            database.runInTransaction(body)
            handler.sendEmptyMessage(OPERATION_SUCCESSFUL)
        }).start()
    }


    override fun handleMessage(msg: Message): Boolean {
        if (callback != null) {
            if (OPERATION_SUCCESSFUL == msg.what) {
                callback.onDatabaseOperationFinished()
                return true
            }
        }
        return false
    }

    companion object {
        val OPERATION_SUCCESSFUL = 1
    }

}

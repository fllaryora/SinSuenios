package rock.sinsuenios.presentation.show_notifications.view

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rock.sinsuenios.R

class NotificationFragment : Fragment() {
    companion object {
        fun newInstance(): NotificationFragment {
            return NotificationFragment()
        }
    }
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }
}
package rock.sinsuenios.presentation.show_dashboard

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rock.sinsuenios.R

class DashboardFragment : Fragment() {
    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }
}
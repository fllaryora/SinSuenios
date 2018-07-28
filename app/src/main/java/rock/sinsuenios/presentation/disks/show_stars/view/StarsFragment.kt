package rock.sinsuenios.presentation.disks.show_stars.view

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rock.sinsuenios.R

class StarsFragment : Fragment(){
    companion object {
        fun newInstance(): StarsFragment {
            return StarsFragment()
        }
    }
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stars, container, false)
    }
}
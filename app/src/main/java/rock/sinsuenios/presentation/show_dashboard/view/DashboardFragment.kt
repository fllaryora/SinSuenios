package rock.sinsuenios.presentation.show_dashboard.view

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import rock.sinsuenios.R
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import rock.sinsuenios.presentation.show_dashboard.listing.TrackAdapter
import rock.sinsuenios.presentation.show_dashboard.viewmodel.TrackViewModel


class DashboardFragment : Fragment() {
    private lateinit var mRecyclerView : RecyclerView
    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view : View = inflater.inflate(R.layout.fragment_dashboard, container, false)
        //the view model
        val viewModel = ViewModelProviders.of(this).get(TrackViewModel::class.java)
        mRecyclerView = view.findViewById(R.id.recycler_view_dashboard) as RecyclerView
        val layoutManager = LinearLayoutManager(this.activity)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.itemAnimator = DefaultItemAnimator()
        val adapter = TrackAdapter()
        /**
         * submitList or setList belong from PagedListAdapter
         * you have to look inside to see what is the name of the method
         */
        viewModel.trackList.observe(this, Observer(adapter::submitList))
        mRecyclerView.adapter = adapter
        return view
    }
}


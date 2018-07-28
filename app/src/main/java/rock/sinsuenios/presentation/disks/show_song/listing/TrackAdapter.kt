package rock.sinsuenios.presentation.show_dashboard.listing

import android.arch.paging.PagedListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import rock.sinsuenios.R
import rock.sinsuenios.SinSueniosApplication.Companion.applicationContext
import rock.sinsuenios.data.database.entities.Tracks
import rock.sinsuenios.presentation.disks.show_song.listing.TrackViewHolder

class TrackAdapter:PagedListAdapter<Tracks, TrackViewHolder>(Tracks.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackViewHolder {
        val itemView = LayoutInflater.from(applicationContext()).inflate(R.layout.card_row, parent, false)
        return TrackViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TrackViewHolder, position: Int) {
        val tracks = getItem(position)
        if (tracks != null) {
            holder.bindTo(tracks)
        } else {
            holder.clear()
        }

    }
}
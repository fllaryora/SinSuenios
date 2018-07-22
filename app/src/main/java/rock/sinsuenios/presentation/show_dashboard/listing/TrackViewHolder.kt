package rock.sinsuenios.presentation.show_dashboard.listing

import android.support.v7.widget.RecyclerView
import android.view.View
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import rock.sinsuenios.R
import rock.sinsuenios.data.database.entities.Tracks

class TrackViewHolder (itemView: View?): RecyclerView.ViewHolder(itemView) {

    private var ticketTextView: AppCompatTextView? = null
    //private var ticketImageView: AppCompatImageView? = null

    init {
        //La llamada al super del padre la hace en RecyclerView.ViewHolder(itemView)
        ticketTextView = itemView!!.findViewById(R.id.ticket_title) as AppCompatTextView
        //ticketImageView = itemView.findViewById(R.id.ticket_image) as AppCompatImageView
    }

    fun bindTo(tracks: Tracks) {
        itemView.tag = tracks.id
        ticketTextView!!.text = tracks.trackName
       // ticketImageView!!.setImageResource(R.drawable.track)

    }

    fun clear() {
        itemView.invalidate()
        ticketTextView!!.invalidate()
        //ticketImageView!!.invalidate()
    }

}

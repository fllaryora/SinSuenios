package rock.sinsuenios.presentation.disks.show_song.listing

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.support.v7.widget.AppCompatTextView
import rock.sinsuenios.R
import rock.sinsuenios.data.database.entities.Tracks
import rock.sinsuenios.presentation.lyrics.LyricsActivity



class TrackViewHolder (itemView: View?): RecyclerView.ViewHolder(itemView) , View.OnClickListener{

    private lateinit var ticketTextView: AppCompatTextView
    private lateinit var lyricText: String
    private var musicResource: Int? = null

    init {
        //La llamada al super del padre la hace en RecyclerView.ViewHolder(itemView)
        if (itemView != null) {
            ticketTextView = itemView.findViewById<AppCompatTextView>(R.id.ticket_title)
            val typeface = Typeface.createFromAsset(itemView.context.assets,
                    itemView.context.getString(R.string.track_typeface))

            ticketTextView.typeface = typeface
            val shader = LinearGradient(
                    0f, 0f, 0f, ticketTextView.textSize,
                    Color.RED, Color.YELLOW,
                    Shader.TileMode.CLAMP
            )
            ticketTextView.paint.shader = shader

            itemView.setOnClickListener(this)
        }
    }

    fun bindTo(tracks: Tracks) {
        itemView.tag = tracks.id
        ticketTextView.text = tracks.trackName
        lyricText = tracks.lyric
        musicResource = tracks.trackSong

    }

    fun clear() {
        itemView.invalidate()
        ticketTextView!!.invalidate()
    }

    override fun onClick(view: View?) {
        if (view != null) {
            val intent: Intent = Intent(itemView.context, LyricsActivity::class.java)
            val bundle: Bundle = Bundle()
            bundle.putString(itemView.context.getString(R.string.EXTRA_DISK_ID), itemView.tag.toString())
            bundle.putString(itemView.context.getString(R.string.EXTRA_TRACK_TITLE), ticketTextView.text.toString())
            bundle.putString(itemView.context.getString(R.string.EXTRA_TRACK_LYRIC), lyricText)
            bundle.putInt(itemView.context.getString(R.string.EXTRA_TRACK_RESOURCE_MUSIC_IN_RAW), musicResource!!)
            intent.putExtras(bundle)
            //TODO PUT CIRCULAR EFFECT HERE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val transitionName: String  = view.context.getString(R.string.transition_track_name_title)
                //this time It uses the viewHolder as view of transition
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        view.context as Activity, view, transitionName)
                val revealX :Int = ((view.x + view.width / 2).toInt())
                val revealY :Int = ((view.y + view.height / 2).toInt())
                bundle.putInt(itemView.context.getString(R.string.EXTRA_CIRCULAR_REVEAL_X), revealX)
                bundle.putInt(itemView.context.getString(R.string.EXTRA_CIRCULAR_REVEAL_Y), revealY)
                intent.putExtras(bundle)
                view.context.startActivity(intent,options.toBundle())
            } else {
                itemView.context.startActivity(intent)
            }
        }

    }

}

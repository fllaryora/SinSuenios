package rock.sinsuenios.presentation.show_dashboard.listing

import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Typeface
import android.os.Bundle
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.View
import rock.sinsuenios.R
import rock.sinsuenios.data.database.entities.Disks
import rock.sinsuenios.presentation.disks.DisksActivity

class DiskViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private var cdRoomTextView: AppCompatTextView? = null
    //private var ticketImageView: AppCompatImageView? = null

    init {
        //La llamada al super del padre la hace en RecyclerView.ViewHolder(itemView)
        if (itemView != null) {
            cdRoomTextView = itemView.findViewById<AppCompatTextView>(R.id.cd_room_title)
            val typeface = Typeface.createFromAsset(itemView.context.assets,
                    itemView.context.getString(R.string.cd_room_typeface))
            if (cdRoomTextView != null) {
                cdRoomTextView!!.typeface = typeface
                val shader = LinearGradient(
                        0f, 0f, 0f, cdRoomTextView!!.textSize,
                        Color.RED, Color.YELLOW,
                        Shader.TileMode.CLAMP
                )
                cdRoomTextView!!.paint.shader = shader
            }
            itemView.setOnClickListener(this)
        }
    }

    fun bindTo(disks: Disks) {
        itemView.tag = disks.id
        if (cdRoomTextView != null) {
            cdRoomTextView!!.text = disks.diskName
        }
    }

    fun clear() {
        itemView.invalidate()
        cdRoomTextView?.invalidate()
    }

    override fun onClick(view: View?) {
        if (view != null) {
            val intent: Intent = Intent(view.context, DisksActivity::class.java)
            val bundle: Bundle = Bundle()
            bundle.putString(view.context.getString(R.string.EXTRA_DISK_TITLE), itemView.tag.toString())
            intent.putExtras(bundle)
            //TODO hacer el movimiento del titulo
            view.context.startActivity(intent)
        }

    }

}
package rock.sinsuenios.presentation.show_dashboard.listing

import android.arch.paging.PagedListAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import rock.sinsuenios.R
import rock.sinsuenios.data.database.entities.Disks

class DiskAdapter : PagedListAdapter<Disks, DiskViewHolder>(Disks.diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.disk_row, parent, false)
        return DiskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DiskViewHolder, position: Int) {
        val disks = getItem(position)
        if (disks != null) {
            holder.bindTo(disks)
        } else {
            holder.clear()
        }

    }
}
package rock.sinsuenios.data.database.filters

import java.util.ArrayList
import java.util.Date
import rock.sinsuenios.data.database.entities.Tracks

class TracksFilter {

    fun filterByText(rowList: List<Tracks>, text: String): List<Tracks> {
        val filteredList = ArrayList<Tracks>()
        for (item in rowList) {
            val textSubmited = item.trackName
            if (textSubmited != null && text == textSubmited) {
                filteredList.add(item)
            }
        }
        return filteredList
    }

    fun filterByLastRefresh(rowList: List<Tracks>): Tracks? {
        var filteredElement: Tracks? = null
        var currentDate: Date? = null

        for (item in rowList) {
            val dateOfCurrentItem = item.lastRefresh
            if (currentDate == null) {
                filteredElement = item
                currentDate = dateOfCurrentItem
            } else if (dateOfCurrentItem!!.after(currentDate)) {
                filteredElement = item
                currentDate = dateOfCurrentItem
            }
        }

        return filteredElement
    }

    fun filterByValidUntil(rowList: List<Tracks>): List<Tracks> {
        val filteredList = ArrayList<Tracks>()
        val validUntil = Date(System.currentTimeMillis() - FOUR_HOUR_AGO)
        for (item in rowList) {
            val lastRefresh = item.lastRefresh
            //validUntil < lastRefresh
            if (validUntil.before(lastRefresh)) {
                filteredList.add(item)
            }
        }

        return filteredList
    }

    fun filterByOutdatedRows(rowList: List<Tracks>): List<Tracks> {
        val outdatedList = ArrayList<Tracks>()
        val validUntil = Date(System.currentTimeMillis() - FOUR_HOUR_AGO)

        for (item in rowList) {
            val lastRefresh = item.lastRefresh
            //validUntil > lastRefresh
            if (validUntil.after(lastRefresh)) {
                outdatedList.add(item)
            }
        }
        return outdatedList
    }

    companion object {
        val FOUR_HOUR_AGO = 4 * 60 * 60 * 1000
    }

}

package com.wrecker.rdious.presantation

import androidx.compose.ui.res.painterResource
import com.wrecker.rdious.R

data class IconsRes(
    val swimming: Int = R.drawable.swimming,
    val rooms: Int = R.drawable.rooms,
    val noRooms: Int = R.drawable.no_room,
    val land: Int = R.drawable.land,
    val garden: Int = R.drawable.garden,
    val garage: Int = R.drawable.garden,
    val boat: Int = R.drawable.boat,
    val condo: Int = R.drawable.condo,
    val apartment: Int = R.drawable.apartment

){
    fun getIcon(name: String): Int {
        return when (name) {
            "swimming" -> swimming
            "rooms" -> rooms
            "noRooms" -> noRooms
            "land" -> land
            "garden" -> garden
            "garage" -> garage
            "boat" -> boat
            "condo" -> condo
            "apartment" -> apartment
            else -> apartment
        }
    }
}

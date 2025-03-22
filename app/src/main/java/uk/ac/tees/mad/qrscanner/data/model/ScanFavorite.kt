package uk.ac.tees.mad.qrscanner.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_data")
data class ScanFavorite(
    @PrimaryKey val id: String = "",
    val userId: String = "",
    val data: String = "",
    val time: Long = System.currentTimeMillis()
)

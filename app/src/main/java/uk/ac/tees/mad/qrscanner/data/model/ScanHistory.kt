package uk.ac.tees.mad.qrscanner.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_data")
data class ScanHistory(
    @PrimaryKey(autoGenerate = true) val id: Int=0,
    val userId: String,
    val data: String,
    val time: Long = System.currentTimeMillis()
)
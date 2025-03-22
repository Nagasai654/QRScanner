package uk.ac.tees.mad.qrscanner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uk.ac.tees.mad.qrscanner.data.model.ScanFavorite
import uk.ac.tees.mad.qrscanner.data.model.ScanHistory

@Database(entities = [ScanHistory::class, ScanFavorite::class], version = 1, exportSchema = false)
abstract class ScannerDatabase: RoomDatabase() {
    abstract fun scannerDao(): ScannerDao

    companion object{

        @Volatile
        private var INSTANCE: ScannerDatabase? = null

        fun getDatabase(context: Context): ScannerDatabase{
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ScannerDatabase::class.java,
                    "scanner_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
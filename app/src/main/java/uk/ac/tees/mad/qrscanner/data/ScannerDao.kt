package uk.ac.tees.mad.qrscanner.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.qrscanner.data.model.ScanFavorite
import uk.ac.tees.mad.qrscanner.data.model.ScanHistory

@Dao
interface ScannerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addHistory(entity:ScanHistory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(entity: ScanFavorite)

    @Query("SELECT * FROM history_data WHERE userId =:userId")
    fun getHistory(userId: String): Flow<List<ScanHistory>>

    @Query("SELECT * FROM favorite_data WHERE userId =:userId")
    fun getFavorite(userId: String): Flow<List<ScanHistory>>

    @Query("DELETE FROM history_data WHERE userId =:userId")
    suspend fun deleteHistory(userId: String)

    @Delete
    suspend fun deleteFavorite(entity: ScanFavorite)
}
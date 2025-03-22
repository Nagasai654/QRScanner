package uk.ac.tees.mad.qrscanner.data.repository

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.qrscanner.data.model.ScanFavorite
import uk.ac.tees.mad.qrscanner.data.model.ScanHistory

interface Repository {
    suspend fun addHistory(entity:ScanHistory)
    suspend fun addFavorite(entity: ScanFavorite)
    fun getHistory(userId: String): Flow<List<ScanHistory>>
    fun getFavorite(userId: String): Flow<List<ScanHistory>>
    suspend fun deleteHistory(userId: String)
    suspend fun deleteFavorite(entity: ScanFavorite)
}
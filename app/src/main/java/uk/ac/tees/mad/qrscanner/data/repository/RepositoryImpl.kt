package uk.ac.tees.mad.qrscanner.data.repository

import kotlinx.coroutines.flow.Flow
import uk.ac.tees.mad.qrscanner.data.ScannerDao
import uk.ac.tees.mad.qrscanner.data.model.ScanFavorite
import uk.ac.tees.mad.qrscanner.data.model.ScanHistory
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dao: ScannerDao
): Repository {
    override suspend fun addHistory(entity: ScanHistory) {
        dao.addHistory(entity)
    }

    override suspend fun addFavorite(entity: ScanFavorite) {
        dao.addFavorite(entity)
    }

    override fun getHistory(userId: String): Flow<List<ScanHistory>> {
        return dao.getHistory(userId)
    }

    override fun getFavorite(userId: String): Flow<List<ScanHistory>> {
        return dao.getFavorite(userId)
    }

    override suspend fun deleteHistory(userId: String) {
        dao.deleteHistory(userId)
    }

    override suspend fun deleteFavorite(entity: ScanFavorite) {
        dao.deleteFavorite(entity)
    }
}
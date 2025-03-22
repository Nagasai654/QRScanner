package uk.ac.tees.mad.qrscanner.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.ac.tees.mad.qrscanner.data.ScannerDao
import uk.ac.tees.mad.qrscanner.data.ScannerDatabase
import uk.ac.tees.mad.qrscanner.data.repository.Repository
import uk.ac.tees.mad.qrscanner.data.repository.RepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFireStore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideScannerDatabase(@ApplicationContext context: Context): ScannerDatabase{
        return ScannerDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideScannerDao(db: ScannerDatabase): ScannerDao{
        return db.scannerDao()
    }

    @Provides
    @Singleton
    fun provideRepository(dao: ScannerDao): Repository{
        return RepositoryImpl(dao)
    }
}
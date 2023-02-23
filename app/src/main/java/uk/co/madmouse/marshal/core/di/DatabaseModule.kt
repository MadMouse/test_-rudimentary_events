package uk.co.madmouse.marshal.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uk.co.madmouse.marshal.core.database.Constants
import uk.co.madmouse.marshal.core.database.EventDao
import uk.co.madmouse.marshal.core.database.MarshalDatabase

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    fun marshalDatabase(@ApplicationContext appContext: Context): MarshalDatabase {
        return Room.databaseBuilder(appContext, MarshalDatabase::class.java, Constants.DATABASE_NAME_EVENTS).build()
    }

    @Provides
    fun provideEventDao(marshalDatabase: MarshalDatabase):EventDao{
        return marshalDatabase.eventDao()
    }
}
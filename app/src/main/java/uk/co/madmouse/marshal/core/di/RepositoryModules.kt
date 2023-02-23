package uk.co.madmouse.marshal.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uk.co.madmouse.marshal.core.repository.EventRepositoryImpl
import uk.co.madmouse.marshal.core.repository.EventsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModules {
    @Binds
    fun provideEventRepository(impl: EventRepositoryImpl ):EventsRepository
}
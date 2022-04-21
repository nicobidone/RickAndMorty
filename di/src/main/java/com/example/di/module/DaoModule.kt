package com.example.di.module

import com.example.data.local.dao.CharacterDao
import com.example.di.LocalClient.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Provides
    @Singleton
    fun provideCharacterDao(appDatabase: AppDatabase): CharacterDao = appDatabase.getCharacterDao()
}

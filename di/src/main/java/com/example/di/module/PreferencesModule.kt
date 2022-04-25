package com.example.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "paging")

    @Provides
    @Singleton
    fun getStorePreferences(@ApplicationContext context: Context) = context.dataStore
}

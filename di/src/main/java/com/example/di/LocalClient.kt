package com.example.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.CharacterDBEntity
import com.example.data.local.CharacterDao
import com.example.data.local.EpisodeDBEntity
import com.example.data.local.LocationDBEntity
import com.example.data.local.OriginDBEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalClient {

    @Database(
        entities = [
            CharacterDBEntity::class,
            LocationDBEntity::class,
            OriginDBEntity::class,
            EpisodeDBEntity::class
        ],
        version = 4,
        exportSchema = false
    )
    abstract class AppDatabase : RoomDatabase() {

        abstract fun getCharacterDao(): CharacterDao
    }

    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "rickandmorty")
            .fallbackToDestructiveMigration()
            .build()
}

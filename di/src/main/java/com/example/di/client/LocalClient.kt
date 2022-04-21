package com.example.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.dao.CharacterDao
import com.example.data.local.entity.CharacterDBEntity
import com.example.data.local.entity.EpisodeDBEntity
import com.example.data.local.entity.LocationDBEntity
import com.example.data.local.entity.OriginDBEntity
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

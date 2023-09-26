package com.github.gasblg.videoapp.data.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.gasblg.videoapp.data.database.dao.RemoteKeysDao
import com.github.gasblg.videoapp.data.database.dao.VideoDao
import com.github.gasblg.videoapp.data.database.entities.RemoteKeyEntity
import com.github.gasblg.videoapp.data.database.entities.VideoEntity


@Database(
    entities = [VideoEntity::class, RemoteKeyEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract val videosDao: VideoDao
    abstract val remoteKeysDao: RemoteKeysDao

    companion object {
        private const val DATABASE_NAME = "videos_app_db"

        @Volatile
        private var instance: AppDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: buildDatabase(
                        context
                    ).also {
                    instance = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDataBase::class.java,
                DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .build()
    }

}

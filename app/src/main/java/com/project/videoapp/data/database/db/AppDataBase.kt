package com.project.videoapp.data.database.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.project.videoapp.data.database.dao.RemoteKeysDao
import com.project.videoapp.data.database.dao.VideoDao
import com.project.videoapp.data.database.entities.RemoteKey
import com.project.videoapp.data.database.entities.Video


@Database(
    entities = [Video::class, RemoteKey::class],
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
                instance ?: buildDatabase(
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

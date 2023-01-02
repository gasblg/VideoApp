package com.project.videoapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.videoapp.data.database.entities.RemoteKey

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("SELECT * FROM keys WHERE label = :label")
    suspend fun remoteKeyByQuery(label: String): RemoteKey

    @Query("DELETE FROM keys WHERE label = :label")
    suspend fun deleteByQuery(label: String)
}
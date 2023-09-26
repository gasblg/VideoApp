package com.github.gasblg.videoapp.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.github.gasblg.videoapp.data.database.dao.RemoteKeysDao
import com.github.gasblg.videoapp.data.database.db.AppDataBase
import com.github.gasblg.videoapp.data.database.entities.RemoteKeyEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RemoteKeysDaoTestEntity {

    private lateinit var database: AppDataBase
    private lateinit var dao: RemoteKeysDao

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java
        ).allowMainThreadQueries().build()
        dao = database.remoteKeysDao
    }

    @After
    fun tearDown() {
        database.close()
    }

    private val key = RemoteKeyEntity(
        label = "videos_list",
        prevKey = "CDIQAQ",
        nextKey = "CGQQAA"
    )

    @Test
    fun insertRemoteKey() = runBlocking {
        dao.insertRemoteKey(key)
        val expected = key
        val actual = dao.getRemoteKeyByLabel(label = "videos_list")

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun getRemoteKeyByLabel() = runBlocking {
        dao.insertRemoteKey(key)
        val expected = key
        val actual = dao.getRemoteKeyByLabel(label = "videos_list")

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun deleteRemoteKeyByLabel() = runBlocking {
        dao.insertRemoteKey(key)
        dao.deleteRemoteKeyByLabel(label = "videos_list")
        val actual = dao.getRemoteKeyByLabel(label = "videos_list")

        Assert.assertEquals(null, actual)
    }
}
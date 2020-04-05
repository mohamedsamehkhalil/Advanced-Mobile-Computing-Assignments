package com.example.weatherapplication.model.db

import android.content.*
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.weatherapplication.model.dao.WeatherDao
import com.example.weatherapplication.model.entities.Weather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

interface EmptyCallBackInterface {
    suspend fun onEmptyDB()
}

@Database(entities = [Weather::class], version = 1, exportSchema = false)
abstract class WeatherRoomDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        private var INSTANCE: WeatherRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope,
            callback: EmptyCallBackInterface
        ): WeatherRoomDatabase {
            INSTANCE?.let { return it }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherRoomDatabase::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback(scope, callback))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

    private class WordDatabaseCallback(
        private val scope: CoroutineScope,
        private val callback: EmptyCallBackInterface
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val weatherDao = database.weatherDao()
                    if (weatherDao.getSize() == 0) {
                        callback.onEmptyDB()
                    }
                }
            }
        }
    }
}
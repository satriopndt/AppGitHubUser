package com.example.appgithubuser.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class GhRoomDatabase : RoomDatabase() {
    companion object{
        @Volatile
        private var INSTANCE: GhRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): GhRoomDatabase{
            if (INSTANCE == null) {
                synchronized(GhRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GhRoomDatabase::class.java, "github_database")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as GhRoomDatabase
        }

    }
    abstract fun daoGithub(): DaoGitHub
}

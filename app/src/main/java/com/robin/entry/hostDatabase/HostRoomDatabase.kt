package com.robin.entry.hostDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Host::class], version = 1)
abstract class HostDaoRoomDatabase : RoomDatabase() {

    abstract fun hostDao(): HostDao

    companion object {
        @Volatile
        private var INSTANCE: HostDaoRoomDatabase? = null

        fun getDatabase(context: Context): HostDaoRoomDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HostDaoRoomDatabase::class.java,
                    "Host_Database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
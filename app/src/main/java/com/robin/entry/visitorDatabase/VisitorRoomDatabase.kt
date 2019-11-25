package com.robin.entry.visitorDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Visitor::class], version = 1)
abstract class VisitorRoomDatabase : RoomDatabase() {

    abstract fun visitorDAO(): VisitorDAO

    companion object {
        @Volatile
        private var INSTANCE: VisitorRoomDatabase? = null

        fun getDatabase(context: Context): VisitorRoomDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VisitorRoomDatabase::class.java,
                    "Visitor_Database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
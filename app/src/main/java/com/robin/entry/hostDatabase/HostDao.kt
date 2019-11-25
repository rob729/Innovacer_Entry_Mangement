package com.robin.entry.hostDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.robin.entry.visitorDatabase.Visitor

@Dao
interface HostDao {

    @Query("SELECT * FROM host_table WHERE visitorEmail = :mail ")
    fun getHost(vararg mail: String): Host
    @Insert
    fun insertHost(vararg host: Host)

}
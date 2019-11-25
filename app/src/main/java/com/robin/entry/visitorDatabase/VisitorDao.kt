package com.robin.entry.visitorDatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VisitorDAO {
    @Query("SELECT * FROM visitor_table WHERE checkOutStatus = 0")
    fun getAllVisitor(): LiveData<List<Visitor>>

    @Insert
    fun insertVisitor(vararg visitor: Visitor)

    @Delete
    fun deleteVisitor(vararg visitor: Visitor)

    @Query("UPDATE visitor_table SET checkOutStatus = 1 WHERE visitorId = :id")
    fun updateStatus(id: Long)


}
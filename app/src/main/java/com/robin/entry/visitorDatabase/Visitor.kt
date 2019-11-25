package com.robin.entry.visitorDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "visitor_table")
data class Visitor(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val inTime: String,
    val hostName: String,
    val checkOutStatus: Boolean,
    @PrimaryKey(autoGenerate = true) var visitorId: Long = 0L
)
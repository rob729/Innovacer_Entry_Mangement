package com.robin.entry.hostDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "host_table")
data class Host(val name: String, @PrimaryKey val email: String, val phoneNumber: String, val visitorEmail: String)
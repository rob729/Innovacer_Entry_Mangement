package com.robin.entry.hostDatabase

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class HostRepository(private val hostDAO: HostDao) {


    @WorkerThread
    fun insert(host: Host) {
        hostDAO.insertHost(host)
    }

    @WorkerThread
    fun getHost(email: String) {
        hostDAO.getHost(email)
    }


}
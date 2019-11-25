package com.robin.entry.visitorDatabase

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class VisitorRepository(private val visitorDAO: VisitorDAO) {

    val allVisitors: LiveData<List<Visitor>> = visitorDAO.getAllVisitor()

    @WorkerThread
    fun insert(visitor: Visitor) {
        visitorDAO.insertVisitor(visitor)
    }

    @WorkerThread
    fun delete(visitor: Visitor) {
        visitorDAO.deleteVisitor(visitor)
    }

    @WorkerThread
    fun updateStatus(id: Long) {
        visitorDAO.updateStatus(id)
    }
}
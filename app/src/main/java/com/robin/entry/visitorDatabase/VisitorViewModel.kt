package com.robin.entry.visitorDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class VisitorViewModel(application: Application) : AndroidViewModel(Application()) {

    private var parentJob = Job()
    private val scope = CoroutineScope(parentJob + Dispatchers.Main)

    private val repository: VisitorRepository
    val allVisitors: LiveData<List<Visitor>>

    init {
        val visitorDAO = VisitorRoomDatabase.getDatabase(application).visitorDAO()
        repository = VisitorRepository(visitorDAO)
        allVisitors = repository.allVisitors
    }

    fun insert(visitor: Visitor) = scope.launch(Dispatchers.IO) {
        repository.insert(visitor)
    }

    fun delete(visitor: Visitor) = scope.launch(Dispatchers.IO) {
        repository.delete(visitor)
    }

    fun updateStatus(id: Long) = scope.launch(Dispatchers.IO) {
        repository.updateStatus(id)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}
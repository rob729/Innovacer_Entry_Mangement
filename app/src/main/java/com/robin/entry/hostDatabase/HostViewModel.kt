package com.robin.entry.hostDatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class HostViewModel(application: Application) : AndroidViewModel(Application()) {

    private var parentJob = Job()
    private val scope = CoroutineScope(parentJob + Dispatchers.Main)

    private val repository: HostRepository

    init {
        val hostDao = HostDaoRoomDatabase.getDatabase(application).hostDao()
        repository = HostRepository(hostDao)
    }

    fun insert(host: Host) = scope.launch(Dispatchers.IO) {
        repository.insert(host)
    }

    fun getHost(email: String) = scope.launch(Dispatchers.IO) {
        repository.getHost(email)
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}
package com.pushpender.moengageassignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pushpender.moengageassignment.ui.homepage.repository.NewsRepository
import com.pushpender.moengageassignment.ui.homepage.viewmodels.NewsViewModel

class ViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)){
            return NewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}
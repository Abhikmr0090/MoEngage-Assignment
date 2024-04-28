package com.example.moengageassignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.moengageassignment.ui.homepage.repository.NewsRepository
import com.example.moengageassignment.ui.homepage.viewmodels.NewsViewModel

class ViewModelFactory(private val repository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)){
            return NewsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewmodel class")
    }
}
package com.pushpender.moengageassignment.ui.homepage.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pushpender.moengageassignment.network.ApiResult
import com.pushpender.moengageassignment.ui.homepage.models.ArticlesModel
import com.pushpender.moengageassignment.ui.homepage.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {

   private val newsMutableLiveData = MutableLiveData<ApiResult<ArticlesModel>>()
   val newsLiveData : LiveData<ApiResult<ArticlesModel>> = newsMutableLiveData

   fun getNews() {
       viewModelScope.launch {
           val data = newsRepository.getNewsData()
           newsMutableLiveData.postValue(data)
       }
   }
}
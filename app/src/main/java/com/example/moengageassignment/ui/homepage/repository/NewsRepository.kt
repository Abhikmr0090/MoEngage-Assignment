package com.example.moengageassignment.ui.homepage.repository

import com.example.moengageassignment.RequestMethod
import com.example.moengageassignment.network.ApiCall
import com.example.moengageassignment.network.ApiResult
import com.example.moengageassignment.ui.homepage.models.ArticlesModel


class NewsRepository {

    suspend fun getNewsData(): ApiResult<ArticlesModel> {
        return ApiCall.fetchNews<ArticlesModel>(
            RequestMethod.GET.name,
            "Android/news-api-feed/staticResponse.json"
        )
    }
}
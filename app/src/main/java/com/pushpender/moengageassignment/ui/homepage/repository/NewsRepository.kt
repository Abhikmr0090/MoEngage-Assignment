package com.pushpender.moengageassignment.ui.homepage.repository

import com.pushpender.moengageassignment.RequestMethod
import com.pushpender.moengageassignment.network.ApiCall
import com.pushpender.moengageassignment.network.ApiResult
import com.pushpender.moengageassignment.ui.homepage.models.ArticlesModel


class NewsRepository {

    suspend fun getNewsData(): ApiResult<ArticlesModel> {
        return ApiCall.fetchNews<ArticlesModel>(
            RequestMethod.GET.name,
            "Android/news-api-feed/staticResponse.json"
        )
    }
}
package com.dmuhia.bgtaskdemoapp.data.network

import com.dmuhia.bgtaskdemoapp.data.model.Post
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
    @GET("posts/1")
    suspend fun getPost(
    ): Response<Post>


}

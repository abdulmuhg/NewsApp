package secretymus.id.newsapp.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import secretymus.id.newsapp.models.News

interface NewsApi {
    @GET("top-headlines")
    fun getNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String,
        @Query("page") page: Int,
        @Query("pagesize") pagesize: Int = 20
    ): Single<News>
}
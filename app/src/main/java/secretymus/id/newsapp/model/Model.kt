package secretymus.id.newsapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Article(
        @SerializedName("source")
        val source: Source,

        @SerializedName("author")
        val author: String,

        @SerializedName("title")
        val title: String,

        @SerializedName("description")
        val description: String?,

        @SerializedName("url")
        val url: String,

        @SerializedName("urlToImage")
        val urlToImage: String?,

        @SerializedName("publishedAt")
        val publishedAt: String?,

        @SerializedName("content")
        val content: String
)

data class News(
        @SerializedName("status")
        val status: String,

        @SerializedName("totalResults")
        val totalResults: Int,

        @SerializedName("articles")
        @Expose
        val articles: List<Article>
)

data class Source(
        @SerializedName("id")
        val id: String?,
        var name: String
)
package secretymus.id.newsapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Article(
        @SerializedName("source")
        @Embedded(prefix = "_source")
        val source: Source?,

        @ColumnInfo(name = "author")
        @SerializedName("author")
        val author: String,

        @ColumnInfo(name = "title")
        @SerializedName("title")
        val title: String,

        @ColumnInfo(name = "description")
        @SerializedName("description")
        val description: String?,

        @ColumnInfo(name = "url")
        @SerializedName("url")
        val url: String,

        @ColumnInfo(name = "url_to_image")
        @SerializedName("urlToImage")
        val urlToImage: String?,

        @ColumnInfo(name = "publish_time")
        @SerializedName("publishedAt")
        val publishedAt: String?,

        @ColumnInfo(name = "content")
        @SerializedName("content")
        val content: String
) : Parcelable {
        @IgnoredOnParcel
        @PrimaryKey(autoGenerate = true)
        var uuid: Int = 0
}

data class News(
        @SerializedName("status")
        val status: String,

        @SerializedName("totalResults")
        val totalResults: Int,

        @SerializedName("articles")
        @Expose
        val articles: List<Article>
)

@Parcelize
@Entity(tableName = "sources")
data class Source(
        @PrimaryKey
        @ColumnInfo
        @SerializedName("id")
        val id: String,
        @ColumnInfo
        @SerializedName("name")
        var name: String
) : Parcelable
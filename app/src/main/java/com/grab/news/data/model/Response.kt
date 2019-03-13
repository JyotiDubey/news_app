package com.grab.news.data.model

import android.os.Parcelable
import androidx.recyclerview.widget.DiffUtil
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by jyotidubey on 2019-03-09.
 */
data class NewsListResponse(
    @SerializedName("status") var status: String,
    @SerializedName("totalResults") var totalResults: String,
    @SerializedName("articles") var news: List<News>? = null
)

@Parcelize
@Entity(tableName = "news")
data class News(

    @PrimaryKey(autoGenerate = true)
    val id:Int,

    @Expose
    @ColumnInfo(name = "author")
    @SerializedName("author")
    val author: String?,

    @Expose
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String?,

    @Expose
    @ColumnInfo(name = "url")
    @SerializedName("url")
    val url: String?,

    @Expose
    @ColumnInfo(name = "urlToImage")
    @SerializedName("urlToImage")
    val urlToImage: String?,

    @Expose
    @ColumnInfo(name = "publishedAt")
    @SerializedName("publishedAt")
    val publishedAt: String?,

    @Expose
    @ColumnInfo(name = "content")
    @SerializedName("content")
    val content: String?
):Parcelable{
    @Ignore
    @SerializedName("source")
    var source: Source? = null
}
@Parcelize
data class Source(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String?
):Parcelable

class NewsDiffCallback(private var oldNews:List<News>, var updatesNews: List<News>) : DiffUtil.Callback(){

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) : Boolean {
        return oldNews[oldItemPosition].url == updatesNews[newItemPosition].url
    }

    override fun getOldListSize() = oldNews.size

    override fun getNewListSize() = updatesNews.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldNews[oldItemPosition] == updatesNews[newItemPosition]

}

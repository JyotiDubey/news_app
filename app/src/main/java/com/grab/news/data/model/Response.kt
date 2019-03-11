package com.grab.news.data.model

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

/**
 * Created by jyotidubey on 2019-03-09.
 */
data class NewsListResponse(
    @SerializedName("status") var status: String,
    @SerializedName("totalResults") var totalResults: String,
    @SerializedName("articles") var news: List<News>? = null
)


data class News(
    @SerializedName("source") val source: Source,
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val urlToImage: String,
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("content") val content: String
)

data class Source(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)

class NewsDiffCallback(var updatesNews:List<News>, var oldNews: List<News>) : DiffUtil.Callback(){

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldNews[oldItemPosition].url == updatesNews[newItemPosition].url

    override fun getOldListSize() = oldNews.size

    override fun getNewListSize() = updatesNews.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldNews[oldItemPosition] == updatesNews[newItemPosition]

}

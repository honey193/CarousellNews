package com.example.carousellnews.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carousellnews.databinding.ArticleLayoutBinding
import com.example.carousellnews.models.Article
import java.util.*

class ArticleListAdapter : RecyclerView.Adapter<ArticleViewHolder>() {
    private var articles: List<Article> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArticleViewHolder(
            ArticleLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        articles.getOrNull(position)?.let { holder.setArticleData(it) }
    }

    override fun getItemCount() = articles.size

    fun modifyArticleList(articleList: List<Article>) {
        articles = articleList
        notifyDataSetChanged()
    }
}

class ArticleViewHolder(private val binding: ArticleLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun setArticleData(article: Article) {
        binding.apply {
            with(article) {
                tvArticleTitle.text = title
                tvArticleDescription.text = description
                tvArticleCreationDate.text = getCreationTimeString(creationTime = creationTime)
                Glide.with(root.context)
                    .load(bannerUrl)
                    .centerCrop()
                    .into(ivArticleImage)
            }
        }
    }

    private fun getCreationTimeString(creationTime: Long): String {
        val daysInMilli = 1000 * 60 * 60 * 24
        val noOfDaysFromCreation = Calendar.getInstance().timeInMillis - creationTime

        with(noOfDaysFromCreation / daysInMilli) {
            if (this <= 5) {
                return FIVE_DAYS_AGO
            } else if (this <= 7) {
                return ONE_WEEK_AGO
            } else if (this <= 28) {
                return ONE_MONTH_AGO
            } else if (this <= 365) {
                return ONE_YEAR_AGO
            } else {
                return ONE_YEAR_AGO
            }
        }
    }

    companion object {
        const val FIVE_DAYS_AGO = "5 days ago"
        const val ONE_WEEK_AGO = "1 week ago"
        const val ONE_MONTH_AGO = "1 month ago"
        const val ONE_YEAR_AGO = "1 year ago"
    }
}

package com.example.carousellnews.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carousellnews.R
import com.example.carousellnews.databinding.ActivityCarousellNewsBinding
import com.example.carousellnews.extensions.getJsonDataFromAsset
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CarousellNewsActivity : AppCompatActivity() {
    private val binding: ActivityCarousellNewsBinding by lazy {
        ActivityCarousellNewsBinding.inflate(layoutInflater)
    }

    private val viewModel by viewModels<CarousellNewsViewModel>()
    private val articleListAdapter by lazy { ArticleListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvArticles.apply {
            adapter = articleListAdapter
            layoutManager = LinearLayoutManager(context)
            smoothScrollBy(0, 0)
        }

        with(viewModel) {
            articlesResponse.observe(this@CarousellNewsActivity) { articles ->
                if (!articles.isNullOrEmpty()) {
                    articleListAdapter.modifyArticleList(articleList = articles)
                }
            }
            this@CarousellNewsActivity.getJsonDataFromAsset("articles.json")?.let { loadArticles(jsonString = it) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.news_sorting_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.recent -> {
                viewModel.sortArticles(SortingTechnique.RECENT)
                true
            }
            R.id.popular -> {
                viewModel.sortArticles(SortingTechnique.POPULAR)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

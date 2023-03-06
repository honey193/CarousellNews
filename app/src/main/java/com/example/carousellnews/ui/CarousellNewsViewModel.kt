package com.example.carousellnews.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carousellnews.models.Article
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class CarousellNewsViewModel : ViewModel() {
    private val _articlesResponse: MutableLiveData<List<Article>> = MutableLiveData()
    val articlesResponse: LiveData<List<Article>> = _articlesResponse
    val sortByRecentFirst =
        Comparator { art1: Article, art2: Article -> (art2.creationTime - art1.creationTime).toInt() }

    fun loadArticles(jsonString: String) {
        val list = Gson().fromJson(jsonString, Array<Article>::class.java)
        _articlesResponse.postValue(list.sortedWith(sortByRecentFirst))
    }

    fun sortArticles(sortingTechnique: SortingTechnique) {
        when (sortingTechnique) {
            SortingTechnique.RECENT -> {
                _articlesResponse.postValue(articlesResponse.value?.sortedWith(sortByRecentFirst))
            }
            SortingTechnique.POPULAR -> {
                val sortByPopularity =
                    Comparator { art1: Article, art2: Article -> art2.rank - art1.rank }
                _articlesResponse.postValue(articlesResponse.value?.sortedWith(sortByPopularity))
            }
        }
    }
}

enum class SortingTechnique {
    RECENT,
    POPULAR
}

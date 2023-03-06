package com.example.carousellnews.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carousellnews.models.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class CarousellNewsViewModel : ViewModel() {
    private val _articlesResponse: MutableLiveData<List<Article>> = MutableLiveData()
    val articlesResponse: LiveData<List<Article>> = _articlesResponse
    private val sortByRecentFirst =
        Comparator { art1: Article, art2: Article -> (art2.creationTime - art1.creationTime).toInt() }

    fun loadArticles(jsonString: String) {
        val listPersonType = object : TypeToken<List<Article>>() {}.type
        val list: List<Article> = Gson().fromJson(jsonString, listPersonType)
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

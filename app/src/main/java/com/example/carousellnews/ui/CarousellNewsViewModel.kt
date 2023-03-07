package com.example.carousellnews.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.carousellnews.models.Article
import com.example.carousellnews.repository.JSONParser
import com.google.gson.Gson
import com.google.gson.JsonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CarousellNewsViewModel @Inject constructor(private val jsonParser: JSONParser) : ViewModel() {
    private val _articlesResponse: MutableLiveData<List<Article>> = MutableLiveData()
    val articlesResponse: LiveData<List<Article>> = _articlesResponse
    val sortByRecentFirst =
        Comparator { art1: Article, art2: Article -> (art2.creationTime - art1.creationTime).toInt() }

    fun loadArticles(jsonString: String) {
        val list = jsonParser.parseJsonString(jsonString = jsonString)
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

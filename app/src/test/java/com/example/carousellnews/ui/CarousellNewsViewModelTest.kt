package com.example.carousellnews.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.carousellnews.JSONObjectProviderUtil
import com.example.carousellnews.LiveDataTestUtil
import com.example.carousellnews.models.Article
import com.example.carousellnews.repository.JSONParser
import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class CarousellNewsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var carousellNewsViewModel: CarousellNewsViewModel

    private var articlesJsonString: String? = null
    private var mockedArticleList = arrayOf<Article>()

    private var jsonParser = mockk<JSONParser>()

    @Before
    fun setUp() {
        val fileName = "resources/articles.json"
        articlesJsonString = JSONObjectProviderUtil.getJsonString(fileName)
        mockedArticleList = Gson().fromJson(articlesJsonString, Array<Article>::class.java)

        carousellNewsViewModel = CarousellNewsViewModel(jsonParser = jsonParser)
        every { jsonParser.parseJsonString(articlesJsonString) } returns mockedArticleList
    }

    @Test
    fun `test loadArticles`() {
        val response = mockedArticleList.sortedWith(carousellNewsViewModel.sortByRecentFirst)
        articlesJsonString?.let { carousellNewsViewModel.loadArticles(it) }

        assertEquals(
            response.size,
            LiveDataTestUtil.getValue(carousellNewsViewModel.articlesResponse)?.size
        )
    }

    @Test
    fun `test sortArticles by creation date`() {
        val response = mockedArticleList.sortedWith(carousellNewsViewModel.sortByRecentFirst)
        articlesJsonString?.let { carousellNewsViewModel.loadArticles(it) }
        carousellNewsViewModel.sortArticles(sortingTechnique = SortingTechnique.RECENT)

        assertEquals(
            response.size,
            LiveDataTestUtil.getValue(carousellNewsViewModel.articlesResponse)?.size
        )
        assertEquals(
            response.getOrNull(0)?.id,
            LiveDataTestUtil.getValue(carousellNewsViewModel.articlesResponse)?.getOrNull(0)?.id
        )
    }

    @Test
    fun `test sortArticles by popularity`() {
        val sortByPopularity =
            Comparator { art1: Article, art2: Article -> art2.rank - art1.rank }

        val response = mockedArticleList.sortedWith(sortByPopularity)
        articlesJsonString?.let { carousellNewsViewModel.loadArticles(it) }
        carousellNewsViewModel.sortArticles(sortingTechnique = SortingTechnique.POPULAR)

        assertEquals(
            response.size,
            LiveDataTestUtil.getValue(carousellNewsViewModel.articlesResponse)?.size
        )
        assertEquals(
            response.getOrNull(0)?.id,
            LiveDataTestUtil.getValue(carousellNewsViewModel.articlesResponse)?.getOrNull(0)?.id
        )
    }
}

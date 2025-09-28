package com.prado.eduardo.luiz.newsapp.ui.theme.news

import com.prado.eduardo.luiz.domain.model.ArticleModel
import com.prado.eduardo.luiz.domain.usecase.GetArticlesUseCase
import com.prado.eduardo.luiz.domain.util.ErrorMapper
import com.prado.eduardo.luiz.newsapp.common.dispatcher.DispatchersProvider
import com.prado.eduardo.luiz.newsapp.navigation.Navigator
import com.prado.eduardo.luiz.newsapp.ui.news.NewsIntent
import com.prado.eduardo.luiz.newsapp.ui.news.NewsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    private lateinit var viewModel: NewsViewModel
    private lateinit var getArticlesUseCase: GetArticlesUseCase
    private lateinit var dispatchersProvider: DispatchersProvider

    private lateinit var navigator: Navigator

    private lateinit var errorMapper: ErrorMapper
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getArticlesUseCase = mockk()
        dispatchersProvider = mockk {
            coEvery { io } returns testDispatcher
        }
        viewModel = NewsViewModel(
            getArticlesUseCase = getArticlesUseCase,
            dispatcher = dispatchersProvider,
            navigator = navigator,
            errorMapper = errorMapper
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when publish is called and use case returns success, then update UI state with articles`() =
        runTest {
            // Given
            val articles = listOf(createSampleArticle())
            coEvery { getArticlesUseCase() } returns Result.success(articles)

            // When
            viewModel.publish(NewsIntent.GetNews)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            with(viewModel.uiState.value) {
                assertFalse(isLoading)
                assertEquals(articles, this.articles)
            }
        }

    @Test
    fun `when publish is called and use case returns error, then update UI state with error`() =
        runTest {
            // Given
            val errorMessage = "Network error"
            coEvery { getArticlesUseCase() } returns Result.failure(Exception(errorMessage))

            // When
            viewModel.publish(NewsIntent.GetNews)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            with(viewModel.uiState.value) {
                assertFalse(isLoading)
                assertTrue(articles.isEmpty())
            }
        }

    private fun createSampleArticle() = ArticleModel(
        title = "Test Title",
        author = "Test Author",
        description = "Test Description",
        url = "http://test.com",
        urlToImage = "http://test.com/image.jpg",
        publishedAt = "2024-01-01",
        content = "Test Content"
    )
}

package com.prado.eduardo.luiz.newsapp.ui.news

import com.prado.eduardo.luiz.domain.model.ArticleModel
import com.prado.eduardo.luiz.domain.usecase.GetArticlesUseCase
import com.prado.eduardo.luiz.domain.util.ErrorMapper
import com.prado.eduardo.luiz.newsapp.common.dispatcher.DispatchersProvider
import com.prado.eduardo.luiz.newsapp.navigation.AppRoute
import com.prado.eduardo.luiz.newsapp.navigation.NavigatorRoute
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
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
    private lateinit var navigator: NavigatorRoute
    private lateinit var errorMapper: ErrorMapper
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getArticlesUseCase = mockk()
        navigator = mockk(relaxed = true)
        errorMapper = mockk()
        dispatchersProvider = mockk {
            every { io } returns testDispatcher
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
    fun `initial state should have loading true and empty articles`() = runTest {
        // When
        val initialState = viewModel.uiState.value

        // Then
        assertTrue(initialState.isLoading)
        assertTrue(initialState.articles.isEmpty())
    }

    @Test
    fun `when publish GetNews is called and use case returns success, then update UI state with articles`() =
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
    fun `when publish GetNews is called and use case returns error, then update UI state and emit error event`() =
        runTest {
            // Given
            val exception = Exception("Network error")
            val errorMessage = "Please check your internet connection."
            coEvery { getArticlesUseCase() } returns Result.failure(exception)
            every { errorMapper.map(exception) } returns errorMessage

            // Collect UI events before the operation
            val uiEvents = mutableListOf<NewsUIEvent>()
            val job = launch {
                viewModel.uiEvent.collect { event ->
                    uiEvents.add(event)
                }
            }

            // When
            viewModel.publish(NewsIntent.GetNews)
            testDispatcher.scheduler.advanceUntilIdle()

            // Then
            with(viewModel.uiState.value) {
                assertFalse(isLoading)
                assertTrue(articles.isEmpty())
            }

            // Verify error event is emitted
            assertTrue(uiEvents.isNotEmpty())
            val errorEvent = uiEvents.first()
            assertTrue(errorEvent is NewsUIEvent.ShowError)
            assertEquals(errorMessage, (errorEvent as NewsUIEvent.ShowError).message)
            verify { errorMapper.map(exception) }

            job.cancel()
        }

    @Test
    fun `when publish OpenArticle is called, then navigate to article route`() = runTest {
        // Given
        val article = createSampleArticle()
        val expectedRoute = AppRoute.Article(
            author = article.author,
            title = article.title,
            url = article.url,
            urlToImage = article.urlToImage,
            publishedAt = article.publishedAt,
            content = article.content,
            description = article.description
        )

        // When
        viewModel.publish(NewsIntent.OpenArticle(article))
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        verify { navigator.navigate(expectedRoute) }
    }

    @Test
    fun `when GetNews is loading, then isLoading should be true`() = runTest {
        // Given
        coEvery { getArticlesUseCase() } returns Result.success(emptyList())

        // When
        viewModel.publish(NewsIntent.GetNews)

        // Then - before advancing the dispatcher, loading should be true
        assertTrue(viewModel.uiState.value.isLoading)

        // Complete the operation
        testDispatcher.scheduler.advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `when multiple GetNews calls are made, then only latest result should be used`() = runTest {
        // Given
        val articles1 = listOf(createSampleArticle("Article 1"))
        val articles2 = listOf(createSampleArticle("Article 2"))

        coEvery { getArticlesUseCase() } returnsMany listOf(
            Result.success(articles1),
            Result.success(articles2)
        )

        // When
        viewModel.publish(NewsIntent.GetNews)
        viewModel.publish(NewsIntent.GetNews)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        with(viewModel.uiState.value) {
            assertFalse(isLoading)
            // Should have the second result
            assertEquals(articles2, this.articles)
        }
    }

    private fun createSampleArticle(title: String = "Test Title") = ArticleModel(
        title = title,
        author = "Test Author",
        description = "Test Description",
        url = "http://test.com",
        urlToImage = "http://test.com/image.jpg",
        publishedAt = "2024-01-01",
        content = "Test Content"
    )
}

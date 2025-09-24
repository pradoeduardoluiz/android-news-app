package com.prado.eduardo.luiz.newsapp.data.repository

import com.prado.eduardo.luiz.newsapp.data.remote.dto.ArticleDTO
import com.prado.eduardo.luiz.newsapp.data.remote.dto.NewsResponseDTO
import com.prado.eduardo.luiz.newsapp.data.remote.service.NewsService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class NewsRepositoryImplTest {

    private lateinit var newsService: NewsService
    private lateinit var repository: NewsRepositoryImpl
    private val source = "test-source"
    private val apiKey = "test-api-key"

    @Before
    fun setup() {
        newsService = mockk()
        repository = NewsRepositoryImpl(
            newsService = newsService,
            source = source,
            apiKey = apiKey
        )
    }

    @Test
    fun `when getArticle is called and service returns success and mapped articles`() = runTest {
        // Given
        val articleDTO = ArticleDTO(
            title = "Test Title",
            author = "Test Author",
            description = "Test Description",
            url = "http://test.com",
            urlToImage = "http://test.com/image.jpg",
            publishedAt = "2024-01-01",
            content = "Test Content",
            source = null
        )
        val newsResponse = NewsResponseDTO(
            status = "ok",
            totalResults = 1,
            articles = listOf(articleDTO)
        )
        coEvery { newsService.getArticles(source, apiKey) } returns Response.success(
            newsResponse
        )

        // When
        val result = repository.getArticle()

        // Then
        assertTrue(result.isSuccess)
        result.onSuccess { articles ->
            assertEquals(1, articles.size)
            assertEquals("Test Title", articles[0].title)
            assertEquals("Test Author", articles[0].author)
            assertEquals("Test Description", articles[0].description)
            assertEquals("http://test.com", articles[0].url)
            assertEquals("http://test.com/image.jpg", articles[0].urlToImage)
            assertEquals("2024-01-01", articles[0].publishedAt)
            assertEquals("Test Content", articles[0].content)
        }
    }

    @Test
    fun `when getArticle is called and service returns error, then return failure`() = runTest {
        // Given
        coEvery { newsService.getArticles(source, apiKey) } returns Response.error(
            404,
            mockk(relaxed = true)
        )

        // When
        val result = repository.getArticle()

        // Then
        assertTrue(result.isFailure)
        result.onFailure { exception ->
            assertEquals("Error fetching articles: 404", exception.message)
        }
    }

    @Test
    fun `when getArticle is called and service throws exception, then return failure`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { newsService.getArticles(source, apiKey) } throws exception

        // When
        val result = repository.getArticle()

        // Then
        assertTrue(result.isFailure)
        result.onFailure {
            assertEquals(exception, it)
        }
    }
}

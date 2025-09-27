package com.prado.eduardo.luiz.domain.usecase

import com.prado.eduardo.luiz.domain.model.ArticleModel
import com.prado.eduardo.luiz.domain.repository.NewsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetArticlesUseCaseTest {

    private lateinit var repository: NewsRepository
    private lateinit var useCase: GetArticlesUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetArticlesUseCase(repository)
    }

    @Test
    fun `when use case is called and returns success`() =
        runTest {
            // Given
            val articles = listOf(
                ArticleModel(
                    title = "Test Title",
                    author = "Test Author",
                    description = "Test Description",
                    url = "http://test.com",
                    urlToImage = "http://test.com/image.jpg",
                    publishedAt = "2024-01-01",
                    content = "Test Content"
                )
            )
            coEvery { repository.getArticle() } returns Result.success(articles)

            // When
            val result = useCase()

            // Then
            assertTrue(result.isSuccess)
            result.onSuccess { resultArticles ->
                assertEquals(articles, resultArticles)
                assertEquals(1, resultArticles.size)
                assertEquals("Test Title", resultArticles[0].title)
                assertEquals("Test Author", resultArticles[0].author)
                assertEquals("Test Description", resultArticles[0].description)
                assertEquals("http://test.com", resultArticles[0].url)
                assertEquals("http://test.com/image.jpg", resultArticles[0].urlToImage)
                assertEquals("2024-01-01", resultArticles[0].publishedAt)
                assertEquals("Test Content", resultArticles[0].content)
            }
        }

    @Test
    fun `when use case is called returns empty list, then return success with empty list`() =
        runTest {
            // Given
            coEvery { repository.getArticle() } returns Result.success(emptyList())

            // When
            val result = useCase()

            // Then
            assertTrue(result.isSuccess)
            result.onSuccess { resultArticles ->
                assertTrue(resultArticles.isEmpty())
            }
        }

    @Test
    fun `when use case is called returns failure, then return failure result`() =
        runTest {
            // Given
            val exception = Exception("Test error")
            coEvery { repository.getArticle() } returns Result.failure(exception)

            // When
            val result = useCase()

            // Then
            assertTrue(result.isFailure)
            result.onFailure {
                assertEquals(exception, it)
                assertEquals("Test error", it.message)
            }
        }

    @Test
    fun `when use case is called it should return articles sorted by publishedAt in descending order`() =
        runTest {
            // Given
            val articles = listOf(
                ArticleModel(
                    title = "Old Article",
                    author = "Test Author",
                    description = "Test Description",
                    url = "http://test.com",
                    urlToImage = "http://test.com/image.jpg",
                    publishedAt = "2024-01-01T10:00:00Z",
                    content = "Test Content"
                ),
                ArticleModel(
                    title = "Most Recent Article",
                    author = "Test Author",
                    description = "Test Description",
                    url = "http://test.com",
                    urlToImage = "http://test.com/image.jpg",
                    publishedAt = "2024-01-03T10:00:00Z",
                    content = "Test Content"
                ),
                ArticleModel(
                    title = "Medium Article",
                    author = "Test Author",
                    description = "Test Description",
                    url = "http://test.com",
                    urlToImage = "http://test.com/image.jpg",
                    publishedAt = "2024-01-02T10:00:00Z",
                    content = "Test Content"
                )
            )
            coEvery { repository.getArticle() } returns Result.success(articles)

            // When
            val result = useCase()

            // Then
            assertTrue(result.isSuccess)
            result.onSuccess { resultArticles ->
                assertEquals(3, resultArticles.size)
                assertEquals("Most Recent Article", resultArticles[0].title)
                assertEquals("2024-01-03T10:00:00Z", resultArticles[0].publishedAt)
                assertEquals("Medium Article", resultArticles[1].title)
                assertEquals("2024-01-02T10:00:00Z", resultArticles[1].publishedAt)
                assertEquals("Old Article", resultArticles[2].title)
                assertEquals("2024-01-01T10:00:00Z", resultArticles[2].publishedAt)
            }
        }
}

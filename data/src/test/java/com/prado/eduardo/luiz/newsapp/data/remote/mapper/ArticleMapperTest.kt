package com.prado.eduardo.luiz.newsapp.data.remote.mapper

import com.prado.eduardo.luiz.domain.model.ArticleModel
import com.prado.eduardo.luiz.newsapp.data.remote.dto.ArticleDTO
import com.prado.eduardo.luiz.newsapp.data.remote.dto.SourceDTO
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleMapperTest {

    @Test
    fun `when mapping ArticleDTO to ArticleModel with valid data, then return correct model`() {
        // Given
        val dto = ArticleDTO(
            title = "Test Title",
            author = "Test Author",
            description = "Test Description",
            url = "http://test.com",
            urlToImage = "http://test.com/image.jpg",
            publishedAt = "2024-01-01",
            content = "Test Content",
            source = SourceDTO(
                id = "test-source",
                name = "Test Source"
            )
        )

        // When
        val result = dto.toModel()

        // Then
        assertEquals("Test Title", result.title)
        assertEquals("Test Author", result.author)
        assertEquals("Test Description", result.description)
        assertEquals("http://test.com", result.url)
        assertEquals("http://test.com/image.jpg", result.urlToImage)
        assertEquals("2024-01-01", result.publishedAt)
        assertEquals("Test Content", result.content)
    }

    @Test
    fun `when mapping ArticleDTO with null values, then return empty strings`() {
        // Given
        val dto = ArticleDTO(
            title = null,
            author = null,
            description = null,
            url = null,
            urlToImage = null,
            publishedAt = null,
            content = null,
            source = null
        )

        // When
        val result = dto.toModel()

        // Then
        assertEquals("", result.title)
        assertEquals("", result.author)
        assertEquals("", result.description)
        assertEquals("", result.url)
        assertEquals("", result.urlToImage)
        assertEquals("", result.publishedAt)
        assertEquals("", result.content)
    }
}

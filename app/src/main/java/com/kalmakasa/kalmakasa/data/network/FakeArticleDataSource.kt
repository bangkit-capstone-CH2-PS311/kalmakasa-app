package com.kalmakasa.kalmakasa.data.network

import com.kalmakasa.kalmakasa.domain.model.Article

object FakeArticleDataSource {
    private const val DESC =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

    val articles = listOf(
        Article(
            id = "article-1",
            imageUrl = "",
            title = "Sample Article",
            description = DESC
        ),
        Article(
            id = "article-2",
            imageUrl = "",
            title = "Sample Article",
            description = DESC
        ),
        Article(
            id = "article-3",
            imageUrl = "",
            title = "Sample Article",
            description = DESC
        ),
        Article(
            id = "article-4",
            imageUrl = "",
            title = "Sample Article",
            description = DESC
        ),
        Article(
            id = "article-5",
            imageUrl = "",
            title = "Sample Article",
            description = DESC
        ),
        Article(
            id = "article-6",
            imageUrl = "",
            title = "Sample Article",
            description = DESC
        ),
        Article(
            id = "article-7",
            imageUrl = "",
            title = "Sample Article",
            description = DESC
        ),
        Article(
            id = "article-8",
            imageUrl = "",
            title = "Sample Article",
            description = DESC
        )
    )
}
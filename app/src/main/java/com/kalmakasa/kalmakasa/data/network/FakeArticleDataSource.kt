package com.kalmakasa.kalmakasa.data.network

import com.kalmakasa.kalmakasa.common.Tag
import com.kalmakasa.kalmakasa.domain.model.Article

object FakeArticleDataSource {
    private const val DESC =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

    private const val sampleImageUrl =
        "https://media.licdn.com/dms/image/D5612AQHr99h8tLn6yw/article-cover_image-shrink_720_1280/0/1697354540708?e=2147483647&v=beta&t=EaygzSTCQRpD4l30CA74e_A2cqTlPFOYI6VL4hEFTnQ"

    val articles = listOf(
        Article(
            id = "article-1",
            imageUrl = sampleImageUrl,
            title = "Sample Article",
            description = DESC,
            tags = listOf(Tag.ANXIETY, Tag.CAREER)
        ),
        Article(
            id = "article-2",
            imageUrl = sampleImageUrl,
            title = "Sample Article",
            description = DESC,
            tags = listOf(Tag.DEPRESSION, Tag.STRESS)

        ),
        Article(
            id = "article-3",
            imageUrl = sampleImageUrl,
            title = "Sample Article",
            description = DESC,
            tags = listOf(Tag.STRESS)
        ),
        Article(
            id = "article-4",
            imageUrl = sampleImageUrl,
            title = "Sample Article",
            description = DESC,
            tags = listOf(Tag.CAREER)
        ),
        Article(
            id = "article-5",
            imageUrl = sampleImageUrl,
            title = "Sample Article",
            description = DESC,
            tags = listOf(Tag.ANXIETY, Tag.RELATIONSHIP)
        ),
        Article(
            id = "article-6",
            imageUrl = sampleImageUrl,
            title = "Sample Article",
            description = DESC,
            tags = listOf(Tag.CAREER)

        ),
        Article(
            id = "article-7",
            imageUrl = sampleImageUrl,
            title = "Sample Article",
            description = DESC,
            tags = listOf(Tag.DEPRESSION, Tag.STRESS)

        ),
        Article(
            id = "article-8",
            imageUrl = sampleImageUrl,
            title = "Sample Article",
            description = DESC,
            tags = listOf(Tag.ANXIETY, Tag.STRESS)
        )
    )
}
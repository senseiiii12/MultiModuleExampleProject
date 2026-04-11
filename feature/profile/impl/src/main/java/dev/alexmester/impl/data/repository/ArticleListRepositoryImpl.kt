package dev.alexmester.impl.data.repository

//class ArticleListRepositoryImpl(
//    private val local: ArticleListLocalDataSource,
//) : ArticleListRepository {
//
//    override fun getReadArticles(): Flow<List<NewsArticle>> =
//        combine(
//            local.getReadArticleIds(),
//            local.getAllBookmarks(),
//        ) { readIds, bookmarks ->
//            val bookmarkMap = bookmarks.associateBy { it.id }
//            readIds.mapNotNull { id ->
//                bookmarkMap[id]?.toDomain()
//                    ?: local.getArticleById(id)?.toDomain()
//            }
//        }
//
//    override fun getClappedArticles(): Flow<List<NewsArticle>> =
//        combine(
//            local.getClappedArticleIds(),
//            local.getAllBookmarks(),
//        ) { clappedIds, bookmarks ->
//            val bookmarkMap = bookmarks.associateBy { it.id }
//            clappedIds.mapNotNull { id ->
//                bookmarkMap[id]?.toDomain()
//                    ?: local.getArticleById(id)?.toDomain()
//            }
//        }
//}
package dev.alexmester.impl.presentation

object ArticleDetailReducer {

    fun reduce(
        state: ArticleDetailScreenState,
        intent: ArticleDetailIntent,
    ): ArticleDetailScreenState {
        val content = state as? ArticleDetailScreenState.Content ?: return state
        return when (intent) {
            is ArticleDetailIntent.Clap -> content.copy(
                clapCount = content.clapCount + 1,
                isClapAnimating = true,
            )
            is ArticleDetailIntent.ToggleBookmark -> content.copy(
                isBookmarked = !content.isBookmarked,
            )
            else -> state
        }
    }

    fun onClapAnimationDone(state: ArticleDetailScreenState): ArticleDetailScreenState =
        (state as? ArticleDetailScreenState.Content)?.copy(isClapAnimating = false) ?: state

    fun onBookmarkSynced(
        state: ArticleDetailScreenState,
        isBookmarked: Boolean,
    ): ArticleDetailScreenState =
        (state as? ArticleDetailScreenState.Content)?.copy(isBookmarked = isBookmarked) ?: state

    fun onClapCountUpdated(
        state: ArticleDetailScreenState,
        count: Int,
    ): ArticleDetailScreenState =
        (state as? ArticleDetailScreenState.Content)?.copy(clapCount = count) ?: state
}
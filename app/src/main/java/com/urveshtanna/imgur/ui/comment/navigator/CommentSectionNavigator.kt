package com.urveshtanna.imgur.ui.comment.navigator

import com.urveshtanna.imgur.data.model.Comment

interface CommentSectionNavigator {
    fun loadComments(comments: List<Comment>)

    fun newCommentAdded(comment: Comment)

    fun handleError(throwable: Throwable)
}
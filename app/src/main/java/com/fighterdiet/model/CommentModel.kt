package com.fighterdiet.model

import java.io.Serializable

class CommentModel(
    var personName: String,
    var time: String,
    var comment: String,
    var ownComment: Boolean,
) : Serializable
package com.example.devintensive.extensions

import com.example.devintensive.models.User
import com.example.devintensive.models.UserView
import com.example.devintensive.utils.Utils

fun User.toUserView(): UserView {

    val nickName = Utils.transliteration("$firstName $lastName")
    val initials = Utils.toInitials(firstName, lastName)
    val status = when {
        lastVisit == null -> "Еще ни разу не был"
        isOnline -> "online"

        else -> "Последний раз был ${lastVisit.humanizeDiff()}"
    }
    return UserView(
        id,
        fullName = "$firstName $lastName",
        nickName = nickName,
        initials = initials,
        avatar = avatar,
        status = status
    )


}




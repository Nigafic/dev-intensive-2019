package com.example.devintensive.models

import com.example.devintensive.models.BaseMessage
import com.example.devintensive.models.User

class Chat (
    val id: String,
    // По умолчанию - пустая коллекция
    val members: MutableList<User> = mutableListOf(),
    val messages: MutableList<BaseMessage> = mutableListOf()
) {
}
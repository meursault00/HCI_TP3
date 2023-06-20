package com.example.homehive.library

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.LinkedList
object HistoryStack {
    val stack = LinkedList<String>()
    private const val maxStackSize = 100

    @RequiresApi(Build.VERSION_CODES.O)
    fun push(item: String) {
        if (stack.size >= maxStackSize) {
            stack.removeFirst()
        }
        val currentTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val formattedTime = currentTime.format(formatter)
        val itemWithTime = "$formattedTime - $item"
        stack.addLast(itemWithTime)
    }
    fun pop(): String? {
        return stack.pollFirst()
    }

    fun clear() {
        stack.clear()
    }

    fun size(): Int {
        return stack.size
    }

}


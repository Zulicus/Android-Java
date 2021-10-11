package com.example.lektion7

import android.widget.TextView
import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CoroSample {

    companion object {
        fun coro() = runBlocking {
            launch {
                delay(5000)
                Log.d("test", getWorld())
            }
        }

        suspend fun getWorld(): String {
            delay(1000)
            return "world"
        }

        fun setWorld(textView: TextView) = runBlocking{
            launch {
                textView.text = getWorld();
            }
        }
    }


}
package com.example.lektion6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Variables
        val STATIC: Int = 1;
        var change: Int = 0;

        var testString: String? = null;

        Log.d("test", STATIC.toString() + change)
        Log.d("test", "$STATIC + $change")

        if(change<1){
            Log.d("test", "$change")
            change++
        }

        var ifAsExpress:String = if(change<2){"if"}else{"else"}
        Log.d("test",ifAsExpress)

        logValue();

        val sampleClass = sampleClass()
        sampleClass.HELLO_STRING

        sampleObject.dogName="fido"
        Log.d("test",sampleObject.dogName)

        var helloText:TextView = findViewById(R.id.helloText)
        helloText.text="Hello from Kotlin Break.nu"

        





    }

    private fun logValue() {
        Log.d("test", "logValue"+makeLog())
    }
    private fun makeLog():String {

        return "makeLog"
    }
}
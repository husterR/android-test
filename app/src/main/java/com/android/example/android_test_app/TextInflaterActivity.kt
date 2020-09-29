package com.android.example.android_test_app

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class TextInflaterActivity : AppCompatActivity() {

    private lateinit var timeText: TextView
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_inflater)
        val toolbar: Toolbar = findViewById(R.id.activity_text_inflater__toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setUpViews()
    }

    private fun setUpViews() {
        timeText = findViewById(R.id.activity_text_inflater__time_text)
        text = findViewById(R.id.activity_text_inflater__text)
        text.text = getHugeString()
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // Der lange Text musste in einem File gespiechert werden da Android nicht so lange UTF-8 Strings akzeptiert
    private fun getHugeString(): String? {
        val termsString = StringBuilder()
        val reader: BufferedReader
        try {
            reader = BufferedReader(
                InputStreamReader(assets.open("hugeText.txt"))
            )
            var str: String?
            while (reader.readLine().also { str = it } != null) {
                termsString.append(str)
            }
            reader.close()
            return termsString.toString()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}
// Wird nicht mehr genutzt da Flutter nichts vergleichbares liefert
class MyLogTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : androidx.appcompat.widget.AppCompatTextView(context, attrs, defStyle){

    private var startTime: MutableList<Long> = mutableListOf()
    private var endTime: MutableList<Long> = mutableListOf()
    lateinit var textView: TextView


    override fun onDraw(canvas: Canvas?) {
        endTime.add(SystemClock.elapsedRealtimeNanos())
        textView.text = "It took "+(endTime[0] - startTime[0])+" nanoseconds \n ${startTime[1]}   ${startTime[2]}   ${startTime[3]}"
        super.onDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        startTime.add(SystemClock.elapsedRealtimeNanos())
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    fun setLogTextView(textView: TextView){
        this.textView = textView
    }
}





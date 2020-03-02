package com.adityaikhbalm.textviewanimation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hello = "Hello World! asdasd asdasdas asdasdas asdasdas asdasdas"

        textBackground.apply {
            animDuration = 100
            text = hello
        }

        textAnimate.apply {
            animDuration = 100
            text = hello
        }

        btnClick.setOnClickListener {
            textBackground.startAnimation()
            textAnimate.startAnimation()
        }
    }
}

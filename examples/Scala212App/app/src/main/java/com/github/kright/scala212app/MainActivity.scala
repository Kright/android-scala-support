package com.github.kright.scala212app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity extends AppCompatActivity {
  override protected def onCreate(savedInstanceState: Bundle) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    println("Hello world!")
  }
}



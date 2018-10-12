package com.xianlai.mypaging

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_paging.setOnClickListener{
            val intent = Intent(this,PagingActivity::class.java)
            startActivity(intent)
        }
    }
}

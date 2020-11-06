package com.steve.mysafebox

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.steve.safeboxlibrary.SaveBoxActivity

class MainActivity : AppCompatActivity() {
    lateinit var bitmaps:MutableList<Bitmap>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       var tempbitmap =  BitmapFactory.decodeResource(resources,R.drawable.aaa)
        var tempbitmap2 =    BitmapFactory.decodeResource(resources,R.drawable.aaa)
        var temspbitmap2=   BitmapFactory.decodeResource(resources,R.drawable.aaa)
        var tempbitmap22 =   BitmapFactory.decodeResource(resources,R.drawable.aaa)
        var tempbitsmap =   BitmapFactory.decodeResource(resources,R.drawable.aaa)
        var tempbitmsap2 =   BitmapFactory.decodeResource(resources,R.drawable.aaa)
        var tempbitmdfap =   BitmapFactory.decodeResource(resources,R.drawable.aaa)
        var tempbitmapf2 =   BitmapFactory.decodeResource(resources,R.drawable.aaa)
        bitmaps = ArrayList()
        bitmaps.add(tempbitmap)
        bitmaps.add(tempbitmap2)
        bitmaps.add(temspbitmap2)
        bitmaps.add(tempbitmap22)
        bitmaps.add(tempbitsmap)
        bitmaps.add(tempbitmsap2)
        bitmaps.add(tempbitmdfap)
        bitmaps.add(tempbitmapf2)

        startActivity(Intent(this, SaveBoxActivity::class.java))
    }
}
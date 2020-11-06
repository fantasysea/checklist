package com.steve.safeboxlibrary

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_save_box.*
import kotlin.reflect.KClass

class SaveBoxActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_box)
        for (entry in toolList){
            var button = Button(this@SaveBoxActivity)
            button.setText(entry.name)
            button.setOnClickListener {
                val inten = Intent(this,entry.classnaem)
                startActivity(inten)
            }
            mytools.addView(button)
        }
        mytools.invalidate()
    }
    companion object{
    var     toolList =  mutableListOf(
        Jumpto("当前内存使用情况",MemoryActivity::class.java),
        Jumpto("GC情况",GCActivity::class.java),
        Jumpto("CPU使用率",CPUActivity::class.java)
    )
    }


}

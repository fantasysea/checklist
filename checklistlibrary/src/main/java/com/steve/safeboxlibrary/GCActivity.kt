package com.steve.safeboxlibrary

import android.os.Build
import android.os.Bundle
import android.os.Debug
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_g_c.*

class GCActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_g_c)
        val pss: Long =Debug.getPss()
        val NativeHeapSize: Long = Debug.getNativeHeapSize()
        val NativeHeapFreeSize: Long = Debug.getNativeHeapFreeSize()

        // 运行的GC次数
        var gccount =  Debug.getRuntimeStat("art.gc.gc-count")
        // GC使用的总耗时，单位是毫秒
        var gctime=  Debug.getRuntimeStat("art.gc.gc-time")
        // 阻塞式GC的次数
        var blockgccount = Debug.getRuntimeStat("art.gc.blocking-gc-count")
        // 阻塞式GC的总耗时
        var blockgccounttime =Debug.getRuntimeStat("art.gc.blocking-gc-time")
        gcinfo.setText("pss is "+pss+
                "\nNativeHeapSize  is "+NativeHeapSize+
                "\nNativeHeapFreeSize  is "+NativeHeapFreeSize+
                "\ngccount  is "+gccount+
                "\ngctime  is "+gctime+
                "\nblockgccount  is "+blockgccount+
                "\nblockgccounttime  is "+blockgccounttime
        )
    }
}
package com.steve.safeboxlibrary

import android.app.ActivityManager
import android.os.Bundle
import android.os.Debug
import android.os.Debug.MemoryInfo
import android.os.Process
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.steve.safeboxlibrary.utils.ToolsUtil
import kotlinx.android.synthetic.main.activity_memory.*
import java.io.File

class MemoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory)
        memoryinfo.setText(displayBriefMemory())
        getmeminfo.setOnClickListener {
            var result = ToolsUtil.exeCmd("cat /proc/meminfo  ")
//            ToolsUtil.exeCmd("dumpsys meminfo "+getPackageName())
            memoryinfo2.setText(result)
        }
        Toast.LENGTH_SHORT
        dumphprof.setOnClickListener {
           Debug.dumpHprofData((getExternalFilesDir("hprof")?.absolutePath ?: filesDir.absolutePath)+ File.separator + "test.hprof")
            var messgae = (getExternalFilesDir("hprof")?.absolutePath ?: filesDir.absolutePath)+ File.separator + "test.hprof"
            Toast.makeText(this,"文件成功保存到: "+messgae,Toast.LENGTH_SHORT).show()
        }
        Log.i("tag","Process.myPid() :"+Process.myPid())//最大内存

        limits.setOnClickListener {
            var result = ToolsUtil.exeCmd("cat /proc/"+ Process.myPid()+"/limits ")
//            var result2 = ToolsUtil.exeCmd("cat /proc/"+ Process.myPid()+"/fd ")
            Log.i("tag","applimits :"+result)//最大内存
            memoryinfo2.setText(result)
        }
    }

    fun displayBriefMemory():String
    {
        val sizem = 1024*1024
        var activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        var info: ActivityManager.MemoryInfo  =  ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(info)
//        var memInfo = activityManager.getProcessMemoryInfo(IntArray(Process.myPid()))


        Log.i("tag","app最大内存 :"+( activityManager.memoryClass )+"m")//最大内存

        Log.i("tag","app最大内存 :"+(Runtime.getRuntime().maxMemory() /sizem)+"m")//最大内存

        Log.i("tag","当前app已经分配的内存 :"+(Runtime.getRuntime().totalMemory()/sizem )+"m")//已经分配的内存
        Log.i("tag","当前app分配的内存剩余空余 :"+(Runtime.getRuntime().freeMemory()/sizem )+"m")//分配的内存剩余空余

        Log.i("tag","系统总内存:"+(info.totalMem /sizem)+"m")
        Log.i("tag","系统剩余内存:"+(info.availMem /sizem)+"m")
        Log.i("tag","系统是否处于低内存运行："+info.lowMemory)
        Log.i("tag","当系统剩余内存低于"+info.threshold /sizem+"m时就看成低内存运行")
        val javaUsed: Long = Runtime.getRuntime().totalMemory()/sizem -Runtime.getRuntime().freeMemory()/sizem
        var memoryInfo = MemoryInfo()
        Debug.getMemoryInfo(memoryInfo)
        Log.i("tag","dalvikPss: "+(memoryInfo.dalvikPss )+"m")
        Log.i("tag","nativePss: "+(memoryInfo.nativePss )+"m")
        Log.i("tag","otherPss： "+memoryInfo.otherPss)
        Log.i("tag","totalPss： "+memoryInfo.totalPss)
        Log.i("tag","dalvikPrivateDirty： "+memoryInfo.dalvikPrivateDirty)

        return "app最大内存 :"+(Runtime.getRuntime().maxMemory() /sizem)+"m\n"+
                "当前app已经分配的内存 :"+(Runtime.getRuntime().totalMemory()/sizem )+"m\n"+
                "当前app分配的内存剩余空余 :"+ (Runtime.getRuntime().freeMemory() )+"m\n"+
                "系统总内存:"+(info.totalMem /sizem)+"m\n"+
                "系统剩余内存:"+(info.availMem /sizem)+"m\n"+
                "系统是否处于低内存运行："+info.lowMemory+"\n"+
                "Java 内存使用超过最大限制的 85%："+javaUsed*100.0/(Runtime.getRuntime().maxMemory() /sizem)+"%\n"+
                "当系统剩余内存低于"+info.threshold /sizem+"m时就看成低内存运行"
    }
}
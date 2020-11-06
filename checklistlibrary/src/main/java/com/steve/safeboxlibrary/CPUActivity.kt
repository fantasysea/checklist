package com.steve.safeboxlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.os.Process
import com.steve.safeboxlibrary.utils.ToolsUtil
import kotlinx.android.synthetic.main.activity_c_p_u.*

class CPUActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_p_u)
        Debug.startMethodTracing("sample")

        var possible = ToolsUtil.exeCmd("cat /sys/devices/system/cpu/possible")
        var cpuinfo_max_freq = ToolsUtil.exeCmd("cat /sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq")
        var stat  = ToolsUtil.exeCmd("cat /proc/"+ Process.myPid()+"/stat")
        info.setText("possible :"+possible+"cpuinfo_max_freq :"+cpuinfo_max_freq+" cpu 使用率 stat"+stat)
        Debug.stopMethodTracing()

    }
}
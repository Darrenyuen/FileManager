package com.yuan.filemanager

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yuan.filemanager.utils.Utils
import org.json.JSONArray
import org.json.JSONObject
import java.io.File


class MainActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName

    private lateinit var test: Button
    private lateinit var log: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test = findViewById(R.id.test)
        log = findViewById(R.id.log)

        test.setOnClickListener {
            if (Utils.existSDCard()) Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show()
            else Toast.makeText(this, "no", Toast.LENGTH_SHORT).show()
        }

        log.setOnClickListener {
//            Log.d(TAG, Environment.getExternalStorageDirectory().toString())    ///storage/emulated/0
//            Log.d(TAG, Environment.getExternalStorageDirectory().absolutePath)  ///storage/emulated/0
//            Log.d(TAG, Environment.getExternalStorageDirectory().canonicalPath) ///storage/emulated/0
//            Log.d(TAG, Environment.getExternalStorageState())                   //mounted
//            Log.d(TAG, Environment.getExternalStorageState())                   //mounted

            getAllFiles()
        }

    }

    private fun getAllFiles() {
        val list = mutableListOf("/")
        while (list.size > 0) {
            val file = File(list.removeAt(0))
            if (!file.exists()) continue
            file.listFiles()?.forEach {it ->
                Log.d(TAG, it.absolutePath)
                list.add(it.absolutePath)
            }
        }
    }
}

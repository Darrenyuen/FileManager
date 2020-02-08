package com.yuan.filemanager

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yuan.filemanager.utils.FileUtil
import java.io.File


class MainActivity : AppCompatActivity() {

    private val TAG = this.javaClass.simpleName

    private lateinit var pathTextView: TextView
    private lateinit var listView: ListView

    private var mExitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   //改变状态栏上元素的颜色
            this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        listView = findViewById(R.id.listView)
        pathTextView = findViewById(R.id.path)
        pathTextView.text = "/"
        listView.adapter = FileItemAdapter(this, R.layout.item_file, FileUtil.getGroupFiles("/").sorted())
        listView.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.getItemAtPosition(i)
            if (File(item.toString()).isDirectory)  {
                pathTextView.text = item.toString()
                listView.adapter = FileItemAdapter(this, R.layout.item_file, FileUtil.getGroupFiles(item.toString()).sorted())
            } else {
                startActivity(FileUtil.openFile(item.toString()))
            }
        }
    }

    override fun onBackPressed() {
        if (!pathTextView.text.toString().equals("/")) {
            listView.adapter = FileItemAdapter(this, R.layout.item_file, FileUtil.getGroupFiles(File(pathTextView.text.toString()).parent).sorted())
            pathTextView.text = File(pathTextView.text.toString()).parent
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && pathTextView.text.toString().equals("/")) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show()
                mExitTime = System.currentTimeMillis()
            } else {
                this.finish()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}

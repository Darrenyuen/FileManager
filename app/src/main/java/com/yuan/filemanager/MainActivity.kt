package com.yuan.filemanager

import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.yuan.filemanager.adapter.FileItemAdapter
import com.yuan.filemanager.utils.FileUtil
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity(), FileDialogFragment.Callback {

    private val TAG = this.javaClass.simpleName

    private lateinit var toolbar: Toolbar
    private lateinit var pathTextView: TextView
    private lateinit var listView: ListView

    private var nowFileName: String? = null
    private var mExitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   //改变状态栏上元素的颜色
            this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        toolbar = findViewById(R.id.toolBar)
        listView = findViewById(R.id.listView)
        pathTextView = findViewById(R.id.path)
        toolbar.inflateMenu(R.menu.menu_toolbar)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.searchItem -> FileDialogFragment().show(supportFragmentManager, 1, "搜索文件")
                R.id.createItem -> {
                    FileDialogFragment().show(supportFragmentManager, 0, "创建文件")
                }
                else -> Toast.makeText(this, "nothing", Toast.LENGTH_SHORT).show()
            }
            false
        }
        pathTextView.text = "/"
        listView.adapter = FileItemAdapter(
            this,
            R.layout.item_file,
            FileUtil.getGroupFiles("/").sorted()
        )

        listView.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapterView.getItemAtPosition(i)
            if (File(item.toString()).isDirectory)  {
                pathTextView.text = item.toString()
                listView.adapter = FileItemAdapter(
                    this,
                    R.layout.item_file,
                    FileUtil.getGroupFiles(item.toString()).sorted()
                )
            } else {
                val intent = FileUtil.openFile(item.toString())
                if (intent?.getStringExtra("other").equals("other")) {
                    Toast.makeText(this, "无法打开该文件", Toast.LENGTH_SHORT).show()
                    return@setOnItemClickListener
                }
                startActivity(intent)
            }
        }

        listView.setOnItemLongClickListener(object : AdapterView.OnItemLongClickListener{
            override fun onItemLongClick(
                p0: AdapterView<*>?,
                p1: View?,
                p2: Int,
                p3: Long
            ): Boolean {
//                TODO("拿到nowfilename") //To change body of created functions use File | Settings | File Templates.
                FileDialogFragment().show(supportFragmentManager, 2, "delete or rename")
                return true
            }

        })
    }

    override fun onClickForCreateSearchFile(fileName: String, code: Int) {
        if (code == 0) createFile(fileName)
        else searchFile(fileName)
    }

    override fun onClickForDeleteRenameFile(code: Int) {
        if (code == 2) deleteFile()
        else renameFile()
    }

    /**
     * this file system is read only
     * it means you can only create a new file in this program package
     */
    private fun createFile(fileName: String) {
        if (fileName.equals("")) {
            Toast.makeText(this, "文件名不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        val file = File(pathTextView.text.toString() + fileName)
        if (file.exists()) {
            Toast.makeText(this, "该文件已存在", Toast.LENGTH_SHORT).show()
            return
        } else {
            try {
                file.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "创建文件出错", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun searchFile(fileName: String) {
        if (fileName.equals("")) {
            Toast.makeText(this, "文件名不能为空", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteFile() {
        Toast.makeText(this, "delete file", Toast.LENGTH_SHORT).show()
    }

    private fun renameFile() {
        Toast.makeText(this, "rename file", Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        if (!pathTextView.text.toString().equals("/")) {
            listView.adapter = FileItemAdapter(
                this,
                R.layout.item_file,
                FileUtil.getGroupFiles(File(pathTextView.text.toString()).parent).sorted()
            )
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

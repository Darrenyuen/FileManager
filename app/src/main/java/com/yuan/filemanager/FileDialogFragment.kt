package com.yuan.filemanager

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.lang.RuntimeException

/**
 *yuan
 *2020/2/10
 **/
class FileDialogFragment: DialogFragment() {

    private var title: String? = null
    private var code: Int? = null

    interface Callback {
        fun onClickForCreateSearchFile(fileName: String, code: Int)
        fun onClickForDeleteRenameFile(code: Int)
    }

    var callback: Callback? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (activity is Callback) {
            this.callback = activity
        } else throw object : RuntimeException() {

        }
    }

    fun show(fragmentManager: FragmentManager, code: Int, title: String) {
        Log.d("show", "show")
        this.title = title
        this.code = code
        show(fragmentManager, "DialogFragment")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d("create", "create")
        if (code == 0 || code == 1) {
            val view = LayoutInflater.from(activity).inflate(R.layout.dialog_create_search, null)
            return AlertDialog.Builder(activity)
                .setTitle(title)
                .setView(view)
                .setPositiveButton("确定") { p0, p1 ->
                    if (callback != null) {
                        val fileNameEditText = view.findViewById<EditText>(R.id.fileNameEditText)
                        code?.let { callback?.onClickForCreateSearchFile(fileNameEditText.text.toString(), it) }
                    }
                }
                .setNegativeButton("取消") { p0, p1 ->

                }
                .create()
        }
        else return AlertDialog.Builder(activity)
                .setItems(arrayOf("删除", "重命名")) { p0, p1 ->
                    if (callback != null) {
                        when(p1) {
                            0 -> code?.let { callback?.onClickForDeleteRenameFile(2) }
                            1 -> callback!!.onClickForDeleteRenameFile(3)
                        }
                    }
                }
                .create()
        }

    override fun onDestroyView() {
        super.onDestroyView()
        callback = null
    }

}
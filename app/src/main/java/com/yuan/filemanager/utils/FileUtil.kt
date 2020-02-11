package com.yuan.filemanager.utils

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.yuan.filemanager.MainActivity
import java.io.File
import java.util.*

/**
 *yuan
 *2020/2/8
 **/
class FileUtil {
    companion object{
        //获取上级或下级文件列表
        fun getGroupFiles(path: String): MutableList<String> {
            val file = File(path)
            val list = mutableListOf<String>()
            file.listFiles()?.forEach { it ->
                list.add(it.absolutePath)
            }
            return list
        }

        //打开各类型的文件
        fun openFile(path: String): Intent? {
            val file = File(path)
            if (!file.exists()) return null
            if (!file.name.contains(".")) return getOtherFileIntent()
            val suffix = file.name.substringAfterLast(".").toLowerCase(Locale.ROOT)
            Log.d("open file", suffix)
            return when(suffix) {
                "m4a", "mp3", "mid", "xmf", "ogg", "wav" -> getAudioFileIntent(path)
                "mp4", "3gp" -> getVideoFileIntent(path)
                "jpg", "gif", "png", "jpeg", "bmp" -> getImageFileIntent(path)
                "ppt" -> getPPTFileIntent(path)
                "xls", "xlsx", "xlsx_tmp" -> getExcelFileIntent(path)
                "doc", "docx" -> getWordFileIntent(path)
                "txt" -> getTextFileIntent(path)
                "pdf" -> getPDFFileIntent(path)
                "apk" -> getApkFileIntent(path)
                else -> getOtherFileIntent()
            }
        }

        private fun getAudioFileIntent(param: String): Intent {
            val intent = Intent("android.intent.action.VIEW")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("oneshot", 0)
            intent.putExtra("configchange", 0)
            val uri = Uri.fromFile(File(param))
            intent.setDataAndType(uri, "audio/*")
            return intent
        }

        private fun getVideoFileIntent(param: String): Intent {
            val intent = Intent("android.intent.action.VIEW")
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.putExtra("oneshot", 0)
            intent.putExtra("configchange", 0)
            val uri = Uri.fromFile(File(param))
            intent.setDataAndType(uri, "video/*")
            return intent
        }

        private fun getImageFileIntent(param: String): Intent {
            val intent = Intent("android.intent.action.VIEW")
            intent.addCategory("android.intent.category.DEFAULT")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri = Uri.fromFile(File(param))
            intent.setDataAndType(uri, "image/*")
            return intent
        }

        private fun getPPTFileIntent(param: String): Intent {
            val intent = Intent("android.intent.action.VIEW")
            intent.addCategory("android.intent.category.DEFAULT")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri = Uri.fromFile(File(param))
            intent.setDataAndType(uri, "application/vnd.ms-powerpoint")
            return intent
        }

        private fun getExcelFileIntent(param: String): Intent {
            val intent = Intent("android.intent.action.VIEW")
            intent.addCategory("android.intent.category.DEFAULT")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri = Uri.fromFile(File(param))
            intent.setDataAndType(uri, "application/vnd.ms-excel")
            return intent
        }

        private fun getWordFileIntent(param: String): Intent {
            val intent = Intent("android.intent.action.VIEW")
            intent.addCategory("android.intent.category.DEFAULT")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri = Uri.fromFile(File(param))
            intent.setDataAndType(uri, "application/msword")
            return intent
        }

        private fun getTextFileIntent(param: String): Intent {
            val intent = Intent("android.intent.action.VIEW")
            intent.addCategory("android.intent.category.DEFAULT")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri = Uri.fromFile(File(param))
            intent.setDataAndType(uri, "text/plain")
            return intent
        }

        private fun getPDFFileIntent(param: String): Intent {
            val intent = Intent("android.intent.action.VIEW")
            intent.addCategory("android.intent.category.DEFAULT")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri = Uri.fromFile(File(param))
            intent.setDataAndType(uri, "application/pdf")
            return intent
        }

        private fun getApkFileIntent(param: String): Intent {
            val intent = Intent("android.content.Intent.ACTION_VIEW")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            val uri = Uri.fromFile(File(param))
            intent.setDataAndType(uri, "application/vnd.android.package-archive")
            return intent
        }

        private fun getOtherFileIntent(): Intent {
            return Intent().putExtra("other", "other")
        }
    }
}
package com.yuan.filemanager.utils

import android.os.Environment

/**
 *yuan
 *2020/2/7
 **/
class Utils {
    companion object {
        fun existSDCard() : Boolean{
            return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
        }
    }
}
package com.yuan.filemanager.utils

import android.app.Activity
import android.app.Dialog
import android.os.Build
import android.view.Window
import android.view.WindowManager

/**
 *yuan
 *2020/2/8
 **/
class StatusBarUtil {
    companion object{
        //设置Activity对应的顶部状态栏的颜色
        fun setWindowStatusBarColor(activity: Activity, colorResId: Int) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val window = activity.window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = activity.resources.getColor(colorResId)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //设置Dialog对应的顶部状态栏的颜色
        fun setWindowStatusBarColor(dialog: Dialog, colorResId: Int) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    val window = dialog.window
                    window!!.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    window.statusBarColor = dialog.context.resources.getColor(colorResId)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
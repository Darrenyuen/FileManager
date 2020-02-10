package com.yuan.filemanager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.yuan.filemanager.R
import java.io.File

/**
 *yuan
 *2020/2/8
 **/
class FileItemAdapter(context: Context, resource: Int, objects: List<String>) :
    ArrayAdapter<String>(context, resource, objects) {

    private val list = objects

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_file, parent, false)
            viewHolder = ViewHolder()
            viewHolder.fileIcon = view.findViewById(R.id.iconFileType)
            viewHolder.fileName = view.findViewById(R.id.fileNameTextView)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        if (File(list[position]).isDirectory) viewHolder.fileIcon?.setImageResource(
            R.drawable.icon_folder
        )
        else {
            when(list[position].substringAfterLast(".")) {
                "m4a", "mp3", "mid", "xmf", "ogg", "wav" -> viewHolder.fileIcon?.setImageResource(
                    R.drawable.icon_musicfile
                )
                "mp4", "3gp" -> viewHolder.fileIcon?.setImageResource(R.drawable.icon_videofile)
                "jpg", "gif", "png", "jpeg", "bmp" -> viewHolder.fileIcon?.setImageResource(
                    R.drawable.icon_picturefile
                )
                "ppt" -> viewHolder.fileIcon?.setImageResource(R.drawable.icon_pptfile)
                "xls" -> viewHolder.fileIcon?.setImageResource(R.drawable.icon_excelfile)
                "doc", "docx" -> viewHolder.fileIcon?.setImageResource(R.drawable.icon_wordfile)
                "txt" -> viewHolder.fileIcon?.setImageResource(R.drawable.icon_textfile)
                "pdf" -> viewHolder.fileIcon?.setImageResource(R.drawable.icon_pdffile)
                "apk" -> viewHolder.fileIcon?.setImageResource(R.drawable.icon_apkfile)
                else -> viewHolder.fileIcon?.setImageResource(R.drawable.icon_unknowfile)
            }
        }
        viewHolder.fileName?.text = list[position].substringAfterLast("/")
        return view
    }

    inner class ViewHolder {
        var fileIcon: ImageView? = null
        var fileName: TextView? = null
    }
}
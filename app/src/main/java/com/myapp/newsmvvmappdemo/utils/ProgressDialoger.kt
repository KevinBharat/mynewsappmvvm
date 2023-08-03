package com.myapp.newsmvvmappdemo.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.myapp.newsmvvmappdemo.R

object ProgressDialoger {
    private var progressDialog: Dialog? = null

    fun initialize(parent: Activity?) {
        if (parent == null) return
        try {
            progressDialog = Dialog(parent)
            progressDialog!!.setContentView(R.layout.dialog_progress)
            progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val img: ImageView = progressDialog!!.findViewById(R.id.img)
            Glide.with(parent).asGif().load(R.raw.ic_loader).into(img)

            progressDialog!!.setCancelable(false)
            parent.runOnUiThread(Runnable {
                try {
                    if (progressDialog != null) {
                        progressDialog!!.show()
                    }
                } catch (e: Exception) {
                }
            })
        } catch (e: Exception) {
        }
    }


    fun dismiss() {
        try {
            if (progressDialog != null && progressDialog!!.isShowing) {
                progressDialog!!.dismiss()
                progressDialog = null
            }
        } catch (e: Exception) {
        }
    }
}
package com.example.koskosan.home

import android.app.Activity
import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.koskosan.R

class ProgressDialog(val loading: Activity) {
    private lateinit var isAlertDialog: AlertDialog

    fun showLoading() {
        val inflater = loading.layoutInflater
        val dialogView = inflater.inflate(R.layout.progress_dialog, null)

        val buildAlert = AlertDialog.Builder(loading)
        buildAlert.setView(dialogView)
        buildAlert.setCancelable(false)
        isAlertDialog = buildAlert.create()
        isAlertDialog.window?.setBackgroundDrawableResource(R.color.tsp)
        isAlertDialog.show()
    }

    fun dismissLoading() {
        isAlertDialog.dismiss()
    }


}
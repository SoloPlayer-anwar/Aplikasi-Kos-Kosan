package com.example.koskosan

import android.widget.TextView
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    fun TextView.formatPrice(value: Int?) {
        this.text = getCurrencyIdr(java.lang.Double.parseDouble(value.toString()))
    }

    fun getCurrencyIdr(price: Double): String {
        val format = DecimalFormat("#,###,###")
        return "IDR " + format.format(price).replace(",".toRegex(), ".")
    }

    fun Long.convertLongToTime(formatTanggal: String): String {
        val date = Date(this)
        val format = SimpleDateFormat(formatTanggal)
        return format.format(date)
    }
}
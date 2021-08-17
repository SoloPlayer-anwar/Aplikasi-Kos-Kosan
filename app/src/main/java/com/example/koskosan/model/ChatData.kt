package com.example.koskosan.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class ChatData (
 var imageUser: String? = "",
 var nameUser: String? = "",
 var waktu: String? = "",
 var pesan: String? = ""
 ):Parcelable
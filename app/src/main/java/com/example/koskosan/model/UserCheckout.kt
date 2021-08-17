package com.example.koskosan.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class UserCheckout(
    var spinerKamar: String? = "",
    var spinerBulan: String? = "",
    var imagePoster: String? = "",
    var fromId: String? = "",
    var nameUser: String? = "",
    var imageUser: String? = "",
    var price: Int? = null,
    var rateCheck: String? = "",
    var status: String? = "",
    var fasilitas: String? = "",
    var pending: String? = ""
):Parcelable
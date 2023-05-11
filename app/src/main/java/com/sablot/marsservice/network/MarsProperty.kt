package com.sablot.marsservice.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarsProperty(
    val id: String,
    @Json(name = "img_src") val imgSrcUrl: String,
    val type: String,
    val price: Double,
) : Parcelable {
    val isRental get() = type == Type.RENT.text
}

enum class Type(val text: String){
    RENT("rent"),
    BUY("buy"),
    ALL("all")
}
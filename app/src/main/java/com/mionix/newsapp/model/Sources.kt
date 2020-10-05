package com.mionix.newsapp.model

import com.google.gson.annotations.SerializedName

data class Sources (
    @SerializedName("status") val status : String,
    @SerializedName("sources") val sources : List<AllSource>
)
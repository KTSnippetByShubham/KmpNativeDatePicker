package com.shubh.kmpnativedatepicker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
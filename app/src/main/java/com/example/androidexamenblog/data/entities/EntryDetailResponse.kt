package com.example.androidexamenblog.data.entities

data class EntryDetailResponse(
    var resultado: String = "",
    var data: BlogEntry = BlogEntry("","",0L,"","")
)

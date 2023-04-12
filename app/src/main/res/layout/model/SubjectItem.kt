package com.education.geno.model

data class SubjectItem(
    val id: Int,
    val class_id: String,
    val image: String,
    var name: String,
    var selected: Boolean=false,
    val parent: String,
    val status: String,
    val created_at: String,
    val updated_at: String
) :SuperCastClass(),java.io.Serializable

/*
"34",
"35",
"36",
"38",*/

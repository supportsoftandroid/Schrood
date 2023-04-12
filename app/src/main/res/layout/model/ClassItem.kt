package com.education.geno.model

import java.io.Serializable

data class ClassItem(
    val id: String,
    val name: String,
    var branch:ArrayList<BranchItem>,
    var subjects:ArrayList<SubjectItem>,
    val updated_at: String,
    val created_at: String,
    var selected: Boolean=false,
):SuperCastClass(), Serializable
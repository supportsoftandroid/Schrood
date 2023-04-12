package com.education.geno.model

data class BranchItem(
    val id: Int,
    var name: String,
    var subjects:ArrayList<SubjectItem>
)
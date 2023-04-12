package com.education.geno.model



data class SelectedItem(
    val title: String,
    val type: String,
    var is_selected: Boolean=false,
):SuperCastClass()
package com.mybaseprojectandroid.model

data class PasienModel(
    var id: String? = null,
    val namaLengkap: String? = null,
    val alamat: String? = null,
    val tanggalLahir: String? = null,
    val lamaDiagnosaDm: String? = null,
    val pengobatan: String? = null,
    val pendamping: String? = null,
    val username: String? = null,
    val password: String? = null,
)

package com.mybaseprojectandroid.model

data class PasienModel(
    var id: String? = null,
    var namaLengkap: String? = null,
    var alamat: String? = null,
    var tanggalLahir: String? = null,
    var lamaDiagnosaDm: String? = null,
    var pengobatan: String? = null,
    var pendamping: String? = null,
    val username: String? = null,
    var password: String? = null,
)

package com.mybaseprojectandroid.model

data class UserModel(
    var id: String? = null,
    var namaLengkap: String? = null,
    var alamat: String? = null,
    var tanggalLahir: String? = null,
    var lamaDiagnosaDm: String? = null,
    var pengobatan: String? = null,
    var pendamping: String? = null,
    val username: String? = null,
    var password: String? = null,
    val readingDocument: Boolean = false,
    val typeAkun: String? = null
)

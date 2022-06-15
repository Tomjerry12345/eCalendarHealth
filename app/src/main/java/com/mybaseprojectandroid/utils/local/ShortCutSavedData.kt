package com.mybaseprojectandroid.utils.local

import com.mybaseprojectandroid.model.AdminModel
import com.mybaseprojectandroid.model.PasienModel
import com.mybaseprojectandroid.utils.other.Constant

fun setSavedAdmin(data: AdminModel?) {
    SavedData.setObject(Constant.KEY_ADMIN, data)
}

fun setSavedPasien(data: PasienModel?) {
    SavedData.setObject(Constant.KEY_PASIEN, data)
}

fun getSavedAdmin(): AdminModel? {
    return SavedData.getObject(Constant.KEY_ADMIN, AdminModel()) as AdminModel?
}

fun getSavedPasien(): PasienModel? {
    return SavedData.getObject(Constant.KEY_PASIEN, PasienModel()) as PasienModel?
}
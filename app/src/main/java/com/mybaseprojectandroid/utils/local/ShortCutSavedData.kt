package com.mybaseprojectandroid.utils.local

import com.mybaseprojectandroid.model.AdminModel
import com.mybaseprojectandroid.model.UserModel
import com.mybaseprojectandroid.utils.other.Constant

fun setSavedAdmin(data: AdminModel?) {
    SavedData.setObject(Constant.KEY_ADMIN, data)
}

fun setSavedPasien(data: UserModel?) {
    SavedData.setObject(Constant.KEY_PASIEN, data)
}

fun getSavedAdmin(): AdminModel? {
    return SavedData.getObject(Constant.KEY_ADMIN, AdminModel()) as AdminModel?
}

fun getSavedPasien(): UserModel? {
    return SavedData.getObject(Constant.KEY_PASIEN, UserModel()) as UserModel?
}
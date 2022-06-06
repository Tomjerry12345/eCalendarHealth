package com.mybaseprojectandroid.utils.local

import com.mybaseprojectandroid.model.PasienModel
import com.mybaseprojectandroid.utils.other.Constant

fun getSavedPasien(): PasienModel {
    return SavedData.getObject(Constant.KEY_PASIEN, PasienModel()) as PasienModel
}
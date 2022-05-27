package com.mybaseprojectandroid.database.firebase

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mybaseprojectandroid.model.ExamplesModel
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.local.SavedData
import com.mybaseprojectandroid.utils.other.showLogAssert
import kotlinx.coroutines.tasks.await

class FirebaseDatabase {

    private val db = Firebase.firestore

    suspend fun addData(path: String, data: Any): Response {
        return try {
            val response = db.collection(path).add(data).await()
            Response.Changed(response.id)
        } catch (e: Exception) {
            Response.Error("${e.message}")
        }

    }

    suspend fun getAllData(
        reference: String
    ): Response {
        return try {
            val data = db
                .collection(reference)
                .get()
                .await()

            Response.Changed(data)

        } catch (e: Exception) {
            showLogAssert("error", "${e.message}")
            Response.Error("${e.message}")
        }
    }

    suspend fun update(
        reference: String,
        colection: String,
        update: String?,
        data: Any,
        msg: String = ""
    ): Response {
        return try {
            if (update != null) {
                db.collection(reference)
                    .document(colection)
                    .update(update, data)
                    .await()
            } else {
                db.collection(reference)
                    .document(colection)
                    .set(data)
                    .await()
            }

            Response.Success(msg)

        } catch (e: Exception) {
            showLogAssert("error", "${e.message}")
            Response.Error("${e.message}")
        }
    }

    suspend fun delete(reference: String, colection: String, msg: String): Response {
        return try {
            db.collection(reference)
                .document(colection)
                .delete()
                .await()

            Response.Success(msg)

        } catch (e: Exception) {
            showLogAssert("error", "${e.message}")
            Response.Error("${e.message}")
        }
    }

    suspend fun login(path: String, username: String, password: String, msgSuccess: String, msgError: String): Response {
        return try {
            val data = db.collection(path).whereEqualTo("username", username).get().await()
            var password1 = ""

            // Check username
            if (data.isEmpty) {
                Response.Error(
                    "Username tidak di temukan"
                )

            } else {
                // Check Password
                for (i in data) {
                    password1 = i["password"] as String
                    showLogAssert("data", "${i["username"]}")
                }

                if (password1 == password) {
                    data.forEach {
                        showLogAssert("data", "${it.toObject(ExamplesModel::class.java)}")
                        val model = it.toObject(ExamplesModel::class.java)
                        SavedData.setObject(model)
                    }

                    Response.Success("Sukses")
                } else {
                    Response.Error("Password salah")
                }

            }

        } catch (e: Exception) {
            Response.Error(e.message.toString())
        }
    }

    suspend fun register(path: String, data: Any, username: String): Response {
        return try {
            val sameUsername = db.collection(path).whereEqualTo("username", username).get().await()
            if (sameUsername.isEmpty) {
                db.collection(path).add(data).await()
                Response.Success("Berhasil")
            } else {
                Response.Error("Username sudah terdaftar")
            }
        } catch (e: Exception) {
            Response.Error("${e.message}")
        }

    }

}
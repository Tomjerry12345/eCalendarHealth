package com.mybaseprojectandroid.database.firebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mybaseprojectandroid.model.ExamplesModel
import com.mybaseprojectandroid.model.UserModel
import com.mybaseprojectandroid.utils.local.SavedData
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.showLogAssert
import kotlinx.coroutines.tasks.await
import java.util.HashMap

class FirebaseDatabase {

    private val db = Firebase.firestore

    suspend fun addData(path: String, data: Any): Response {
        return try {
            val response = db.collection(path).add(data).await()
            update(path, response.id, "id", response.id)
            Response.Success("Berhasil")
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

    suspend fun getDataByQuery(
        reference: String,
        listQuery: List<HashMap<String, out Any>>
    ): Response {
        return try {

            val data = db
                .collection(reference)

            val response = getQuery(data, listQuery).get().await()

            Response.Changed(response)

        } catch (e: Exception) {
            showLogAssert("error", "${e.message}")
            Response.Error("${e.message}")
        }
    }

    private fun getQuery(
        reference: CollectionReference,
        listQuery: List<HashMap<String, out Any>>,
    ): Query {
        var query: Query? = null

        listQuery.map {
            val key = it["key"].toString()
            val value = it["value"]
            query = if (query == null)
                initQuery(reference, key, value)
            else
                indexingQuery(query!!, key, value)
        }

        return query!!
    }

    private fun initQuery(
        reference: CollectionReference,
        key: String,
        value: Any?
    ): Query {
        return reference.whereEqualTo(key, value)
    }

    private fun indexingQuery(
        query: Query,
        key: String,
        value: Any?
    ): Query {
        return query.whereEqualTo(key, value)
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

    suspend fun login(path: String, username: String, password: String): Response {
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
                }

                if (password1 == password) {
                    data.forEach {
                        showLogAssert("data", "${it.toObject(ExamplesModel::class.java)}")
                        val model = it.toObject(UserModel::class.java)
                        SavedData.setObject(Constant.KEY_PASIEN, model)
                    }
                    SavedData.setBoolean(Constant.KEY_IS_LOGGIN, true)
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
                val response = db.collection(path).add(data).await()
                update(path, response.id, "id", response.id)
                Response.Changed(response.id)
            } else {
                Response.Error("Username sudah terdaftar")
            }
        } catch (e: Exception) {
            Response.Error("${e.message}")
        }

    }

}
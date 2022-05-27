package com.mybaseprojectandroid.ui.examples.firebase.crud

import android.view.View
import androidx.lifecycle.*
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.model.ExamplesModel
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.showLogAssert
import kotlinx.coroutines.launch

class ExamplesFirebaseCrudViewModel(private val firebaseDatabase: FirebaseDatabase) : ViewModel() {
    // Get Data In Firebase
    val data: LiveData<Response> = liveData {
        val response = firebaseDatabase.getAllData("pertanyaan")
        emit(response)
    }

    fun onAddDataFirebase(view: View) {
        val modelPertanyaan = ExamplesModel(examples = "examples")
        viewModelScope.launch {
            when (val response: Response =
                firebaseDatabase.addData("pertanyaan", modelPertanyaan)) {
                is Response.Success -> {
                }
                is Response.Error -> {
                    showLogAssert("error", response.error)
                }

                is Response.Changed -> {

                    // Set Id From Database

//                    val id = response.data
//                    modelPertanyaan.id = id.toString()
//                    firebaseDatabase.updateOneCollection(
//                        "pertanyaan",
//                        id.toString(),
//                        null,
//                        modelPertanyaan,
//                        "sukses"
//                    )

                    // Move Fragment
//                    view.findNavController().navigate(R.id.action_pertanyaanFragment_self)
                }
            }
        }

    }


    class Factory(private val firebaseDatabase: FirebaseDatabase) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExamplesFirebaseCrudViewModel(firebaseDatabase) as T
        }
    }
}
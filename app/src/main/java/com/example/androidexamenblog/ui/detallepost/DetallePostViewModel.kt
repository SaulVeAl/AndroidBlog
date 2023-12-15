package com.example.androidexamenblog.ui.detallepost

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidexamenblog.R
import com.example.androidexamenblog.data.entities.BlogEntry
import com.example.androidexamenblog.data.entities.EntryDetailResponse
import com.example.androidexamenblog.data.remote.BlogService
import com.example.androidexamenblog.data.repository.BlogRepository
import com.example.androidexamenblog.utils.Resource
import com.example.androidexamenblog.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallePostViewModel @Inject constructor(
    private val app: Application,
    private val repository: BlogRepository
) : AndroidViewModel(app) {

    val detallePostResponse: MutableLiveData<Resource<EntryDetailResponse>> = MutableLiveData()

    fun obtenerPost(id: String) = viewModelScope.launch(Dispatchers.IO){

        detallePostResponse.postValue(Resource.Loading())

        try{

            if(Utils.isNetworkAvailable(app)){

                val result = repository.obtenerEntrada(id)

                if(result.isSuccessful){

                    detallePostResponse.postValue(Resource.Success(result.body() as EntryDetailResponse))

                }else{

                    detallePostResponse.postValue(Resource.Error(result.message(),null))

                }

            } else {

                detallePostResponse.postValue(Resource.Error(app.resources.getString(R.string.error_red)))

            }

        }catch (ex: Exception){

            detallePostResponse.postValue(ex.localizedMessage?.let { Resource.Error(it.toString(),null) })
            Log.e("ViewModel", "Error: Obtener Post -> ${ex.localizedMessage}")

        }

    }

}
package com.example.androidexamenblog.ui.listaposts

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidexamenblog.R
import com.example.androidexamenblog.data.entities.BlogEntry
import com.example.androidexamenblog.data.entities.BlogEntryDB
import com.example.androidexamenblog.data.remote.BlogService
import com.example.androidexamenblog.data.repository.BlogRepository
import com.example.androidexamenblog.utils.Resource
import com.example.androidexamenblog.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogEntryListViewModel @Inject constructor(
    private val app: Application,
    private val repository: BlogRepository
): AndroidViewModel(app) {

    val listaResponse: MutableLiveData<Resource<ArrayList<BlogEntry>>> = MutableLiveData()

    fun getLista() = viewModelScope.launch(Dispatchers.IO){

        listaResponse.postValue(Resource.Loading())

        try{

            if(Utils.isNetworkAvailable(app)){

                val result = repository.obtenerEntradas()

                if(result.isSuccessful){

                    var entradas = result.body() as ArrayList<BlogEntry>

                    if(entradas.size>0){
                        var listaDB = arrayListOf<BlogEntryDB>()

                        entradas.forEach {

                            listaDB.add(BlogEntryDB(it.idEntry,it.title,it.author,it.content,it.date))

                        }

                        repository.guardarEntradas(listaDB)
                    }

                    listaResponse.postValue(Resource.Success(entradas))

                }else{

                    listaResponse.postValue(Resource.Error(result.message(),null))

                }

            } else {

                var listaBD = repository.obtenerEntradasLocal()

                var entradasBD = arrayListOf<BlogEntry>()

                listaBD.value?.forEach {

                    entradasBD.add(BlogEntry(it.author?:"",it.content?:"",it.date ?: 0L,it.idEntry))

                }

                if(entradasBD.size>0){
                    listaResponse.postValue(Resource.Success(entradasBD))
                } else {
                    listaResponse.postValue(Resource.Error(app.resources.getString(R.string.error_red)))
                }

            }

        }catch (ex: Exception){

            listaResponse.postValue(ex.localizedMessage?.let { Resource.Error(it.toString(),null) })
            Log.e("ViewModel", "Error: Obtener Lsitado -> ${ex.localizedMessage}")

        }

    }

}
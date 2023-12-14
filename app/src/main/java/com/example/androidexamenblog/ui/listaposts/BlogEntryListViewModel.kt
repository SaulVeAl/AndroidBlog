package com.example.androidexamenblog.ui.listaposts

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidexamenblog.R
import com.example.androidexamenblog.data.entities.BlogEntry
import com.example.androidexamenblog.data.remote.BlogService
import com.example.androidexamenblog.utils.Resource
import com.example.androidexamenblog.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BlogEntryListViewModel(private val app: Application): AndroidViewModel(app) {

    val listaResponse: MutableLiveData<Resource<ArrayList<BlogEntry>>> = MutableLiveData()

    fun getLista() = viewModelScope.launch(Dispatchers.IO){

        listaResponse.postValue(Resource.Loading())

        try{

            if(Utils.isNetworkAvailable(app)){

                val result = BlogService.getInstance().getBlogEntries()

                if(result.isSuccessful){

                    listaResponse.postValue(Resource.Success(result.body() as ArrayList<BlogEntry>))

                }else{

                    listaResponse.postValue(Resource.Error(result.message(),null))

                }

            } else {

                listaResponse.postValue(Resource.Error(app.resources.getString(R.string.error_red)))

            }

        }catch (ex: Exception){

            listaResponse.postValue(ex.localizedMessage?.let { Resource.Error(it.toString(),null) })
            Log.e("ViewModel", "Error: Obtener Lsitado -> ${ex.localizedMessage}")

        }

    }

}
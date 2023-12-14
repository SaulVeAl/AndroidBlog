package com.example.androidexamenblog.ui.crearpost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.androidexamenblog.R
import com.example.androidexamenblog.data.entities.BlogEntry
import com.example.androidexamenblog.databinding.FragmentCrearPostBinding
import com.example.androidexamenblog.ui.MainActivity
import com.example.androidexamenblog.utils.Resource
import com.example.androidexamenblog.utils.Utils

class CrearPostFragment : Fragment() {

    private lateinit var binding: FragmentCrearPostBinding
    val viewModel: CrearPostViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCrearPostBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        binding.btnAtras.setOnClickListener {

            findNavController().navigateUp()

        }

        binding.btnRegistrar.setOnClickListener {

            registrarEntrada()

        }

    }

    fun registrarEntrada(){

        var titulo = binding.etTitulo.text.toString()
        var autor = binding.etAutor.text.toString()
        var contenido = binding.etContenido.text.toString()

        if(titulo.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Titulo es un dato obligatorio!",Toast.LENGTH_SHORT).show()
            return
        }

        if(autor.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Autor es un dato obligatorio!",Toast.LENGTH_SHORT).show()
            return
        }

        if(contenido.isNullOrEmpty()){
            Toast.makeText(requireContext(),"Contenido es un dato obligatorio!",Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.registrarPost(titulo,autor,contenido)

    }

    fun initObservers(){

        viewModel.crearPostResponse.observe(viewLifecycleOwner){ response ->

            when (response) {

                is Resource.Loading -> {


                    Utils.loader(
                        requireActivity(),
                        (requireActivity() as MainActivity).loader,
                        true
                    )

                }

                is Resource.Success -> {

                    var registrado = response.data as Boolean

                    Utils.loader(
                        requireActivity(),
                        (requireActivity() as MainActivity).loader,
                        false
                    )

                    if(registrado){

                        findNavController().navigateUp()

                    }

                }

                is Resource.Error -> {

                    var msj = response.message ?: "Error"
                    Toast.makeText(requireContext(), msj, Toast.LENGTH_SHORT).show()

                    Utils.loader(
                        requireActivity(),
                        (requireActivity() as MainActivity).loader,
                        false
                    )

                }

            }

        }

    }

}
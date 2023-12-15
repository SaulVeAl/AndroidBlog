package com.example.androidexamenblog.ui.detallepost

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
import com.example.androidexamenblog.data.entities.EntryDetailResponse
import com.example.androidexamenblog.databinding.FragmentDetallePostBinding
import com.example.androidexamenblog.ui.MainActivity
import com.example.androidexamenblog.utils.Resource
import com.example.androidexamenblog.utils.Utils

class DetallePostFragment : Fragment() {

    private lateinit var binding: FragmentDetallePostBinding
    val viewModel: DetallePostViewModel by activityViewModels()
    var idPost: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetallePostBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()

        idPost = requireArguments().getString("idPost","")

        binding.btnAtras.setOnClickListener {

            findNavController().navigateUp()

        }

        if(!idPost.isNullOrEmpty()){

            viewModel.obtenerPost(idPost)

        }

    }

    fun initObservers(){

        viewModel.detallePostResponse.observe(viewLifecycleOwner){ response ->

            when (response) {

                is Resource.Loading -> {


                    Utils.loader(
                        requireActivity(),
                        (requireActivity() as MainActivity).loader,
                        true
                    )

                }

                is Resource.Success -> {

                    var post = response.data as EntryDetailResponse

                    mostrarDetallePost(post.data)

                    Utils.loader(
                        requireActivity(),
                        (requireActivity() as MainActivity).loader,
                        false
                    )

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

    private fun mostrarDetallePost(post: BlogEntry) {

        binding.tvTitulo.text = post.title
        binding.tvAutor.text = post.author
        binding.tvFecha.text = Utils.convertDate(post.date,"dd/MM/yyyy HH:mm")
        binding.tvContenido.text = post.content

    }

    override fun onResume() {
        super.onResume()
        if(Utils.isNetworkAvailable(requireContext())){
            (requireActivity() as MainActivity).clNoInternet?.visibility = View.GONE
        }else {
            (requireActivity() as MainActivity).clNoInternet?.visibility = View.VISIBLE
        }
    }

}
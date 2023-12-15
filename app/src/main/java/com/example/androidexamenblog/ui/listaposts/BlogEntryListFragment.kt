package com.example.androidexamenblog.ui.listaposts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidexamenblog.R
import com.example.androidexamenblog.data.entities.BlogEntry
import com.example.androidexamenblog.databinding.FragmentBlogEntryListBinding
import com.example.androidexamenblog.ui.MainActivity
import com.example.androidexamenblog.utils.Resource
import com.example.androidexamenblog.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BlogEntryListFragment : Fragment() {

    private lateinit var binding: FragmentBlogEntryListBinding
    private lateinit var adapter: BlogEntryAdapter
    val viewModel: BlogEntryListViewModel by viewModels()
    var listaPosts = arrayListOf<BlogEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBlogEntryListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()
        (requireActivity() as MainActivity).fabEntrada!!.setOnClickListener {

            if(Utils.isNetworkAvailable(requireContext())){
                (requireActivity() as MainActivity).clNoInternet?.visibility = View.GONE
                findNavController().navigate(R.id.action_blogEntryListFragment_to_crearPostFragment)
            }else {
                (requireActivity() as MainActivity).clNoInternet?.visibility = View.VISIBLE
                Toast.makeText(requireContext(),"No hay conexión a internet, no es posible registrar nuevas entradas al blog :D",Toast.LENGTH_SHORT).show()
            }

        }

        var opcionesBusqueda = arrayListOf<String>()

        opcionesBusqueda.add("Titulo")
        opcionesBusqueda.add("Autor")
        opcionesBusqueda.add("Contenido")

        var adapterSpinner = ArrayAdapter<String>(requireContext(),R.layout.item_simple_spinner_base,opcionesBusqueda)

        adapterSpinner.setDropDownViewResource(R.layout.item_simple_spinner)

        binding.spnrBusqueda.adapter = adapterSpinner

        binding.spnrBusqueda.onItemSelectedListener = object : OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                busquedaPosts()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

        binding.etBusqueda.setOnEditorActionListener { v, actionId, event ->

            if(actionId == EditorInfo.IME_ACTION_SEARCH){

                busquedaPosts()
                return@setOnEditorActionListener true

            }
            false

        }

    }

    fun busquedaPosts(){

        var nuevaLista = arrayListOf<BlogEntry>()
        var textoBuscar = binding.etBusqueda.text.toString().uppercase()
        var index = binding.spnrBusqueda.selectedItemPosition

        listaPosts.forEach {

            if(index == 0){

                if(it.title.uppercase().contains(textoBuscar)){
                    nuevaLista.add(it)
                }

            } else if (index == 1){

                if(it.author.uppercase().contains(textoBuscar)){
                    nuevaLista.add(it)
                }

            } else if(index == 2){

                if(it.content.uppercase().contains(textoBuscar)){
                    nuevaLista.add(it)
                }

            }

        }

        adapter.setItems(nuevaLista)

    }

    override fun onResume() {
        super.onResume()

        if(Utils.isNetworkAvailable(requireContext())){
            (requireActivity() as MainActivity).clNoInternet?.visibility = View.GONE
        }else {
            (requireActivity() as MainActivity).clNoInternet?.visibility = View.VISIBLE
        }

        viewModel.getLista()
        binding.etBusqueda.setText("")
    }

    fun initRecyclerView(){
        adapter = BlogEntryAdapter()
        adapter.postClickListener = { view, post ->

            if(!post.idEntry.isNullOrEmpty()){

                var bundle = Bundle()
                bundle.putString("idPost",post.idEntry)
                findNavController().navigate(R.id.action_blogEntryListFragment_to_detallePostFragment,bundle)

            }

        }
        binding.rvPosts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPosts.adapter = adapter
    }

    fun initObservers(){

        viewModel.listaResponse.observe(viewLifecycleOwner){ response ->

            when (response) {

                is Resource.Loading -> {


                    Utils.loader(
                        requireActivity(),
                        (requireActivity() as MainActivity).loader,
                        true
                    )

                }

                is Resource.Success -> {

                    var posts = response.data as ArrayList<BlogEntry>

                    listaPosts.clear()
                    listaPosts.addAll(posts)

                    adapter.setItems(listaPosts)

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

}
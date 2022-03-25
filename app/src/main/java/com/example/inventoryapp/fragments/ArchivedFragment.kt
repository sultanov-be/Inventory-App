package com.example.inventoryapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.inventoryapp.R
import com.example.inventoryapp.application.MyApplication
import com.example.inventoryapp.databinding.FragmentArchiveBinding
import com.example.inventoryapp.model.Product
import com.example.inventoryapp.adapters.RecyclerAdapter
import com.example.inventoryapp.utils.RecyclerItemClickListener
import com.example.inventoryapp.utils.showToast
import com.example.inventoryapp.view_model.ArchiveViewModel
import com.example.inventoryapp.view_model.ArchiveViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior


class ArchivedFragment : Fragment(), RecyclerItemClickListener {

    private var _binding: FragmentArchiveBinding? = null
    private val binding
        get() = _binding!!
    private val recyclerAdapter by lazy { RecyclerAdapter(this) }
    private val viewModel by viewModels<ArchiveViewModel> { ArchiveViewModelFactory((requireActivity().application as MyApplication).repository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArchiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycler()
        setUpBottomSheet()
        setUpSearchView()

    }

    private fun setUpSearchView() {
        binding.search.apply {
            isSubmitButtonEnabled = true
            setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) {
                        observeData(query)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        observeData(newText)
                    }
                    return true
                }
            })
        }
    }

    private fun setUpBottomSheet() {
        with(binding.include) {
            textView1.text = resources.getText(R.string.restore)
            textView2.apply {
                text = resources.getText(R.string.delete)
                visibility = View.VISIBLE
            }
        }
    }

    private fun setUpRecycler() {
        binding.recycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = recyclerAdapter
        }
        viewModel.allArchiveProduct.observe(viewLifecycleOwner) {
            recyclerAdapter.setList(it)
        }
    }


    override fun onClick(product: Product) {

    }

    override fun dotClicked(product: Product) {
        with(binding.include) {
            val bottomSheet = BottomSheetBehavior.from(bottomSheet)
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED

            textView2.setOnClickListener {
                bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
                showAlertDialog(product)
            }

            textView1.setOnClickListener {
                bottomSheet.state = BottomSheetBehavior.STATE_HIDDEN
                viewModel.unArchiveData(product)
                "Успешно востановлено".showToast(requireContext())
            }
        }
    }

    private fun observeData(querySearch: String) {
        val searchQuery = "%$querySearch%"

        viewModel.getSearchArchiveProduct(searchQuery).observe(viewLifecycleOwner) {
            it.let {
                recyclerAdapter.setList(it)
            }
        }
    }

    private fun showAlertDialog(product: Product) {
        AlertDialog.Builder(requireContext())
            .setTitle("Удалить ${product.name} из архива?")
            .setPositiveButton(R.string.delete) { view, _ ->
                viewModel.deleteProduct(product.id)
                view.dismiss()
                "Успешно удалено".showToast(requireContext())
            }
            .setNegativeButton(R.string.cancel) { view, _ ->
                view.dismiss()
            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
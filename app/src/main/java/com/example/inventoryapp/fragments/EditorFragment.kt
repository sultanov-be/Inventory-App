package com.example.inventoryapp.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventoryapp.R
import com.example.inventoryapp.application.MyApplication
import com.example.inventoryapp.databinding.FragmentEditorBinding
import com.example.inventoryapp.model.Product
import com.example.inventoryapp.utils.loadImage
import com.example.inventoryapp.utils.showToast
import com.example.inventoryapp.view_model.EditorViewModel
import com.example.inventoryapp.view_model.EditorViewModelFactory

class EditorFragment : Fragment() {

    private val viewModel by viewModels<EditorViewModel> { EditorViewModelFactory((requireActivity().application as MyApplication).repository) }

    private var _binding: FragmentEditorBinding? = null
    private val binding
        get() = _binding!!

    private val args by navArgs<EditorFragmentArgs>()

    private var bitmap: Bitmap? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            if (bitmap != null) {
                binding.productImage.loadImage(bitmap)
                this.bitmap = bitmap
            }
        }

    private val requestSinglePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getContent.launch()
            } else {
                "Error".showToast(requireContext())
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditorBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpButtons()
        if (args.product != null) {
            setUpUi(args.product!!)

        }
        with(binding) {
            addButton.setOnClickListener {
                if (productName.text.isNotEmpty() && price.text.isNotEmpty() && quantity.text.isNotEmpty() && supplier.text.isNotEmpty() && bitmap != null) {
                    val name = productName.text.toString()
                    val price = price.text.toString().toInt()
                    val supplier = supplier.text.toString()
                    val quantity = quantity.text.toString().toInt()
                    if (args.product == null) {
                        viewModel.insertData(
                            Product(
                                0, name,
                                bitmap!!, price, quantity, false, supplier
                            )
                        )
                    } else {
                        val product = args.product!!
                        viewModel.updateData(
                            Product(
                                product.id, name,
                                bitmap!!, price, quantity, product.isArchive, supplier
                            )
                        )
                    }
                } else {
                    "Не все поля заполнены".showToast(requireContext())
                }
            }
        }
        viewModel.dataIsSaved.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    "Успешно сохранено".showToast(requireContext())
                    navigateUp()
                }
            }
        }
    }


    private fun setUpUi(product: Product) { // edit mode
        with(binding) {
            productName.setText(product.name)
            price.setText(product.price.toString())
            quantity.setText(product.quantity.toString())
            supplier.setText(product.sup)
            binding.addButton.text = resources.getString(R.string.save)
            toolbarTitle.text = resources.getString(R.string.edit)
            addButton.setOnClickListener {
                viewModel.updateData(product)
            }
            productImage.loadImage(product.image)
        }
    }

    private fun setUpButtons() {
        with(binding) {
            back.setOnClickListener { navigateUp() }
            cancelButton.setOnClickListener { navigateUp() }
            productImage.setOnClickListener { requestSinglePermissionLauncher.launch(android.Manifest.permission.CAMERA) }
        }
    }

    private fun navigateUp() {
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
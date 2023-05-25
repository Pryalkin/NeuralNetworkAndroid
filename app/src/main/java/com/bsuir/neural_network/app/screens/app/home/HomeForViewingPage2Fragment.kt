package com.bsuir.neural_network.app.screens.app.home

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager

import com.bsuir.neural_network.R
import com.bsuir.neural_network.app.dto.ImageAnswerDTO
import com.bsuir.neural_network.app.utils.observeEvent
import com.bsuir.neural_network.app.views.HomeViewModel
import com.bsuir.neural_network.databinding.FragmentHomeForViewingPage2Binding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import kotlin.properties.Delegates


class HomeForViewingPage2Fragment : Fragment(){

    private var pageNumber by Delegates.notNull<Int>()
    private lateinit var binding: FragmentHomeForViewingPage2Binding
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var myLauncher : ActivityResultLauncher<Intent>

    private var selectedImageUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val uri = data?.data
                uri?.let { uploadFile(it) }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = if (arguments != null) requireArguments().getInt("num") else 1
        validatePermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentHomeForViewingPage2Binding.inflate(inflater)

        when (pageNumber) {
            0 -> {
                customizeScreen(b = true, b1 = false)
                configureTheAdapterForTheImage()
            }
            1 -> {
                customizeScreen(b = false, b1 = true)
                binding.imageView.setOnClickListener {
                    openImageChooser()
                }
                binding.buttonUpload.setOnClickListener {
                    uploadFile(selectedImageUri)
                }
            }
        }
        observeShowMessageEvent()
        return binding.root
    }

    private fun validatePermission() {
        Dexter.withActivity(activity)
            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    Toast.makeText(requireActivity(), "Permission Granted", Toast.LENGTH_LONG).show()
                }
                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {
                    AlertDialog.Builder(requireActivity())
                        .setTitle(R.string.storage_permission_rationale_title)
                        .setMessage(R.string.storage_permission_rationale_message)
                        .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener {
                                dialogInterface, i ->
                            dialogInterface.dismiss()
                            token?.cancelPermissionRequest()
                        })
                        .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener {
                                dialogInterface, i ->
                            dialogInterface.dismiss()
                            token?.continuePermissionRequest()
                        })
                        .show()
                }
                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(requireActivity(), R.string.storage_permission_denied_message, Toast.LENGTH_LONG).show()
                }
            }
            ).check()
    }


    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png, image/jpg")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    selectedImageUri = data?.data
                    binding.imageView.setImageURI(selectedImageUri)
                }
            }
        }
    }

    private fun uploadFile(uri: Uri?) {
        val keywords = binding.etKeywords.text.toString()
        val file = File(getRealPathFromURI(uri))
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
        viewModel.upload(keywords, body)
    }

    private fun getRealPathFromURI(uri: Uri?): String? {
        var realPath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = uri?.let { context?.contentResolver?.query(it, projection, null, null, null) }
        if (cursor != null && cursor.moveToFirst()) {
            val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            realPath = cursor.getString(column_index)
            cursor.close()
        }
        return realPath
    }



    private fun configureTheAdapterForTheImage() {
        imageAdapter = ImageAdapter(object  : ImageActionListener{
            override fun onImageDetails(image: ImageAnswerDTO) {
                TODO("Not yet implemented")
            }

            override fun onImageSave(image: ImageAnswerDTO) {
                TODO("Not yet implemented")
            }

        })

        viewModel.images.observe(viewLifecycleOwner){
            imageAdapter.imageAnswerDTOs = it
        }

        val layoutManagerAnnouncement = LinearLayoutManager(context)
        binding.recyclerViewImage.layoutManager = layoutManagerAnnouncement
        binding.recyclerViewImage.adapter = imageAdapter

        val itemAnimatorAnnouncement = binding.recyclerViewImage.itemAnimator
        if (itemAnimatorAnnouncement is DefaultItemAnimator){
            itemAnimatorAnnouncement.supportsChangeAnimations = false
        }
        viewModel.getAllImages()
    }

    private fun observeShowMessageEvent() = viewModel.message.observeEvent(viewLifecycleOwner) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private fun customizeScreen(b: Boolean, b1: Boolean) {
        binding.apply {
            if (b) seeImages.visibility = View.VISIBLE
            else seeImages.visibility = View.GONE
            if (b1) createImage.visibility = View.VISIBLE
            else createImage.visibility = View.GONE
        }
    }

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
        @JvmStatic
        fun newInstance(page: Int): HomeForViewingPage2Fragment {
            val fragment: HomeForViewingPage2Fragment = HomeForViewingPage2Fragment()
            val args = Bundle()
            args.putInt("num", page)
            fragment.arguments = args
            return fragment
        }
    }

}


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
import com.bsuir.neural_network.app.dto.PersonAnswerDTO
import com.bsuir.neural_network.app.dto.utils.PersonDTO
import com.bsuir.neural_network.app.dto.utils.Role
import com.bsuir.neural_network.app.dto.utils.SampleApplicationDTO
import com.bsuir.neural_network.app.utils.observeEvent
import com.bsuir.neural_network.app.views.HomeViewModel
import com.bsuir.neural_network.databinding.FragmentHomeForViewingPage2Binding
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
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
    private lateinit var saAdapter: AppAdapter
    private lateinit var personAdapter: PersonAdapter
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
                if(viewModel.getRole() == Role.ROLE_ADMIN.name)
                    configureTheAdapterForPerson()
                else
                    configureTheAdapterForTheImage()
            }
            1 -> {
                customizeScreen(b = false, b1 = true)
                binding.imageView.setOnClickListener {
                    Toast.makeText(requireActivity(), "Кнопка нажата", Toast.LENGTH_SHORT).show()
                    openImageChooser()
                }
                binding.apply {
                    dtnSelectedDate.setOnClickListener {
                        val datePickerFragment = DatePickerFragment()
                        val supportFragmentManager = requireActivity().supportFragmentManager

                        supportFragmentManager.setFragmentResultListener(
                            "REQUEST_KEY",
                            viewLifecycleOwner
                        ) { resultKey, bundle ->
                            if (resultKey == "REQUEST_KEY") {
                                val date = bundle.getString("SELECTED_DATE")
                                dateOfBirth.text = date
                            }
                        }
                        datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
                    }
                }
                binding.btnSend.setOnClickListener {
                    binding.apply {
                        if(edName.text.toString() == ""){
                           Toast.makeText(requireActivity(), "Введите имя!", Toast.LENGTH_SHORT).show()
                        } else if(edSurname.text.toString() == ""){
                            Toast.makeText(requireActivity(), "Введите фамилию!", Toast.LENGTH_SHORT).show()
                        } else if(edPatronymic.text.toString() == ""){
                            Toast.makeText(requireActivity(), "Введите отчество!", Toast.LENGTH_SHORT).show()
                        } else if(edSeries.text.toString() == ""){
                            Toast.makeText(requireActivity(), "Введите серию паспорта!", Toast.LENGTH_SHORT).show()
                        } else if(edNumber.text.toString() == ""){
                            Toast.makeText(requireActivity(), "Введите номер паспорта!", Toast.LENGTH_SHORT).show()
                        } else if(selectedImageUri == null) {
                            Toast.makeText(requireActivity(), "Загрузите фотографию!", Toast.LENGTH_SHORT).show()
                        } else {
//                            val formatter = SimpleDateFormat("MM-dd-yyyy", Locale("Europe/Minsk"))
//                            val dateOfBirth = formatter.parse(dateOfBirth.text.toString())
                            val personDTO: PersonDTO = PersonDTO(
                                name = edName.text.toString(),
                                surname = edSurname.text.toString(),
                                patronymic = edPatronymic.text.toString(),
                                dateOfBirth = dateOfBirth.text.toString(),
                                passportSeries = edSeries.text.toString(),
                                passportNumber = edNumber.text.toString()
                            )
                            val body: MultipartBody.Part = uploadFile(selectedImageUri)
                            viewModel.personRegistration(personDTO, body, requireActivity())

                        }
                    }

                }
            }
        }
        observeShowMessageEvent()
        return binding.root
    }

    private fun configureTheAdapterForPerson() {
        personAdapter = PersonAdapter(object  : PersonActionListener{
            override fun add(personAnswerDTO: PersonAnswerDTO) {
                viewModel.add(personAnswerDTO.id)
            }
            override fun delete(personAnswerDTO: PersonAnswerDTO) {
                viewModel.delete(personAnswerDTO.id)
            }
        })

        viewModel.people.observe(viewLifecycleOwner){
            personAdapter.people = it
        }
        val layoutManagerAnnouncement = LinearLayoutManager(context)
        binding.recyclerViewApp.layoutManager = layoutManagerAnnouncement
        binding.recyclerViewApp.adapter = personAdapter
        val itemAnimatorAnnouncement = binding.recyclerViewApp.itemAnimator
        if (itemAnimatorAnnouncement is DefaultItemAnimator){
            itemAnimatorAnnouncement.supportsChangeAnimations = false
        }
        viewModel.getPeople()
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

    private fun uploadFile(uri: Uri?): MultipartBody.Part  {
        val file = File(getRealPathFromURI(uri))
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
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
        saAdapter = AppAdapter(object  : SAActionListener{
            override fun onDetails(sampleApplication: SampleApplicationDTO) {
                TODO("Not yet implemented")
            }
            override fun apply(sampleApplication: SampleApplicationDTO) {
                if(viewModel.getRole() == Role.ROLE_USER.name)
                    Toast.makeText(requireActivity(), "Зарегистрируйтесь!", Toast.LENGTH_SHORT).show()
                else
                    viewModel.applicationRegistration(sampleApplication.id)
            }
            override fun copyLink(sampleApplication: SampleApplicationDTO) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, sampleApplication.url)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }, viewModel.getRole())
        viewModel.sas.observe(viewLifecycleOwner){
            saAdapter.sampleApplications = it
        }
        val layoutManagerAnnouncement = LinearLayoutManager(context)
        binding.recyclerViewApp.layoutManager = layoutManagerAnnouncement
        binding.recyclerViewApp.adapter = saAdapter
        val itemAnimatorAnnouncement = binding.recyclerViewApp.itemAnimator
        if (itemAnimatorAnnouncement is DefaultItemAnimator){
            itemAnimatorAnnouncement.supportsChangeAnimations = false
        }
        viewModel.getAllSA()
    }

    private fun observeShowMessageEvent() = viewModel.message.observeEvent(viewLifecycleOwner) {
        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
    }

    private fun customizeScreen(b: Boolean, b1: Boolean) {
        binding.apply {
            if (b) seeApp.visibility = View.VISIBLE
            else seeApp.visibility = View.GONE
            if (b1) createPerson.visibility = View.VISIBLE
            else createPerson.visibility = View.GONE
        }
    }

//    private fun seeSpinnerForSearchScore() {
//        val result = if (viewModel.getRole() == Role.ROLE_USER.name) {
//            SearchScore.values().filter {it.getValue() < 8}.toTypedArray()
//        } else
//            SearchScore.values()
//        val adapter: ArrayAdapter<SearchScore> = ArrayAdapter(
//            requireContext(),
//            android.R.layout.simple_spinner_item,
//            result
//        )
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//    }

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


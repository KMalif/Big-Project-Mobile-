package com.plugin.bigproject.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.plugin.bigproject.R
import com.plugin.bigproject.contracts.CameraActivityContract
import com.plugin.bigproject.databinding.ActivityCameraBinding
import com.plugin.bigproject.presenters.CameraActivityPresenter
import com.plugin.bigproject.util.UploadRequestBody
import com.plugin.bigproject.util.getFileName
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class CameraActivity : AppCompatActivity(), CameraActivityContract.View {
    companion object {
        private val IMAGE_PICKCODE = 100
        private val PERMISSION_CODE = 1001
    }

    private var presenter : CameraActivityContract.Presenter? = null
    private var galleryImage : Uri ? = null
    private var choosedImage : com.esafirm.imagepicker.model.Image? = null
    private var image : MultipartBody.Part? = null

    private lateinit var binding: ActivityCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        presenter = CameraActivityPresenter(this)
//        btnCamera()
        btnGalery()
        btnBack()

        binding.BtnUpload.setOnClickListener {
            uploadImage()
        }
    }
    override fun onResume() {
        super.onResume()
        setUpDropdown()
    }

    private fun setUpDropdown(){
        val hairs = resources.getStringArray(R.array.hair)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, hairs)
        binding.EtHair.setAdapter(arrayAdapter)
    }

    private fun btnBack(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private val imagePickerLauncher = registerImagePicker {
        choosedImage = if(it.size == 0)  null else it[0]
        showImage()
    }

    private fun chooseImage(){
        val config = ImagePickerConfig{
            mode = ImagePickerMode.SINGLE
            isIncludeVideo = false
            isShowCamera = false
        }

        imagePickerLauncher.launch(config)
    }

    private fun showImage(){
        choosedImage?.let{
                image -> binding.ImageDetail.setImageURI(image.uri)
        }
    }

    private fun uploadImage(){
        if(choosedImage != null){
            var originalFile = File(choosedImage?.path!!)

            var imagePart : RequestBody = RequestBody.create(
                "image/*".toMediaTypeOrNull(),
                originalFile
            )

            image = MultipartBody.Part.createFormData(
                "foto",
                originalFile.name,
                imagePart
            )
        }

        val hair = RequestBody.create(MultipartBody.FORM, binding.EtHair.text.toString())

        presenter?.prediction(image!!, hair)

    }

    private fun btnGalery(){
        binding.BtnChooseImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions,PERMISSION_CODE)
                }
                else{ chooseImage() }

            } else{ chooseImage() }
        }
    }
//    private fun btnCamera(){
//        binding.BtnCamera.setOnClickListener {
//            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(i, 123)
//        }
//    }

//    private fun pickImageFromGallery(){
//        Intent(Intent.ACTION_PICK).also{
//            it.type = "image/*"
//            val mimeTypes = arrayOf("image/jpeg", "image/jpg", "image/png")
//            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
//            startActivityForResult(it, IMAGE_PICKCODE)
//        }
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICKCODE){
//            galleryImage = data?.data
//            binding.ImageDetail.setImageURI(galleryImage)
//            println("Image = $galleryImage")
//        }
//
//    }




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
           grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    chooseImage()
                }
                else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


//    private fun upload(){
//        binding.BtnUpload.setOnClickListener {
//            if (galleryImage == null){
//                showToast("Select an Image first")
//                return@setOnClickListener
//            }
//
//            val parcelDescriptor = contentResolver.openFileDescriptor(galleryImage!!, "r", null) ?: return@setOnClickListener
//            val file = File(cacheDir, contentResolver.getFileName(galleryImage!!))
//            val inputStream = FileInputStream(parcelDescriptor?.fileDescriptor)
//            val outputStream = FileOutputStream(file)
//            inputStream.copyTo(outputStream)
//            binding.loadingUpload.progress = 0
//            val body = UploadRequestBody(file, "image", this)
//            val image =  MultipartBody.Part.createFormData("image", file.name, body)
//            val hair = RequestBody.create(MultipartBody.FORM, binding.EtHair.text.toString())
//            val panjang = binding.EtHair.text.toString()
//            println("Panjang Rambutmu $panjang")
//            presenter?.prediction(image, hair)
//
//
//        }
//    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

//    override fun onProgressUpdate(percentage: Int) {
//        binding.loadingUpload.progress = percentage
//    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
}


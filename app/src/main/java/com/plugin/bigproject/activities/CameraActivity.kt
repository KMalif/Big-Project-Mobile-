package com.plugin.bigproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.plugin.bigproject.R
import com.plugin.bigproject.adapters.RecomendationAdapter
import com.plugin.bigproject.contracts.CameraActivityContract
import com.plugin.bigproject.databinding.ActivityCameraBinding
import com.plugin.bigproject.models.Recomendation
import com.plugin.bigproject.presenters.CameraActivityPresenter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class CameraActivity : AppCompatActivity(), CameraActivityContract.View {

    private var presenter : CameraActivityContract.Presenter? = null
    private var choosedImage : com.esafirm.imagepicker.model.Image? = null
    private var image : MultipartBody.Part? = null

    private lateinit var recomendationAdapter: RecomendationAdapter
    private lateinit var binding: ActivityCameraBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        presenter = CameraActivityPresenter(this)
        btnBack()

        binding.BtnChooseImage.setOnClickListener {
          chooseImage()
        }

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
            val originalFile = File(choosedImage?.path!!)

            val imagePart : RequestBody = RequestBody.create("image/*".toMediaTypeOrNull(), originalFile)

            image = MultipartBody.Part.createFormData("files", originalFile.name, imagePart)

        }
        val hair = RequestBody.create(MultipartBody.FORM, binding.EtHair.text.toString())
        presenter?.prediction(image!!, hair)
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loadingUpload.apply {
            isIndeterminate = true
            visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        binding.loadingUpload.apply {
            isIndeterminate = false
            progress = 0
            visibility = View.GONE
        }
    }

    override fun getRecomendation(recomendations: List<Recomendation>, faceShape : String) {
        println("Shape $faceShape Recomendations $recomendations ")
        hideInput()
        showRecomendation()
        binding.RVRecomendation.apply {
            recomendationAdapter = RecomendationAdapter(recomendations)
            val mlayoutManager = GridLayoutManager(this@CameraActivity, 2)
            mlayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = mlayoutManager
            adapter = recomendationAdapter
        }
        binding.TvShape.text = faceShape
    }

    private fun showRecomendation(){
        binding.apply {
            TvShape.visibility = View.VISIBLE
            TitleRecomendation.visibility = View.VISIBLE
            RVRecomendation.visibility = View.VISIBLE
        }
    }


    private fun hideInput(){
        binding.apply {
            BtnChooseImage.visibility = View.GONE
            EtHair.visibility = View.GONE
            InputLayout.visibility = View.GONE
            BtnUpload.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
}


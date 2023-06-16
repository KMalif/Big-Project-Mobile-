package com.plugin.bigproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.esafirm.imagepicker.features.ImagePickerConfig
import com.esafirm.imagepicker.features.ImagePickerMode
import com.esafirm.imagepicker.features.registerImagePicker
import com.plugin.bigproject.adapters.RecomendationAdapter
import com.plugin.bigproject.adapters.RecomendationListener
import com.plugin.bigproject.contracts.CameraActivityContract
import com.plugin.bigproject.databinding.ActivityCameraBinding
import com.plugin.bigproject.models.Recomendation
import com.plugin.bigproject.presenters.CameraActivityPresenter
import com.plugin.bigproject.util.Constants
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.Serializable

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
    }

    override fun onResume() {
        super.onResume()
        btnBackListener()
        btnChooseListener()
        btnUploadListener()
    }

    private fun btnUploadListener(){
        binding.BtnUpload.setOnClickListener {
            uploadImage()
        }
    }

    private fun btnChooseListener(){
        binding.BtnChooseImage.setOnClickListener {
            chooseImage()
        }
    }

    private fun btnBackListener(){
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private val imagePickerLauncher = registerImagePicker {
        choosedImage = if(it.isEmpty())  null else it[0]
        showImage()
    }

    private fun chooseImage(){
        val config = ImagePickerConfig{
            mode = ImagePickerMode.SINGLE
            isIncludeVideo = false

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
            val imagePart : RequestBody = originalFile.asRequestBody("image/*".toMediaTypeOrNull())
            image = MultipartBody.Part.createFormData("files", originalFile.name, imagePart)
        }
        val pendek = "pendek"
        val hair = pendek.toRequestBody(MultipartBody.FORM)
        val gender = Constants.getGender(this).toRequestBody(MultipartBody.FORM)
        val token = Constants.getToken(this)
        if(image == null ){
            showToast("Pilih gambar terlebih dahulu")
        }else{
            presenter?.prediction(token,image!!, hair,gender)
            binding.BtnUpload.apply {
                visibility = View.GONE
            }
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        binding.loadingUpload.apply {
            visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        binding.loadingUpload.apply {
            visibility = View.GONE
        }
    }

    override fun getRecomendation(recomendations: List<Recomendation>, faceShape : String, suggest : String) {
        println("Shape $faceShape Recomendations $recomendations ")
        hideInput()
        showRecomendation()
        binding.RVRecomendation.apply {
            recomendationAdapter = RecomendationAdapter(recomendations, object : RecomendationListener{
                override fun onRecomendationClick(recomendation: Recomendation) {
                    startActivity(Intent(this@CameraActivity, DetailRekomendationActivity::class.java).apply {
                        putExtra("recomendation", recomendation as Serializable)
                    })
                }
            })
            val mlayoutManager = GridLayoutManager(this@CameraActivity, 2)
            mlayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = mlayoutManager
            adapter = recomendationAdapter
        }
        binding.TvShape.text = faceShape
        binding.TvSuggest.text = suggest
    }

    private fun showRecomendation(){
        binding.apply {
            TvShape.visibility = View.VISIBLE
            TvSuggest.visibility = View.VISIBLE
            TitleRecomendation.visibility = View.VISIBLE
            RVRecomendation.visibility = View.VISIBLE
        }
    }


    private fun hideInput(){
        binding.apply {
            BtnChooseImage.visibility = View.GONE
            BtnUpload.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }
}


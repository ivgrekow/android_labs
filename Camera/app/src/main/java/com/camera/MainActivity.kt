package com.camera

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider.getUriForFile
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

class MainActivity : AppCompatActivity() {
    private lateinit var outputFileUri: Uri
    private var picUri: Uri? = null
    private lateinit var imageView: ImageView
    private lateinit var takePictureLauncher: ActivityResultLauncher<Intent>
    private lateinit var cropPictureLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val packageManager = packageManager
        val isCamera = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)
        println(isCamera)

        /*val intent = Intent()
        intent.setAction(Intent.ACTION_CAMERA_BUTTON)
        intent.putExtra(Intent.EXTRA_KEY_EVENT, KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_CAMERA))
        sendOrderedBroadcast(intent, null)*/

        /*val intent = intent
        intent.setComponent(null)
        intent.setPackage("com.android.camera")
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)*/

        imageView = findViewById(R.id.photo)
        val button: Button = findViewById(R.id.button_shot)

        takePictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                picUri = outputFileUri
                performCrop()
            }
        }

        cropPictureLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                imageView.setImageURI(picUri)
            }
        }

        button.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val file = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "test.jpg")
            outputFileUri = getUriForFile(this, "com.camera.fileprovider", file)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)

            try {
                takePictureLauncher.launch(takePictureIntent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun performCrop() {
        try {
            val cropIntent = Intent("com.android.camera.action.CROP")
            cropIntent.setDataAndType(picUri, "image/*")
            cropIntent.putExtra("crop", "true")
            cropIntent.putExtra("aspectX", 1)
            cropIntent.putExtra("aspectY", 1)
            cropIntent.putExtra("outputX", 256)
            cropIntent.putExtra("outputY", 256)
            cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, picUri)
            cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            cropIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            cropIntent.putExtra("outputFormat", "JPEG")
            cropPictureLauncher.launch(cropIntent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "Ваше устройство не поддерживает кадрирование", Toast.LENGTH_SHORT).show()
        }
    }
}
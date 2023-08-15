package com.example.vrapplication

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.vr.sdk.widgets.pano.VrPanoramaView

class MainActivity : AppCompatActivity() {

    val DEMO_PANORAMA_LINK = "http://reznik.lt/wp-content/uploads/2017/09/preview3000.jpg"

    val vrPanoramaView: VrPanoramaView by lazy { findViewById(R.id.vrPanoramaView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val option = VrPanoramaView.Options().also {
            it.inputType = VrPanoramaView.Options.TYPE_MONO
        }


        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.img_1)
        vrPanoramaView.loadImageFromBitmap(bitmap, option)

        Glide.with(this).asBitmap().load(DEMO_PANORAMA_LINK).into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {}
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                // "option": Declared at the step 3
                Toast.makeText(this@MainActivity, "onResourceReady.", Toast.LENGTH_SHORT).show()

                //vrPanoramaView.loadImageFromBitmap(resource, option)
            }

        })

        val isGyroscopeExist = packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_GYROSCOPE)
        if (!isGyroscopeExist) {
            // Error handling
            Toast.makeText(this, "There is no built-in gyroscope. <VrPanoramaView> cannot function properly.", Toast.LENGTH_LONG).show()
        }

    }

    override fun onResume() {
        super.onResume()
        val baseFrameLayout = vrPanoramaView[0] as ViewGroup

        // For "Exit fullscreen" button and "About VR View" button
        val relativeLayout = baseFrameLayout.getChildAt(1) as ViewGroup
        val exitFullscreenImageButton = relativeLayout.getChildAt(0)
        val aboutVRViewImageButton = relativeLayout.getChildAt(1)

        // For "Cardboard" button and "Enter fullscreen" button
        val linearLayout = relativeLayout.getChildAt(2) as ViewGroup
        val cardboardImageButton = linearLayout.getChildAt(0)
        val enterFullscreenImageButton = linearLayout.getChildAt(1)

        exitFullscreenImageButton.visibility = View.GONE
        aboutVRViewImageButton.visibility = View.GONE
        //cardboardImageButton.visibility = View.GONE
        enterFullscreenImageButton.visibility = View.GONE
    }
}
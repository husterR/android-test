package com.android.example.android_test_app

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navHeaderContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.navigationView)
        navHeaderContainer = navView.getHeaderView(0) as LinearLayout
        Glide.with(this)
            .load("https://i.picsum.photos/id/704/400/200.jpg?hmac=7l5nWE3j1sr6X5mZUpZDaQ6aRIPuMypV0DvsHuDku2U")
            .into(object : CustomTarget<Drawable>() {
                override fun onLoadCleared(placeholder: Drawable?) {}
                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    navHeaderContainer.background = resource
                }
            })
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun onNavToMenuItem(item: MenuItem) {
        when (item.title){
            getString(R.string.menu_webview) -> startActivity(Intent(this, WebViewActivity::class.java))
            getString(R.string.menu_gallery) -> startActivity(Intent(this, GalleryActivity::class.java))
            getString(R.string.menu_video) -> startActivity(Intent(this, VideoActivity::class.java))
        }

    }
}


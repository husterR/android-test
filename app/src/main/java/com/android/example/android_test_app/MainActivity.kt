package com.android.example.android_test_app

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.text_list_item.view.*


class MainActivity : AppCompatActivity() {

    private lateinit var navHeaderContainer: LinearLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setUpViews()
    }

    private fun setUpViews() {
        val navView: NavigationView = findViewById(R.id.navigationView)
        navHeaderContainer = navView.getHeaderView(0) as LinearLayout
        drawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerOpened(drawerView: View) {
                Glide.with(drawerView.context)
                    .load("https://cdn.fluidmobile.de/wp-content/uploads/2019/09/roman_header-1.jpg.webp")
                    .into(object : CustomTarget<Drawable>() {
                        override fun onLoadCleared(placeholder: Drawable?) {}
                        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                            navHeaderContainer.background = resource
                        }
                    })
            }
        })
        recyclerView = findViewById(R.id.main_content)
        recyclerView.adapter = TextListAdapter(List(1000){ index -> "Item $index"})
        val actionBarToggleButton = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(actionBarToggleButton)
        actionBarToggleButton.isDrawerIndicatorEnabled = true
        actionBarToggleButton.syncState()
    }

    fun onNavToMenuItem(item: MenuItem) {
        when (item.title) {
            getString(R.string.menu_webview) -> startActivity(Intent(this, WebViewActivity::class.java))
            getString(R.string.menu_video) -> startActivity(Intent(this, VideoActivity::class.java))
            getString(R.string.menu_text) -> startActivity(Intent(this, TextInflaterActivity::class.java))
        }

    }
}
class TextListAdapter(private val data: List<String> = mutableListOf()) : RecyclerView.Adapter<TextListAdapter.TextListViewHolder>() {

    private fun getItem(position: Int): String{
        return data[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextListViewHolder {
        return TextListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.text_list_item, parent , false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: TextListViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    class TextListViewHolder(private val containerView: View) : RecyclerView.ViewHolder(containerView) {

        @SuppressLint("SetTextI18n")
        fun bind(itemHeader: String, position: Int) {
            containerView.text_list_item__icon.setImageResource(R.drawable.android)
            containerView.text_list_item__header.text = itemHeader
            containerView.text_list_item_description.text = "Subtitle to Item #$position\nand its description"
            containerView.text_list_item__end_icon.setImageResource(R.drawable.flutter)
        }
    }
}


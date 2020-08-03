package com.android.example.android_test_app

import android.Manifest
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.android.example.android_test_app.ui.gallery.GalleryViewModel
import kotlinx.android.synthetic.main.gallery_recycler_list_item.view.*
import permissions.dispatcher.*
import java.io.File

@RuntimePermissions
class GalleryActivity : AppCompatActivity() {
    private lateinit var galleryRecyclerView: RecyclerView
    private lateinit var galleryListAdapter: GalleryListAdapter
    private lateinit var viewModel: GalleryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        viewModel= GalleryViewModel(this.application)
        val toolbar: Toolbar = findViewById(R.id.activity_gallery__toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        galleryRecyclerView = findViewById(R.id.activity_gallery__recycler_view)
        galleryListAdapter = GalleryListAdapter()
        galleryRecyclerView.adapter = galleryListAdapter
        getAllImagePathsWithPermissionCheck()
        setUpObserver()
    }

    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun getAllImagePaths(){
        val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
        val orderBy = MediaStore.Images.Media._ID
        val cursor: Cursor = application.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
            null, orderBy
        )!!
        val count: Int = cursor.count

        val list : ArrayList<String> = ArrayList()

        for (i in 0 until count) {
            cursor.moveToPosition(i)
            val dataColumnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            list.add(cursor.getString(dataColumnIndex))
        }
        cursor.close()
        galleryListAdapter.setData(list)
    }

    private fun setUpObserver() {
        viewModel.text.observe(this, Observer {
            galleryListAdapter.setData(it)
            galleryListAdapter.notifyDataSetChanged()
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated function
        onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    @OnShowRationale(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun showRationaleForCamera(request: PermissionRequest) {
    }

    @OnPermissionDenied(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun onCameraDenied() {
    }

    @OnNeverAskAgain(Manifest.permission.READ_EXTERNAL_STORAGE)
    fun onCameraNeverAskAgain() {
    }
}

class GalleryListAdapter(private var data: List<String> = mutableListOf()) : RecyclerView.Adapter<GalleryListAdapter.GalleryListViewHolder>() {

    fun setData(list: List<String>) {
        data = list
        notifyDataSetChanged()
    }

    fun getItem(position: Int): String{
        return data[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryListViewHolder {
        return GalleryListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.gallery_recycler_list_item, parent , false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: GalleryListViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    class GalleryListViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {

        fun bind(imagePath: String, position: Int) {
            containerView.gallery_recycler_list_item__image_view.setImageURI(Uri.fromFile(File(imagePath)))
            containerView.gallery_recycler_list_item__text.text = "This is #$position Picture"
        }
    }

}

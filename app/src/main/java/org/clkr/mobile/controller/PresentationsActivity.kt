package org.clkr.mobile.controller

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_PREFIX_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import org.clkr.mobile.R
import org.clkr.mobile.model.Presentation
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.List
import java.util.stream.Collectors


class PresentationsActivity : AppCompatActivity() {
    private var presentationsListView: ListView? = null
    private var presentationAdapter: PresentationAdapter? = null
    private val presentations = listOf(Presentation()) //new ArrayList<>();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportActionBar != null) {
            supportActionBar!!.title = "Presentations"
        }
        presentationsListView = findViewById(R.id.presentationsListView)
        presentationsListView?.emptyView = findViewById(android.R.id.empty)
        presentationAdapter = PresentationAdapter(this, presentations)
        presentationsListView?.adapter = presentationAdapter
    }

    override fun onStart() {
        super.onStart()
    }

}
package com.nitish.epass

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.nitish.epass.databinding.ActivityMainBinding
import com.nitish.epass.databinding.SecurityloginformlayoutBinding
import com.nitish.epass.databinding.StudentloginformlayoutBinding
import com.nitish.epass.databinding.TeacherloginformBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var teacherloginformBinding: TeacherloginformBinding
    lateinit var securityloginformlayoutBinding: SecurityloginformlayoutBinding
    lateinit var studentloginformlayoutBinding: StudentloginformlayoutBinding
    lateinit var teacheralertdialog:AlertDialog
    lateinit var securityalertdialog:AlertDialog
    lateinit var studentalertdialog:AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //BINDING FDR MAIN ACTIVITY
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)
        //BINDING FOR TEACHER LOGIN FORM
        teacherloginformBinding= TeacherloginformBinding.inflate(layoutInflater)
        //BINDING FOR SECURITY LOGIN FORM
        securityloginformlayoutBinding= SecurityloginformlayoutBinding.inflate(layoutInflater)
        //BINDING FOR STUDENT LOGIN FORM
        studentloginformlayoutBinding= StudentloginformlayoutBinding.inflate(layoutInflater)

        //ALERTDIALOG BUILDER FOR TEACHER LOGIN FORM
        teacheralertdialog=AlertDialog.Builder(this).create()
        teacheralertdialog.setView(teacherloginformBinding.root)
        //ALERTDIALOG BUILDER FOR SECURITY LOGIN FORM
        securityalertdialog=AlertDialog.Builder(this).create()
        securityalertdialog.setView(securityloginformlayoutBinding.root)
        //ALERTDIALOG BUILDER FOR STUDENT LOGIN FORM
        studentalertdialog=AlertDialog.Builder(this).create()
        studentalertdialog.setView(studentloginformlayoutBinding.root)


        //ON CLICK TEACHER LOGIN BUTTON
        binding.teacherloginbtn.setOnClickListener {
            teacheralertdialog.show()
        }
        //ON CLICK SECURITY LOGIN BUTTON
        binding.securityloginbtn.setOnClickListener {
            securityalertdialog.show()
        }
        //ON CLICK STUDFENT LOGIN BUTTON
        binding.studentlogin.setOnClickListener {
            studentalertdialog.show()
        }
        //HIDE ACTION BAR
        supportActionBar?.hide()
        //SANCKBAR FOR NO INTERNET CONNECTION
        val constraintLayout=binding.constraintlayout
        val snackbar= Snackbar.make(constraintLayout, "No internet connection !! ", Snackbar.LENGTH_LONG)
        //CHECK INTERNET CONNECTION
        if (checkForInternet(this)) {

        } else {
           snackbar.show()
        }
    }
    //FUNCTION FOR CHECK INTERNET CONNECTION
    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }
}
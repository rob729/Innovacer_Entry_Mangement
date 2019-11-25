package com.robin.entry


import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Check In"
        loadFragment(CheckInFragment())

        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.checkin -> {
                    loadFragment(CheckInFragment())
                    supportActionBar?.title = "Check In"
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.checkout -> {
                    loadFragment(CheckOutFragment())
                    supportActionBar?.title = "Check Out"
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) { // load fragment
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.commit()
    }
}

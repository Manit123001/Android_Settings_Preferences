package com.mcnewz.sample.androidsettingsapplication

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.mcnewz.sample.androidsettingsapplication.PreferenceHelper.get
import com.mcnewz.sample.androidsettingsapplication.PreferenceHelper.set
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.layout_settings_toolbar.*

class MainActivity : AppCompatActivity(), SettingsFragment.ItemClick {
    private val TAG = "MainActivityx"
    private var settingsFragment: SettingsFragment? = null
    private var accountFragment: AccountFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PreferenceManager.setDefaultValues(this, R.xml.pref_settings, false)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        showDefaultSettings()
        accountSetting()

    }

    private fun accountSetting() {
        val prefs: SharedPreferences = PreferenceHelper.defaultPrefs(this)
        // get Value
        val name: String? = prefs[PREFERENCES_PROFILE_NAME, ""]
        val job: String? = prefs[PREFERENCES_PROFILE_JOB, ""]
        val tel: String? = prefs[PREFERENCES_PROFILE_TEL, ""]
        tv_profile.text = "Profile: \n" +
                "$name\n" +
                "$job\n" +
                "$tel"
    }

    private fun showDefaultSettings() {
        val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val gallery = prefs.getString(getString(R.string.key_gallery_name), "Local")
        mLog(gallery)
        val upload = prefs.getBoolean(getString(R.string.key_upload_over_wifi), true)
        mLog(upload.toString())
        val backup = prefs.getString(getString(R.string.key_backup_frequency), "1")
        mLog(upload.toString())
        val notification = prefs.getBoolean(getString(R.string.key_notifications_new_message), true)
        mLog(upload.toString())
        val vibrate = prefs.getBoolean(getString(R.string.key_vibrate), true)
        mLog(upload.toString())
        tv_gallery.text = "Setting: \n" +
                "Default Store: $gallery \n" +
                "Auto Upload: $upload \n" +
                "Bakeup Frequency: $backup \n" +
                "Notification: $notification \n" +
                "Vibrate: $vibrate \n" +
                ""
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mToast("onBackPressed")
        showDefaultSettings()
        accountSetting()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu;
        menuInflater.inflate(R.menu.items, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.action_settings -> {
                inflateSettingsFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun inflateSettingsFragment() {
        if (settingsFragment == null) {
            settingsFragment = SettingsFragment()
        }
        val transaction: android.app.FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.settings_container, settingsFragment, "Settings Fragment")
        transaction.addToBackStack("Settings Fragment")
        transaction.commit()
    }

    override fun showAccountActivity() {
        if (accountFragment == null) {
            accountFragment = AccountFragment()
        }
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.settings_container, accountFragment, "Account Fragment")
        transaction.addToBackStack("Account Fragment")
        transaction.commit()
    }

    private fun mLog(s: String) {
        Log.d(TAG, s)
    }

    private fun mToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }
}

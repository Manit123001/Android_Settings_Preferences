package com.mcnewz.sample.androidsettingsapplication

import android.content.Context
import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.widget.Toast
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.*

class SettingsFragment : PreferenceFragment(),
        Preference.OnPreferenceChangeListener,
        Preference.OnPreferenceClickListener {
    private val TAG = "SettingsFragment"
    private var iItems: ItemClick? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.pref_settings)
        // update screen
        val sp = preferenceScreen.sharedPreferences

        // set Preference Click Listeners
        val accountPreference: Preference = findPreference(getString(R.string.key_account_settings))
        accountPreference.onPreferenceClickListener = this

        // set Preference Change Listeners
        val galleryNamePreference: Preference = preferenceManager.findPreference(getString(R.string.key_gallery_name))
        galleryNamePreference.onPreferenceChangeListener = this
        galleryNamePreference.summary = sp.getString(getString(R.string.key_gallery_name), "")
        val uploadWifiPreference: Preference = preferenceManager.findPreference(getString(R.string.key_upload_over_wifi))
        uploadWifiPreference.onPreferenceChangeListener = this
        val notificationsNewMessagePreference: Preference = preferenceManager.findPreference(getString(R.string.key_notifications_new_message))
        notificationsNewMessagePreference.onPreferenceChangeListener = this
        val notificationsRingtonePreference: Preference = preferenceManager.findPreference(getString(R.string.key_notifications_new_message_ringtone))
        notificationsRingtonePreference.onPreferenceChangeListener = this
        val vibratePreference: Preference = preferenceManager.findPreference(getString(R.string.key_vibrate))
        vibratePreference.onPreferenceChangeListener = this
        val backupFrequencyPreference: Preference = preferenceManager.findPreference(getString(R.string.key_backup_frequency))
        backupFrequencyPreference.onPreferenceChangeListener = this
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        mToast(newValue.toString())
        // set text summary change ...
        if (preference is EditTextPreference) {
            if (preference.key == getString(R.string.key_gallery_name)) {
                preference.summary = newValue.toString()
            }
        }
        // check key to upload to server ...
        when (preference!!.key) {
            getString(R.string.key_gallery_name) -> updatePreferenceSettingSuccess(getString(R.string.key_gallery_name))
            getString(R.string.key_upload_over_wifi) -> updatePreferenceSettingSuccess(getString(R.string.key_upload_over_wifi))
            getString(R.string.key_notifications_new_message) -> updatePreferenceSettingSuccess(getString(R.string.key_notifications_new_message))
            getString(R.string.key_notifications_new_message_ringtone) -> updatePreferenceSettingSuccess(getString(R.string.key_notifications_new_message_ringtone))
            getString(R.string.key_vibrate) -> updatePreferenceSettingSuccess(getString(R.string.key_vibrate))
            getString(R.string.key_backup_frequency) -> updatePreferenceSettingSuccess(getString(R.string.key_backup_frequency))
        }
        return true
    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        if (preference!!.key == getString(R.string.key_account_settings)) {
            iItems!!.showAccountActivity()
        }
        return true
    }

    private fun updatePreferenceSettingSuccess(key: String?) {
        // updates to server or anythings here
        mLog("updatePreferenceSettingSuccess $key")
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        val menuItem: MenuItem = menu!!.findItem(R.id.action_settings)
        menuItem.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = super.onCreateView(inflater, container, savedInstanceState)
        view.setBackgroundColor(ContextCompat.getColor(activity, R.color.white))
        setHasOptionsMenu(true)
        return view
    }

    private fun mLog(s: String) {
        Log.d(TAG, s)
    }

    private fun mToast(s: String) {
        Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        iItems = (context as ItemClick)
    }

    interface ItemClick {
        fun showAccountActivity()
    }
}
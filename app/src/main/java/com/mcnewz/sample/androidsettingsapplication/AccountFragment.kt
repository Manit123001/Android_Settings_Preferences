package com.mcnewz.sample.androidsettingsapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_account.*
import com.mcnewz.sample.androidsettingsapplication.PreferenceHelper.set
import com.mcnewz.sample.androidsettingsapplication.PreferenceHelper.get

class AccountFragment : Fragment() {
    val TAG = "ACCountFragment"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val prefs: SharedPreferences = PreferenceHelper.defaultPrefs(activity!!)

        btn_save.setOnClickListener {
            view.hideKeyboard()
            // when I'dont know preference Helper
//            val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//            val editor: SharedPreferences.Editor = prefs.edit()
//            if (!edt_name.text.toString().equals("")) {
//                val name: String? = edt_name.text.toString()
//                printToLog("saving name: " + name)
//                editor.putString(PREFERENCES_NAME, name)
//                editor.apply()
//            }

            // Easy to use it's Good solution
            // set Value
            // prefs.set(PREFERENCES_PROFILE_NAME, edt_name.text.toString())
            prefs[PREFERENCES_PROFILE_NAME] = edt_name.text.toString()
            prefs[PREFERENCES_PROFILE_JOB] = edt_job.text.toString()
            prefs[PREFERENCES_PROFILE_TEL] = edt_tel.text.toString()

            // get Value
            val name: String? = prefs[PREFERENCES_PROFILE_NAME, ""]
            val job: String? = prefs[PREFERENCES_PROFILE_JOB, ""]
            val tel: String? = prefs[PREFERENCES_PROFILE_TEL, ""]

            tv_account.text = "$name\n" +
                    "$job\n" +
                    "$tel"
            mToast("$name $job $tel")
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        val menuItem: MenuItem = menu!!.findItem(R.id.action_settings)
        menuItem.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    private fun mLog(s: String) {
        Log.d(TAG, s)
    }

    private fun mToast(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show()
    }

    fun View.hideKeyboard() {
        mLog("closing keyboard")
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
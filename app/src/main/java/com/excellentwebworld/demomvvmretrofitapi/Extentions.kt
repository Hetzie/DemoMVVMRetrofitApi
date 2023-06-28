package com.excellentwebworld.demomvvmretrofitapi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import okhttp3.MediaType
import okhttp3.RequestBody

fun Context.showToast(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
fun Context.dialog(
    title: String,
    msg: String?=null,
    positiveText : String,
    negativeText : String,
    positiveClick : (()->Unit)? = null,
    negativeClick : (()->Unit)? = null,
    dialogView : View? = null
){
    val builder = AlertDialog.Builder(this)
    builder.setView(dialogView)

    builder.setView(dialogView)
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton(positiveText)
        {_,_-> positiveClick?.invoke() }
        .setNegativeButton(negativeText)
        {_,_-> negativeClick?.invoke() }
        .create()
        .show()
}

fun <T> Context.intent(activity: Class<T>, extraName: String?=null, extraMsg: String?=null){
    val intent = Intent(this, activity)
    intent.putExtra(extraName, extraMsg)
    startActivity(intent)
}

fun View.showSnackBar(
    message: String,
    f:(()->Unit)? = null
){
    val sB = Snackbar.make(this, message, Snackbar.LENGTH_LONG).setAction("label") { f }
    sB.show()
}

fun Context.showKeyBoard(view: View){
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
}

fun Context.hideKeyBoard(view: View){
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0);
}

fun createPartFromString(stringData: String): RequestBody {
    return RequestBody.create(MediaType.parse("text/plain"), stringData)
}
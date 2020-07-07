package com.nao749.simpletodoapp

import android.content.Context
import android.widget.Toast

fun makeToast(context:Context,message:String){

    Toast.makeText(context,message,Toast.LENGTH_SHORT).show()

}
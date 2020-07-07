package com.nao749.simpletodoapp

import android.app.Application
import io.realm.Realm

class MyFirstApplication:Application() {


    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

    }


}
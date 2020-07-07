package com.nao749.simpletodoapp

import io.realm.RealmObject

open class TodoDB : RealmObject(){

    //todoの内容
    var todoTask : String = ""

}
package com.nao749.simpletodoapp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() ,MasterFragment.OnListFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            goEditScreen("",ModeInEdit.NEW)
        }

        //MasterFragmentの呼び出し
        supportFragmentManager.beginTransaction()
            .add(R.id.container_master,MasterFragment.newInstance(1)).commit()


    }

    //EditActivityから戻ってきて画面の更新
    override fun onResume() {
        super.onResume()

        upDateTodoList()

    }

    //画面更新時のフラグメントの遷移
    private fun upDateTodoList() {

        supportFragmentManager.beginTransaction()
            .replace(R.id.container_master,MasterFragment.newInstance(1)).commit()

    }



    private fun goEditScreen(todo:String,mode:ModeInEdit) {
        val intent = Intent(this@MainActivity,EditActivity::class.java).apply {
            putExtra(IntentKey.TODO.name,todo)
            putExtra(IntentKey.MODE.name,mode)
        }
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        menu.apply {

            findItem(R.id.menu_delete).isVisible = false
            findItem(R.id.menu_done).isVisible = false

        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        when (item.itemId) {
            R.id.action_settings -> true

            R.id.menu_delete -> {
                //メニューを削除しようとしたとき


            }

            else -> super.onOptionsItemSelected(item)
        }
        return  true
    }

    override fun onListItemClick(item: TodoDB) {
        goEditScreen(item.todoTask,ModeInEdit.EDIT)
    }
}

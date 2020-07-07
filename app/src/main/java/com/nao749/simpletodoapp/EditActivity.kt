package com.nao749.simpletodoapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() ,EditFragment.OnFragmentInteractionListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setSupportActionBar(toolbar)

        toolbar.apply {
            setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
            setNavigationOnClickListener {
                finish()
            }
        }

        val bundle = intent.extras!!
        val todo = bundle.getString(IntentKey.TODO.name)!!
        val mode = bundle.getSerializable(IntentKey.MODE.name) as ModeInEdit

        supportFragmentManager.beginTransaction()
            .add(R.id.container_Edit,EditFragment.newInstance(todo,mode)).commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        menu!!.apply {
            findItem(R.id.menu_delete).isVisible = false
            findItem(R.id.menu_done).isVisible = false
        }
        return true
    }


    //EditFragmentから戻ってきたよ
    override fun onDataEditActivity() {
        finish()
    }

}

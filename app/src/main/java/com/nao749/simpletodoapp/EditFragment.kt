package com.nao749.simpletodoapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_edit.*
import java.lang.RuntimeException


private  val ARG_TODO = IntentKey.TODO.name
private  val ARG_MODE = IntentKey.MODE.name


class EditFragment : Fragment() {

    private var todo : String = ""
    private var mode : ModeInEdit? = null

    private var mListener : OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            todo = requireArguments().getString(ARG_TODO)!!
            mode = requireArguments().getSerializable(ARG_MODE) as ModeInEdit
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_edit, container, false)

        setHasOptionsMenu(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        updateUI(mode!!)//画面の更新
    }

    private fun updateUI(mode: ModeInEdit) {
        when(mode){
            ModeInEdit.NEW -> {
                textInputLayout.setText("")
            }
            ModeInEdit.EDIT -> {
                textInputLayout.setText(todo)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        //menuItemはactivityとmenuItem内の両方に設置しておかないといけない

        menu!!.apply {
            findItem(R.id.menu_done).isVisible = true
            findItem(R.id.menu_delete).isVisible = false
        }
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //データベースへの登録

        if (item.itemId == R.id.menu_done){

            recordDB(mode!!)

        }

        return super.onOptionsItemSelected(item)

    }

    private fun recordDB(mode: ModeInEdit) {

        val isRequiredItemsFilled = isRequiredFilledCheck()

        //入力しているかどうかのチェック
        if(!isRequiredItemsFilled) return

        when(mode){

            ModeInEdit.NEW -> addNewTodo()

            ModeInEdit.EDIT -> editTodo()

        }


        mListener!!.onDataEditActivity()

        //fragmentManagerは非推奨になってます。
        parentFragmentManager.beginTransaction().remove(this).commit()

    }

    private fun isRequiredFilledCheck(): Boolean {

        if(textInputLayout.text.toString() == ""){

            textInputLayout.error = getString(R.string.error)

            return false
        }

        return true

    }

    private fun editTodo() {
        //DBの更新処理

        val realm = Realm.getDefaultInstance()

        //一行だけとってくるためFindFirstを使用
        val selectedTodo = realm.where(TodoDB::class.java).equalTo(TodoDB::todoTask.name,todo).findFirst()

        realm.beginTransaction()

        //とってきたデータの更新
        selectedTodo!!.apply {
            todoTask = textInputLayout.text.toString()
        }

        realm.commitTransaction()

        realm.close()



    }


    //新規追加
    private fun addNewTodo() {

        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val newTodo = realm.createObject(TodoDB::class.java)
        newTodo.apply {
            todoTask = textInputLayout.text.toString()
        }

        realm.commitTransaction()

        realm.close()
    }

    //Fragment生成時はonAttach,onDetachをともに記載すること
    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnFragmentInteractionListener){
            mListener = context
        }else{
            throw RuntimeException(context.toString() + "must implement OnFragmentListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface OnFragmentInteractionListener{

        fun onDataEditActivity()

    }

    companion object {

        @JvmStatic
        fun newInstance(todo: String, mode: ModeInEdit) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TODO, todo)
                    putSerializable(ARG_MODE, mode)
                }
            }
    }
}

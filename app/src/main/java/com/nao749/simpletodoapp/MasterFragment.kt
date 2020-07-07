package com.nao749.simpletodoapp

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.realm.Realm


class MasterFragment : Fragment() {
    private var columnCount = 1

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    //LayoutManagerのセット
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_master_list, container, false)

        setHasOptionsMenu(true)

        // Set the adapter
        if (view is RecyclerView) {

            //with関数
            with(view) {
                layoutManager = when {

                    //表示する列が一列の場合はLinearLayoutManager
                    columnCount <= 1 -> LinearLayoutManager(context)

                    //二列以上の場合はGridLayoutManger
                    else -> GridLayoutManager(context, columnCount)
                }

                val realm = Realm.getDefaultInstance()
                val result = realm.where(TodoDB::class.java).findAll()



                //viewにアダプターのセットをしている
                adapter = MyMasterRecyclerViewAdapter(result , listener)
            }
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        super.onCreateOptionsMenu(menu, inflater)

        menu.apply {

            findItem(R.id.menu_done).isVisible = false
            findItem(R.id.menu_delete).isVisible = true

        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if(item.itemId == R.id.menu_delete){
            deleteTodo()
        }

        return super.onOptionsItemSelected(item)

    }


    private fun deleteTodo() {
        //Todo 削除
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnListFragmentInteractionListener {

        fun onListItemClick(item: TodoDB)
    }

    companion object {


        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            MasterFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}

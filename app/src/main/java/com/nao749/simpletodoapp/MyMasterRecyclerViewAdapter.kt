package com.nao749.simpletodoapp

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView


import com.nao749.simpletodoapp.MasterFragment.OnListFragmentInteractionListener
import io.realm.RealmResults

import kotlinx.android.synthetic.main.fragment_master.view.*


class MyMasterRecyclerViewAdapter(
    private val mValues: RealmResults<TodoDB>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyMasterRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as TodoDB
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListItemClick(item)
        }
    }

    //viewHolderの作成
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_master, parent, false)
        return ViewHolder(view)


    }


    //生成したviewHolderを張り付ける
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.textTodo.text = mValues[position]!!.todoTask
        holder.checkBox


        //RecyclerViewのクリックリスナー
        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    //アダプターが保持している行数
    override fun getItemCount(): Int = mValues.size


    //リスト一行分のviewHolderClassの作成
    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val textTodo : TextView  = mView.textTodo
        val checkBox : CheckBox = mView.checkBox
    }
}

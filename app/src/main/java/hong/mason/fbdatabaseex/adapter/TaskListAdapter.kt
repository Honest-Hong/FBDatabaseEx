package hong.mason.fbdatabaseex.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hong.mason.fbdatabaseex.Constants
import hong.mason.fbdatabaseex.R
import hong.mason.fbdatabaseex.adapter.viewholder.NoticeViewHolder
import hong.mason.fbdatabaseex.adapter.viewholder.TaskViewHolder
import hong.mason.fbdatabaseex.base.BaseViewHolder
import hong.mason.fbdatabaseex.base.HasViewType

/**
 * Created by kakao on 2017. 11. 14..
 */
class TaskListAdapter : RecyclerView.Adapter<BaseViewHolder>() {
    private var list : MutableList<Any> = ArrayList()

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        holder?.onBind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder
        = when(viewType) {
        Constants.ViewType.NOTICE -> NoticeViewHolder(parent.inflate(R.layout.viewholder_notice))
        Constants.ViewType.TASK -> TaskViewHolder(parent.inflate(R.layout.viewholder_task))
        else -> throw Exception("Not except view type $viewType")
        }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        if(list[position] is HasViewType) {
            return (list[position] as HasViewType).viewType
        } else {
            throw Exception("Data must have view type.")
        }
    }

    fun setList(list : MutableList<Any>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun updateData(updateList: List<Any>) {
        updateList.forEach {
            if (list.contains(it)) {
                val index = list.indexOf(it)
                list[index] = it
                notifyItemChanged(index)
            } else {
                list.add(it)
                notifyItemInserted(list.size - 1)
            }
        }
    }

    private fun ViewGroup.inflate(id: Int) : View
        = LayoutInflater.from(this.context).inflate(id, this, false)
}
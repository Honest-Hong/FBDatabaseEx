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
    private var list : List<Any> = emptyList()
    private var noticeSize = 0
    private var taskSize = 0

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

    fun addNotice(list: List<Any>) {
        val mutable = this.list.toMutableList()
        (0 until noticeSize).forEach {
            mutable.removeAt(0)
        }
        mutable.addAll(0, list)
        this.list = mutable.toList()
        noticeSize = list.size
        notifyDataSetChanged()
    }

    fun addTask(list: List<Any>) {
        val mutable = this.list.toMutableList()
        (noticeSize until noticeSize + taskSize).forEach {
            mutable.removeAt(noticeSize)
        }
        mutable.addAll(list)
        this.list = mutable.toList()
        taskSize = list.size
        notifyDataSetChanged()
    }

    private fun ViewGroup.inflate(id: Int) : View
        = LayoutInflater.from(this.context).inflate(id, this, false)
}
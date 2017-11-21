package hong.mason.fbdatabaseex.adapter.viewholder

import android.view.View
import hong.mason.fbdatabaseex.base.BaseViewHolder
import hong.mason.fbdatabaseex.data.TaskDTO
import kotlinx.android.synthetic.main.viewholder_task.view.*

/**
 * Created by kakao on 2017. 11. 14..
 */
class TaskViewHolder(itemView: View) : BaseViewHolder(itemView) {
    override fun onBind(data: Any) {
        if(data is TaskDTO) {
            itemView.textTitle.text = data.name
            itemView.textContent.text = data.value.toString()
        }
    }
}
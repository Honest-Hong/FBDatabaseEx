package hong.mason.fbdatabaseex.adapter.viewholder

import android.view.View
import hong.mason.fbdatabaseex.base.BaseViewHolder
import hong.mason.fbdatabaseex.data.NoticeDTO
import kotlinx.android.synthetic.main.viewholder_notice.view.*

/**
 * Created by kakao on 2017. 11. 14..
 */
class NoticeViewHolder(itemView: View) : BaseViewHolder(itemView) {
    override fun onBind(data: Any) {
        if(data is NoticeDTO) {
            itemView.textView.text = data.title
        }
    }
}
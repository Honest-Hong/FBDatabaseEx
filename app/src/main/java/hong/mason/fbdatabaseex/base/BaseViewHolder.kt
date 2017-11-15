package hong.mason.fbdatabaseex.base

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by kakao on 2017. 11. 14..
 */
abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun onBind(data: Any)
}
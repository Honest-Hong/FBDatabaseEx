package hong.mason.fbdatabaseex.data

import hong.mason.fbdatabaseex.Constants
import hong.mason.fbdatabaseex.base.HasViewType

/**
 * Created by kakao on 2017. 11. 14..
 */
data class NoticeDTO(
        val title: String
) : HasViewType {
    override var viewType: Int = Constants.ViewType.NOTICE
}
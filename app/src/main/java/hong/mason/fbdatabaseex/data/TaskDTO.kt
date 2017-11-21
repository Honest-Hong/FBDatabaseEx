package hong.mason.fbdatabaseex.data

import hong.mason.fbdatabaseex.Constants
import hong.mason.fbdatabaseex.base.HasViewType

/**
 * Created by kakao on 2017. 11. 14..
 */
data class TaskDTO(
        val name: String,
        var value: Int = 0
) : HasViewType {
    override var viewType: Int = Constants.ViewType.TASK
}
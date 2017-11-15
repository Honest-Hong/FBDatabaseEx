package hong.mason.fbdatabaseex

/**
 * Created by kakao on 2017. 11. 14..
 */
class Constants {
    class Request {
        companion object {
            val REQUEST_AUTH = 0x100
        }
    }
    class ViewType {
        companion object {
            val NOTICE = 1
            val TASK = 2
        }
    }
    class Key {
        companion object {
            val NOTICE = "notice"
            val TASK = "task"
        }
    }
}
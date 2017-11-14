package hong.mason.fbdatabaseex

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    lateinit var disposable : Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        disposable = Observable.just(0)
                .delay(1000, TimeUnit.MILLISECONDS)
                .subscribe {
                    val intent = Intent(baseContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
    }

    override fun onStop() {
        super.onStop()
        if(!disposable.isDisposed) {
            disposable.dispose()
        }
    }
}

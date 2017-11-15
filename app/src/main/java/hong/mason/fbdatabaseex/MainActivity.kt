package hong.mason.fbdatabaseex

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hong.mason.fbdatabaseex.adapter.TaskListAdapter
import hong.mason.fbdatabaseex.data.NoticeDTO
import hong.mason.fbdatabaseex.data.TaskDTO
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val adapter : TaskListAdapter = TaskListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()


        if(FirebaseAuth.getInstance().currentUser == null) {
            val providers = mutableListOf(AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build())

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    Constants.Request.REQUEST_AUTH)
        } else {
            loadData()
        }

        buttonUpdate.setOnClickListener {
            updateNotice(0, "Local update!!")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            Constants.Request.REQUEST_AUTH -> {
                val response = IdpResponse.fromResultIntent(data)
                if (resultCode == Activity.RESULT_OK) {
                    val user = FirebaseAuth.getInstance().currentUser
                    Toast.makeText(baseContext, "Success::$user", Toast.LENGTH_SHORT).show()
                    loadData()
                }
            }
        }
    }

    private fun setupView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(baseContext)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(baseContext, DividerItemDecoration.VERTICAL))
    }

    private fun loadData() {
        FirebaseDatabase.getInstance()
                .reference.child("notice")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val list = p0.children.map { data ->
                            NoticeDTO(data.value as String)
                        }
                        adapter.addNotice(list)
                    }
                })
        FirebaseDatabase.getInstance()
                .reference.child("task")
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        val list = p0.children.map { data ->
                            TaskDTO(data.child("title").value as String, data.child("content").value as String)
                        }
                        adapter.addTask(list)
                    }
                })
    }

    private fun updateNotice(index: Int, text: String) {
        FirebaseDatabase.getInstance()
                .reference.child("notice")
                .child(index.toString()).setValue(text)
    }
}

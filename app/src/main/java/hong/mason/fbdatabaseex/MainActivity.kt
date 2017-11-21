package hong.mason.fbdatabaseex

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.firebase.firestore.*
import hong.mason.fbdatabaseex.adapter.TaskListAdapter
import hong.mason.fbdatabaseex.data.TaskDTO
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private val adapter : TaskListAdapter = TaskListAdapter()
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        loadData()
    }

    private fun setupView() {
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(baseContext)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(baseContext, DividerItemDecoration.VERTICAL))

        buttonAdd.setOnClickListener {
            addData()
        }
    }

    private fun loadData() {
        db.collection("tasks")
                .get()
                .addOnCompleteListener {
                    if(it.isSuccessful) {
                        val list : MutableList<Any> = ArrayList()
                        it.result.documents.forEach {
                            val data = TaskDTO(
                                    it.data.getOrDefault("name", "null") as String,
                                    (it.data.getOrDefault("value", 0) as Long).toInt())
                            list.add(data)
                        }
                        adapter.setList(list)
                    }
                }
        db.collection("tasks")
                .addSnapshotListener { snapshot, e ->
                    if (e != null) {
                        e.printStackTrace()
                        return@addSnapshotListener
                    }

                    if (snapshot?.isEmpty == false) {
                        val list: MutableList<Any> = ArrayList()
                        snapshot.documentChanges.forEach {
                            val data = TaskDTO(
                                    it.document.data.getOrDefault("name", "null") as String,
                                    (it.document.data.getOrDefault("value", 0) as Long).toInt())
                            list.add(data)
                        }
                        adapter.updateData(list)
                    }
                }
    }

    private fun addData() {
        val user = arrayOf("mason", "daniel", "sian", "jules", "courtney", "lupin", "landon", "aiden")
        val rand = Random()
        val map = HashMap<String, Any>()
        map.put("name", user[Math.abs(rand.nextInt()) % user.size])
        map.put("value", rand.nextInt())
        db.collection("tasks")
                .add(map)
                .addOnSuccessListener {
                    Log.d("MainActivity", "addData::Success")
                }
    }
}

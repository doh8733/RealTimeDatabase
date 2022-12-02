package com.example.realtimedatabase

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_user.view.*
import java.net.IDN

class MainActivity : AppCompatActivity() {
    private val list = mutableListOf<User>()
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnPushData.setOnClickListener {
            //   writeData()
            val id: Int = edtId.text.toString().toInt()
            val name = edtName.text.toString()
            val user = User(id, name)
            addUser(user)
        }
        btnReadData.setOnClickListener {
            readDataBase()
        }
        btnDelete.setOnClickListener {
            deleteData()
        }
        btnUpdate.setOnClickListener {
            val id = edtId.text.toString().toInt()
            val name = edtName.text.toString()
            val user = User(id, name)
            updateData(user)
        }
        userAdapter = UserAdapter(list) {
            onDeleteUser(it.id)
        }
        rcView.layoutManager = LinearLayoutManager(this)
        rcView.adapter = userAdapter

        getListUser()
    }

    private fun writeData() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef = database.getReference("user")
        val user = User(1, "Do Quoc Huy")
        myRef.setValue(user, object : DatabaseReference.CompletionListener {
            override fun onComplete(error: DatabaseError?, ref: DatabaseReference) {
                Toast.makeText(this@MainActivity, "Thanh cong", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun readDataBase() {
        // Read from the database
        val database = Firebase.database
        val myRef = database.getReference("user")
        myRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value: User? = dataSnapshot.getValue(User::class.java)
                tvResultData.text = value.toString()
                Log.d("TAG", "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException())
            }
        })
    }

    private fun deleteData() {
        val database = Firebase.database
        val myRef = database.getReference("user")
        myRef.removeValue(object : DatabaseReference.CompletionListener {
            override fun onComplete(error: DatabaseError?, ref: DatabaseReference) {

            }

        })
    }

    private fun updateData(user: User) {

        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef = database.getReference("user_list/${user.id}")
        myRef.setValue(user, object : DatabaseReference.CompletionListener {
            override fun onComplete(error: DatabaseError?, ref: DatabaseReference) {
                Toast.makeText(this@MainActivity, "Thanh cong", Toast.LENGTH_SHORT).show()
                userAdapter.notifyDataSetChanged()
            }

        })
        //xoa cung tuong tu
        //update 1 truong duy nhat
//        val database = Firebase.database
//        val myRef = database.getReference("user/name")
//        myRef.setValue("Dao Van A"
//        ) { error, ref ->
//            Toast.makeText(this, "Thanh cong", Toast.LENGTH_SHORT).show()
//        }

    }

    private fun addUser(user: User) {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef = database.getReference("user_list")
        val pathObj = "${user.id}"
        myRef.child(pathObj).setValue(
            user
        ) { error, ref ->
            Toast.makeText(this@MainActivity, "Thanh cong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getListUser() {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef = database.getReference("user_list")
        myRef.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (dataUser in snapshot.children) {
                    val user: User = dataUser.getValue(User::class.java)!!
                    list.add(user)
                    userAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Loi!!!!!", Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun onDeleteUser(id: Int) {
        val database = Firebase.database
        val myRef = database.getReference("user_list/$id")
        myRef.removeValue()
        userAdapter.notifyDataSetChanged()
    }

}
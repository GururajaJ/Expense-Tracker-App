package com.example.expencetacker

import android.provider.SyncStateContract
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import androidx.compose.runtime.mutableStateListOf

import androidx.lifecycle.viewModelScope
import androidx.privacysandbox.tools.core.model.Constant
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch
class EViewModel : ViewModel() {
    var income by mutableStateOf("")
        private set
    private val _expenses = mutableStateListOf<Expense>()  // Mutable list to hold expenses
    val expenses: List<Expense> = _expenses

    var pay by mutableStateOf("")

    // Function to update the income value
    fun updateIncome(newIncome: String) {
        income = newIncome
    }
    fun addExpense(amount: String, description: String) {
        _expenses.add(Expense(amount, description))
    }
    fun addpay(paylis:Int){
        pay += paylis
    }
    fun getTotalExpenses(): Int {
        return _expenses.sumOf { it.amount.toIntOrNull() ?: 0 } // Calculate total expenses
    }
    private val auth : FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthSate>()
    val  authSate : LiveData<AuthSate> = _authState

    fun checkAuth(){
        if(auth.currentUser ==null){
            _authState.value=AuthSate.unauthenticated
        }
        else{
            _authState.value=AuthSate.Authenticated
        }

    }

    fun login(email :String,password:String){


        if(email.isEmpty()|| password.isEmpty()){
            _authState.value=AuthSate.Error("Email or password can't be empty")
            return
        }

        _authState.value=AuthSate.loadState
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{task->
                if(task.isSuccessful){
                    _authState.value=AuthSate.Authenticated
                }
                else{
                    _authState.value=AuthSate.Error(task.exception?.message?:"Something Went Wrong")
                }

            }
    }

    fun signUp(email: String, password: String) {
        // Check if email or password is empty
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthSate.Error("Email or password can't be empty")
            return
        }

        // Set state to loading while we try to sign up


        // Attempt to create a new user with Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // If successful, set authenticated state
                    _authState.value = AuthSate.Authenticated
                } else {
                    // If an error occurs, set error state
                    val errorMessage = task.exception?.message ?: "Something went wrong"
                    Log.e("AuthError", "Error during sign-up: $errorMessage") // Log for debugging
                    _authState.value = AuthSate.Error(errorMessage)
                }
            }
    }


    fun signout(){
        auth.signOut()
        _authState.value=AuthSate.unauthenticated
    }
    //ChatViewModel
    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    val generativeModel : GenerativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = Constants.apiKey
    )

    fun sendMessage(question : String){
        viewModelScope.launch {

            try{
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role){ text(it.message) }
                    }.toList()
                )

                messageList.add(MessageModel(question,"user"))
                messageList.add(MessageModel("Typing....","model"))

                val response = chat.sendMessage(question)
                messageList.removeLast()
                messageList.add(MessageModel(response.text.toString(),"model"))
            }catch (e : Exception){
                messageList.removeLast()
                messageList.add(MessageModel("Error : "+e.message.toString(),"model"))
            }


        }
    }

}


sealed class AuthSate{
    object Authenticated : AuthSate()
    object unauthenticated : AuthSate()
    object loadState :AuthSate()
    data class Error(val message :String):AuthSate()

}


data class Expense(
    val amount: String,
    val description: String
)
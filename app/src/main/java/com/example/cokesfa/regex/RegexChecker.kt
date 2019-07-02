package com.example.cokesfa.regex

import com.google.firebase.database.snapshot.BooleanNode
import java.util.regex.Matcher
import java.util.regex.Pattern


class EmailChecker(val email:String) {

    val regex="^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}\$"

    public fun isValid() : Boolean {
        val pattern= Pattern.compile(regex)
        val matcher=pattern.matcher(email)

        return matcher.matches()
    }

}



class PasswordChecker ( val password:String) {
    val regex="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"

    public fun isValid() : Boolean {
        val pattern=Pattern.compile(regex)
        val matcher=pattern.matcher(password)

        return matcher.matches()
    }
}
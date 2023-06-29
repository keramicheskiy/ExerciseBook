package com.example.exercisebook.Utils

import com.example.exercisebook.DataClasses.User

class UserBuilder {
    var id: String = ""
    var login: String = ""
    var number: String = ""
    var name: String = ""
    var isCouch: Boolean = false

    fun setId(value : String) : UserBuilder {
        this.id = value
        return this
    }

    fun setLogin(value : String) : UserBuilder {
        this.login = value
        return this
    }

    fun setNumber(value : String) : UserBuilder {
        this.number = value
        return this
    }

    fun setName(value : String) : UserBuilder {
        this.name = value
        return this
    }

    fun setIsCouch(value : Boolean) : UserBuilder {
        this.isCouch = value
        return this
    }

    fun build(): User {
        return User(
            id = id,
            login = login,
            number = number,
            name = name,
            isCouch = isCouch
        )
    }

}
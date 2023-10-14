package com.example.registrationpage

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(val context: Context, val factory: SQLiteDatabase.CursorFactory?) :
// Знак вопроса пишем чтобы принимать значние null
    SQLiteOpenHelper(context, "app", factory, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id INT PRIMARY KEY, login TEXT, email TEXT, pass TEXT)"
        // primary значит что первичный
        db!!.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser(user: User) {
        val values = ContentValues()
        values.put("login", user.login)
        values.put("email", user.email)
        values.put("pass", user.pass)

        val db = this.writableDatabase
        // writableDatabata это текущая база данных
        db.insert("users", null, values)
        db.close()

    }
    fun getUser(login: String, pass: String) : Boolean{
        // в функцию передается логин и пароль
        val db = this.readableDatabase
        // readableDatabase нужна чтобы что то прочитать из базы данных

        val result = db.rawQuery("SELECT * From users WHERE login = '$login' AND pass = '$pass'",null)
        // Select означает что будет искать записать в таблице users у которой логин и пароль будет совпадать с тему данными которые сюда передаются
        // тут пишет sql команду суть которой будет находить те записи у которых логин будет равен тому значению что передается внутри функции
        // Обязательно пишем одинарные ковычки, чтобы всё сработало. То что мы хотим сверить
        // не забывает указать ещё и пароль, функция сделана так чтобы мы сверяли то что введет пользователь и функция находила его или не находила его
        // Обязательно пишем null
        return result.moveToFirst()

        // Если функцию написать протсто так, она выдаст ошибку. Чтобы она не выдавала ошибку нужно написать что она возращает т.е boolean, пишем её после скобок функции после двоеточия
        // moveToFirst будет возращать true или false в засимости найдется ли такая запись или нет
    }
}
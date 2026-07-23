package com.redfrogec.credigo.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.redfrogec.CrediBase

class AndroidDatabaseDriverFactory(
    private val context: Context
): DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            CrediBase.Schema,
            context,
            "CrediBase.db"
        )
    }
}
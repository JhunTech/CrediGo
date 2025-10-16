package com.redfrogec.credigo.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.redfrogec.CrediDataBase

class IOSDatabaseDriverFactory(): DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            CrediDataBase.Schema,
            "CrediBase.db"
        )
    }
}
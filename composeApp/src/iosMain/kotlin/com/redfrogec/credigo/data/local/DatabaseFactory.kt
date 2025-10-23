package com.redfrogec.credigo.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.redfrogec.CrediBase

class IOSDatabaseDriverFactory(): DatabaseDriverFactory {
    override fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            CrediBase.Schema,
            "CrediBase.db"
        )
    }
}
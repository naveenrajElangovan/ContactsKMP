
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.contactssample.db.MyAppSQLDelightDatabase


actual class DatabaseDriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = MyAppSQLDelightDatabase.Schema.synchronous(),
            name = "MyAppSQLDelightDatabase.db"
        )
    }

}
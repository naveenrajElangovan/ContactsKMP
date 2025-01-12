import android.app.Application
import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.contactssample.MyApplication
import com.example.contactssample.db.MyAppSQLDelightDatabase


actual class DatabaseDriverFactory actual constructor() {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = MyAppSQLDelightDatabase.Schema.synchronous(),
            context = MyApplication.appContext,
            name = "MyAppSQLDelightDatabase.db"
        )
    }

}
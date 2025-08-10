package com.template.db

import com.template.db.TemplateDatabase
import com.template.db.Users
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(private val database: TemplateDatabase) : UserRepository {
    
    override suspend fun findByEmail(email: String): Users? = withContext(Dispatchers.IO) {
        database.userQueries.getUserByEmail(email).executeAsOneOrNull()
    }
    
    override suspend fun findById(id: Long): Users? = withContext(Dispatchers.IO) {
        database.userQueries.getUserById(id).executeAsOneOrNull()
    }
    
    override suspend fun create(email: String, passwordHash: String, userType: String): Long = withContext(Dispatchers.IO) {
        database.userQueries.createUser(email, passwordHash, userType).executeAsOne()
    }
    
    override suspend fun emailExists(email: String): Boolean = withContext(Dispatchers.IO) {
        database.userQueries.getUserByEmail(email).executeAsOneOrNull() != null
    }
}
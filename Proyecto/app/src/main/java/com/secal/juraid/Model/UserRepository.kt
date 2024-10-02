package com.secal.juraid.Model

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.SessionStatus
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put


class UserRepository(private val supabase: SupabaseClient, scope: CoroutineScope) {


    private val _sessionState = MutableStateFlow<SessionStatus>(SessionStatus.LoadingFromStorage)
    val sessionState: StateFlow<SessionStatus> get() = _sessionState

    init {
        scope.launch {
            // Listener para cambios de sesión
            supabase.auth.sessionStatus.collect { sessionStatus ->
                _sessionState.value = sessionStatus
            }
        }
    }


    suspend fun signIn(userEmail: String, userPassword: String) {
        supabase.auth.signInWith(Email){
            email = userEmail
            password = userPassword
        }
    }

    suspend fun signUp(
        userEmail: String,
        userPassword: String,
        name: String,
        firstLastName: String,
        secondLastName: String,
        phone: String
    ) {
        supabase.auth.signUpWith(Email) {
            email = userEmail
            password = userPassword
            data = buildJsonObject {
                put("name", name)
                put("first_last_name", firstLastName)
                put("second_last_name", secondLastName)
                put("phone", phone)
                put("role", 0)
            }
        }
    }

    suspend fun signOut() {
        supabase.auth.signOut()
    }

    suspend fun getUserName(): String? {
        return try {
            val user = supabase.auth.retrieveUserForCurrentSession()
            val metadata = user.userMetadata
            val name = metadata?.get("name")?.toString()?.trim()?.replace("\"", "") ?: ""
            val firstName = metadata?.get("first_last_name")?.toString()?.trim()?.replace("\"", "") ?: ""
            val secondName = metadata?.get("second_last_name")?.toString()?.trim()?.replace("\"", "") ?: ""

            listOf(name, firstName, secondName)
                .filter { it.isNotBlank() }
                .joinToString(" ")
                .ifBlank { null }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getUserRole(): Int? {
        return try {
            val user = supabase.auth.retrieveUserForCurrentSession()
            val metadata = user.userMetadata
            val role = metadata?.get("role")?.toString()?.toInt() ?: 0
            role

        } catch (e: Exception) {
            null
        }
    }

}

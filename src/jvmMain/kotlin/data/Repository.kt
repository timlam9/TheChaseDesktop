package data

import data.models.Question
import data.models.User
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

const val url = "http://192.168.1.101:8080"
const val version = "/v1"

const val questionsEndpoint = "/questions"
const val usersEndpoint = "/users"
const val loginEndpoint = "/login"

class Repository(private val client: HttpClient = ClientBuilder.defaultHttpClient) {

    private var token = ""

    suspend fun login(email: String, password: String): String {
        val url = "$url$version$usersEndpoint$loginEndpoint"

        val apiToken: String = client.submitForm(
            url = url,
            formParameters = parameters {
                append("email", email)
                append("password", password)
            }
        ).body()

        token = apiToken

        return apiToken
    }

    suspend fun getUsers(): List<User> = client.get("$url$version$usersEndpoint").body()

    private suspend fun getQuestions(): List<Question> {
        return client.get("$url$version$questionsEndpoint") {
            header("Authorization", "Bearer $token")
        }.body()
    }

    suspend fun getQuestionsResponse(): ServerResponse<List<Question>> {
        return try {
            val result = getQuestions()
            ServerResponse.Content(result)
        } catch (e: Exception) {
            e.printStackTrace()
            ServerResponse.Error(e)
        }
    }
}


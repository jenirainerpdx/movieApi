package com.jentest.movieapi.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Movie (@Id var id: String?,
                  val name: String,
                  val genre: String,
                  val releaseDate: LocalDateTime) {

    override fun toString(): String {
        return "Movie(id='$id', name='$name', genre='$genre', releaseDate=$releaseDate)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Movie

        if (id != other.id) return false
        if (name != other.name) return false
        if (genre != other.genre) return false
        if (releaseDate != other.releaseDate) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + genre.hashCode()
        result = 31 * result + releaseDate.hashCode()
        return result
    }
}

package com.jentest.movieapi.repository

import com.jentest.movieapi.model.Movie
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface MovieRepository : ReactiveMongoRepository<Movie, String> {
}

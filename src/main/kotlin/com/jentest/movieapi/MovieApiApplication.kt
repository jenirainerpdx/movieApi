package com.jentest.movieapi

import com.jentest.movieapi.model.Movie
import com.jentest.movieapi.repository.MovieRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.core.ReactiveMongoOperations
import reactor.core.publisher.Flux
import java.time.LocalDateTime

fun main(args: Array<String>) {
    runApplication<MovieApiApplication>(*args)
}

@SpringBootApplication
class MovieApiApplication {

    @Bean
    fun init(operations: ReactiveMongoOperations, movieRepository: MovieRepository): CommandLineRunner {
        val cli = CommandLineRunner { val productFlux: Flux<Movie> = Flux.just(
                Movie(null, "Avenger: Infinity Wars", "Action", LocalDateTime.now()),
                Movie(null, "Marley & Me", "Comedy", LocalDateTime.now()),
                Movie(null, "Devil Wears Prada", "Comedy", LocalDateTime.now()))
                .flatMap { it -> movieRepository.save(it) }
            productFlux.thenMany(movieRepository.findAll())
                    .subscribe { System.out.println(it) } }
        return cli
    }
}

package com.jentest.movieapi.controller

import com.jentest.movieapi.model.Movie
import com.jentest.movieapi.repository.MovieRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/movies")
class MovieController(val movieRepository: MovieRepository) {

    @GetMapping
    fun getMovies(): Flux<Movie> {
        return movieRepository.findAll();
    }

    @GetMapping("{id}")
    fun getMovieById(@PathVariable id: String): Mono<ResponseEntity<Movie>> {
        return movieRepository
                .findById(id)
                .map{ it ->  ResponseEntity(it, HttpStatus.OK)}
                .defaultIfEmpty(ResponseEntity.notFound().build())
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveMovie(@RequestBody movie: Movie): Mono<Movie> {
        return movieRepository.save(movie)
    }

    /**
     * @TODO: fix this.
     */
    @PutMapping("{id}")
    fun updateMovie(@PathVariable id: String, @RequestBody movie: Movie): Mono<ResponseEntity<Movie>> {
        return movieRepository.findById(id)
                .map { it ->
                    val savedMovie = movieRepository.save(movie).block()
                    return@map ResponseEntity(savedMovie, HttpStatus.OK)
                }
                .defaultIfEmpty(ResponseEntity.notFound().build<Movie>())
    }

    /**
     * @TODO: fix this; doesn't commit the change.
     */
    @DeleteMapping("{id}")
    fun deleteMovieById(@PathVariable id: String): Mono<ResponseEntity<Unit>>{
        return movieRepository.findById(id)
                .map { existingMovie ->
                    movieRepository.delete(existingMovie)
                    return@map ResponseEntity<Unit>(HttpStatus.OK)
                }
                .defaultIfEmpty(ResponseEntity.notFound().build<Unit>())
    }

    @DeleteMapping
    fun deleteAllMovies(): Mono<Void> {
        return movieRepository.deleteAll()
    }

}

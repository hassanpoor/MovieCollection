package service;

import domain.Movie;
import domain.Review;
import exception.MovieException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class MovieReactiveService {
    private final MovieInfoService movieInfoService;
    private final ReviewService reviewService;

    public MovieReactiveService(MovieInfoService movieInfoService, ReviewService reviewService) {
        this.movieInfoService = movieInfoService;
        this.reviewService = reviewService;
    }

    public Flux<Movie> getAllMovies() {
        var movieInfoFlux = movieInfoService.retrieveMoviesFlux();
        return movieInfoFlux.flatMap(movieInfo -> {
                    Mono<List<Review>> reviewsMono = reviewService.retriveReviewsFlux(movieInfo.getMovieId()).collectList();
                    return reviewsMono.map(reviewList -> new Movie(movieInfo, reviewList));
                }).onErrorMap(ex -> {
                    log.error("exception is :", ex);
                    throw new MovieException(ex.getMessage());
                })
                .log();
    }

    public Mono<Movie> getMovieById(long movieId){
        var movieInfoMono=movieInfoService.retrieveMovieInfoMonoUsingId(movieId);
        Mono<List<Review>> reviewsFlux = reviewService.retriveReviewsFlux(movieId).collectList();
        return movieInfoMono.zipWith(reviewsFlux, Movie::new);
    }


}

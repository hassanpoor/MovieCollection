package service;

import domain.MovieInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static util.CommonUtil.delay;

public class MovieInfoService {
    public List<MovieInfo> movieList() {
        delay(1000);
        var moviesList = List.of(new MovieInfo(1l, 100l, "Batman Begins", 2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(2l, 101l, "The Dark Knight", 2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo(3l, 102l, "Dark Knight Rises", 2008, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        return moviesList;
    }

    public MovieInfo retrieveMovieUsingId(long movieId) {
        delay(1000);
        var movie = new MovieInfo(movieId, 100l, "Batman Begins", 2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));

        return movie;
    }

    public Flux<MovieInfo> movieInfoFlux() {
        var movieInfoList = movieList();
        return Flux.fromIterable(movieInfoList);
    }

    public Mono<MovieInfo> retrieveMovieInfoMonoUsingId(long movieId) {
        var movie = retrieveMovieUsingId(movieId);
        return Mono.just(movie);
    }
}

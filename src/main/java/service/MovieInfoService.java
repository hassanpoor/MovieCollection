package service;

import domain.MovieInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import util.CommonUtil;

import static util.CommonUtil.delay;

public class MovieInfoService {


    public MovieInfo retrieveMovieUsingId(long movieId) {
        delay(1000);
//        MovieInfo movie = new MovieInfo(movieId, 100l, "Batman Begins", 2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15"));
        return CommonUtil.createMovieList().stream().filter(a -> a.getMovieId().equals(movieId)).findFirst().orElse(null);
    }

    public Flux<MovieInfo> movieInfoFlux() {
        var movieInfoList = CommonUtil.createMovieList();
        return Flux.fromIterable(movieInfoList);
    }

    public Mono<MovieInfo> retrieveMovieInfoMonoUsingId(long movieId) {
        var movie = retrieveMovieUsingId(movieId);
        return Mono.just(movie);
    }

    public Flux<MovieInfo> retrieveMoviesFlux(){
        return Flux.fromIterable(CommonUtil.createMovieList());
    }
}

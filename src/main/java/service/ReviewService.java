package service;

import domain.Review;
import reactor.core.publisher.Flux;

import java.util.List;

public class ReviewService {
    public List<Review> retrieveReviews(long MovieId) {

        var reviewsList = List.of(new Review(MovieId, "Awesome Movie", 8.9),
                new Review(MovieId, "Excellent Movie", 9.0));
        return reviewsList;
    }

    public Flux<Review> retriveReviewsFlux(long movieId) {
        return Flux.fromIterable(retrieveReviews(movieId));
    }

}

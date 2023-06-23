package util;

import domain.MovieInfo;

import java.time.LocalDate;
import java.util.List;

import static java.lang.Thread.sleep;

public class CommonUtil {
    public static void delay(int ms) {
        try {
            sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<String> createNamesList() {
        return List.of("alex", "ben", "chloe", "adam", "adam");
    }

    public static List<MovieInfo> createMovieList() {
        delay(1000);
        var moviesList = List.of(new MovieInfo(1l, 100l, "Batman Begins", 2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(2l, 101l, "The Dark Knight", 2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo(3l, 102l, "Dark Knight Rises", 2008, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        return moviesList;
    }
}

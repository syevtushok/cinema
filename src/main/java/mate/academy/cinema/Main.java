package mate.academy.cinema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import mate.academy.cinema.lib.Injector;
import mate.academy.cinema.model.CinemaHall;
import mate.academy.cinema.model.Movie;
import mate.academy.cinema.model.MovieSession;
import mate.academy.cinema.service.CinemaHallService;
import mate.academy.cinema.service.MovieService;
import mate.academy.cinema.service.MovieSessionService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy.cinema");

    public static void main(String[] args) {
        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        movieService.add(movie);
        movieService.getAll().forEach(System.out::println);

        CinemaHallService cinemaHallService =
                (CinemaHallService) injector.getInstance(CinemaHallService.class);
        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(200);
        cinemaHall = cinemaHallService.add(cinemaHall);

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setMovie(movie);
        movieSession.setShowTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 30)));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        System.out.println("------------");
        movieSessionService.findAvailableSessions(movie.getId(), LocalDate.now())
                .forEach(System.out::println);
        System.out.println("------------");
        movieSessionService.findAvailableSessions(movie.getId(),
                LocalDate.of(2020, 4, 4)).forEach(System.out::println);
    }
}

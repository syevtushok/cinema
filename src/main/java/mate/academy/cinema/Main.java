package mate.academy.cinema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import mate.academy.cinema.exceptions.AuthenticationException;
import mate.academy.cinema.lib.Injector;
import mate.academy.cinema.model.CinemaHall;
import mate.academy.cinema.model.Movie;
import mate.academy.cinema.model.MovieSession;
import mate.academy.cinema.model.ShoppingCart;
import mate.academy.cinema.model.User;
import mate.academy.cinema.service.AuthenticationService;
import mate.academy.cinema.service.CinemaHallService;
import mate.academy.cinema.service.MovieService;
import mate.academy.cinema.service.MovieSessionService;
import mate.academy.cinema.service.OrderService;
import mate.academy.cinema.service.ShoppingCartService;
import mate.academy.cinema.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static Injector injector = Injector.getInstance("mate.academy.cinema");

    public static void main(String[] args) throws AuthenticationException {
        movieTest();
        userTest();
        authenticationTest();
        hw273Test();
        hw274Test();
    }

    private static void movieTest() {
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
        movieSession.setShowTime(LocalDateTime.of(LocalDate.of(2020, 2, 6),
                LocalTime.of(12, 30)));
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        movieSessionService.add(movieSession);
        movieSessionService.findAvailableSessions(movie.getId(), LocalDate.now())
                .forEach(System.out::println);
    }

    private static void userTest() {
        User user = new User();
        user.setEmail("a@a.a");
        user.setPassword("a");
        UserService userService = (UserService) injector.getInstance(UserService.class);
        userService.add(user);
        LOGGER.info(userService.findByEmail("a@a.a"));
    }

    private static void authenticationTest() throws AuthenticationException {
        AuthenticationService service =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        service.register("b@b.b", "b");
        LOGGER.info("Correct data " + service.login("b@b.b", "b"));
    }

    private static void hw273Test() throws AuthenticationException {
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        List<MovieSession> availabeMovies = movieSessionService.findAvailableSessions(1L,
                LocalDate.of(2020, 2, 6));
        AuthenticationService service =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        User user = service.login("b@b.b", "b");
        ShoppingCartService cartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        MovieSession movieSession = availabeMovies.get(0);
        cartService.addSession(movieSession, user);
        ShoppingCart shoppingCart = cartService.getByUser(user);
        LOGGER.info(shoppingCart);
    }

    private static void hw274Test() throws AuthenticationException {
        MovieSessionService movieSessionService =
                (MovieSessionService) injector.getInstance(MovieSessionService.class);
        List<MovieSession> availabeMovies = movieSessionService.findAvailableSessions(1L,
                LocalDate.of(2020, 2, 6));
        AuthenticationService service =
                (AuthenticationService) injector.getInstance(AuthenticationService.class);
        User user = service.login("b@b.b", "b");
        ShoppingCartService cartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        MovieSession movieSession = availabeMovies.get(0);
        cartService.addSession(movieSession, user);
        ShoppingCart shoppingCart = cartService.getByUser(user);
        LOGGER.info(shoppingCart);
        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        orderService.completeOrder(shoppingCart.getTickets(), user);
        LOGGER.info(orderService.getOrderHistory(user));
    }
}

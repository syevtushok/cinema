package mate.academy.cinema.controllers;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import mate.academy.cinema.dto.request.ShoppingCartRequestDto;
import mate.academy.cinema.dto.response.ShoppingCartResponseDto;
import mate.academy.cinema.dto.response.TicketResponseDto;
import mate.academy.cinema.model.MovieSession;
import mate.academy.cinema.model.ShoppingCart;
import mate.academy.cinema.model.Ticket;
import mate.academy.cinema.model.User;
import mate.academy.cinema.service.MovieSessionService;
import mate.academy.cinema.service.ShoppingCartService;
import mate.academy.cinema.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shoppingcarts")
public class ShoppingCartController {
    private ShoppingCartService shoppingCartService;
    private UserService userService;
    private MovieSessionService movieSessionService;

    public ShoppingCartController(ShoppingCartService shoppingCartService,
                                  UserService userService,
                                  MovieSessionService movieSessionService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.movieSessionService = movieSessionService;
    }

    @PostMapping("/addmoviesession")
    public ShoppingCartResponseDto addToShoppingCart(@RequestBody ShoppingCartRequestDto
                                                             shoppingCartRequestDto) {
        MovieSession movieSession =
                movieSessionService.getById(shoppingCartRequestDto.getMovieSessionId());
        User user = userService.getById(shoppingCartRequestDto.getUserId());
        shoppingCartService.addSession(movieSession, user);
        return getShoppingCartResponseDto(shoppingCartService.getByUser(user));
    }

    @GetMapping("/byuser{userId}")
    public ShoppingCartResponseDto getShoppingCartByUser(@PathVariable Long userId) {
        return getShoppingCartResponseDto(shoppingCartService
                .getByUser(userService.getById(userId)));
    }

    private ShoppingCartResponseDto getShoppingCartResponseDto(ShoppingCart shoppingCart) {
        ShoppingCartResponseDto shoppingCartResponseDto = new ShoppingCartResponseDto();
        shoppingCartResponseDto.setTickets(getListTicketsResponseDto(shoppingCart));
        shoppingCartResponseDto.setUserId(shoppingCart.getUser().getId());
        return shoppingCartResponseDto;
    }

    private List<TicketResponseDto> getListTicketsResponseDto(ShoppingCart shoppingCart) {
        return shoppingCart.getTickets().stream()
                .map(this::getTicketResponseDto)
                .collect(Collectors.toList());
    }

    private TicketResponseDto getTicketResponseDto(Ticket ticket) {
        TicketResponseDto ticketResponseDto = new TicketResponseDto();
        ticketResponseDto.setUserId(ticket.getUser().getId());
        ticketResponseDto.setCinemaHall(ticket.getMovieSession().getCinemaHall().getDescription());
        ticketResponseDto.setMovie(ticket.getMovieSession().getMovie().getTitle());
        ticketResponseDto.setShowTime(ticket.getMovieSession().getShowTime()
                .format(DateTimeFormatter.ISO_LOCAL_TIME));
        return ticketResponseDto;
    }

}

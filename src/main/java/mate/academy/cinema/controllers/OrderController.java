package mate.academy.cinema.controllers;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import mate.academy.cinema.dto.request.OrderRequestDto;
import mate.academy.cinema.dto.response.OrderResponseDto;
import mate.academy.cinema.dto.response.TicketResponseDto;
import mate.academy.cinema.model.Order;
import mate.academy.cinema.model.Ticket;
import mate.academy.cinema.service.OrderService;
import mate.academy.cinema.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private UserService userService;

    public OrderController(OrderService orderService,
                           UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/complete")
    public OrderResponseDto completeOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Order order = orderService.completeOrder(userService.getById(orderRequestDto.getUserId()));
        return getOrderResponseDto(order);
    }

    @GetMapping("/{userId}")
    public List<Order> getAllOrders(@PathVariable Long userId) {
        return orderService.getOrderHistory(userService.getById(userId));
    }

    private OrderResponseDto getOrderResponseDto(Order order) {
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setTickets(getListTicketsResponseDto(order));
        orderResponseDto.setOrderId(order.getId());
        orderResponseDto.setUserId(order.getUser().getId());
        return orderResponseDto;
    }

    private List<TicketResponseDto> getListTicketsResponseDto(Order order) {
        List<Ticket> tickets = order.getTickets();
        return tickets.stream()
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

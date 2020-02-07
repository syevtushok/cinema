package mate.academy.cinema.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import mate.academy.cinema.dao.OrderDao;
import mate.academy.cinema.lib.Inject;
import mate.academy.cinema.lib.Service;
import mate.academy.cinema.model.Order;
import mate.academy.cinema.model.Ticket;
import mate.academy.cinema.model.User;
import mate.academy.cinema.service.OrderService;
import mate.academy.cinema.service.ShoppingCartService;

@Service
public class OrderServiceImpl implements OrderService {
    @Inject
    private static OrderDao orderDao;
    @Inject
    private static ShoppingCartService shoppingCartService;

    @Override
    public Order completeOrder(List<Ticket> tickets, User user) {
        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setTickets(tickets);
        order.setUser(user);

        shoppingCartService.clear(user);
        return orderDao.add(order);
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        return orderDao.getAllUserOrders(user);
    }
}

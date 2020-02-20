package mate.academy.cinema.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import mate.academy.cinema.dao.OrderDao;
import mate.academy.cinema.model.Order;
import mate.academy.cinema.model.ShoppingCart;
import mate.academy.cinema.model.User;
import mate.academy.cinema.service.OrderService;
import mate.academy.cinema.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderDao orderDao;
    private final ShoppingCartService shoppingCartService;

    public OrderServiceImpl(OrderDao orderDao, ShoppingCartService shoppingCartService) {
        this.orderDao = orderDao;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public Order completeOrder(User user) {
        ShoppingCart shoppingCartByUser = shoppingCartService.getByUser(user);
        Order order = new Order();
        order.setTickets(shoppingCartByUser.getTickets());
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        shoppingCartService.clearByUser(user);
        return orderDao.add(order);
    }

    @Override
    public List<Order> getOrderHistory(User user) {
        return orderDao.getAllUserOrders(user);
    }

    @Override
    public Order getById(Long id) {
        return orderDao.getById(id);
    }
}

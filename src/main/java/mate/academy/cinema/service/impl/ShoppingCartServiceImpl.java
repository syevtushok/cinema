package mate.academy.cinema.service.impl;

import mate.academy.cinema.dao.ShoppingCartDao;
import mate.academy.cinema.dao.TicketDao;
import mate.academy.cinema.lib.Dao;
import mate.academy.cinema.lib.Inject;
import mate.academy.cinema.model.MovieSession;
import mate.academy.cinema.model.ShoppingCart;
import mate.academy.cinema.model.Ticket;
import mate.academy.cinema.model.User;
import mate.academy.cinema.service.ShoppingCartService;

@Dao
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    ShoppingCartDao shoppingCartDao;
    @Inject
    TicketDao ticketDao;

    @Override
    public void addSession(MovieSession movieSession, User user) {
        Ticket ticket = new Ticket();
        ticket.setCinemaHall(movieSession.getCinemaHall());
        ticket.setMovie(movieSession.getMovie());
        ticket.setUser(user);
        ticket.setShowTime(movieSession.getShowTime());

        ShoppingCart shoppingCart = shoppingCartDao.getByUser(user);
        shoppingCart.getTickets().add(ticketDao.add(ticket));
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUser(User user) {
        return shoppingCartDao.getByUser(user);
    }

    @Override
    public void registerNewShoppingCart(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartDao.add(shoppingCart);
    }

    @Override
    public void clear(User user) {
        ShoppingCart shoppingCart = shoppingCartDao.getByUser(user);
        shoppingCart.getTickets().clear();
        shoppingCartDao.update(shoppingCart);
    }
}

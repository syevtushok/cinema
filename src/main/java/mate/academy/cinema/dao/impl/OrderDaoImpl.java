package mate.academy.cinema.dao.impl;

import java.util.List;

import mate.academy.cinema.dao.OrderDao;
import mate.academy.cinema.exceptions.DataProcessingException;
import mate.academy.cinema.model.Order;
import mate.academy.cinema.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl implements OrderDao {
    private SessionFactory sessionFactory;

    @Override
    public Order add(Order order) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long orderId = (Long) session.save(order);
            transaction.commit();
            order.setId(orderId);
            return order;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cannot create order", e);
        }
    }

    @Override
    public List<Order> getAllUserOrders(User user) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("select distinct o from Order o "
                                       + "join fetch o.tickets where o.user "
                                       + "= :user",
                    Order.class).setParameter("user", user).list();
        }
    }
}

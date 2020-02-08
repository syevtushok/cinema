package mate.academy.cinema.dao.impl;

import java.util.List;

import mate.academy.cinema.dao.OrderDao;
import mate.academy.cinema.exceptions.DataProcessingException;
import mate.academy.cinema.lib.Dao;
import mate.academy.cinema.model.Order;
import mate.academy.cinema.model.User;
import mate.academy.cinema.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order add(Order order) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("select distinct o from Order o join fetch o.tickets where o.user "
                            + "= :user",
                    Order.class).setParameter("user", user).list();
        }
    }
}

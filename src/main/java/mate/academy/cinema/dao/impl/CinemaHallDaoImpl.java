package mate.academy.cinema.dao.impl;

import java.util.List;

import mate.academy.cinema.dao.CinemaHallDao;
import mate.academy.cinema.exceptions.DataProcessingException;
import mate.academy.cinema.lib.Dao;
import mate.academy.cinema.model.CinemaHall;
import mate.academy.cinema.util.HibernateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class CinemaHallDaoImpl implements CinemaHallDao {
    private static final Logger LOGGER = LogManager.getLogger(CinemaHallDaoImpl.class);

    @Override
    public CinemaHall add(CinemaHall cinemaHall) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Long cinemaHallId = (Long) session.save(cinemaHall);
            transaction.commit();
            cinemaHall.setId(cinemaHallId);
            return cinemaHall;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot add cinema hall", e);
        }
    }

    @Override
    public List<CinemaHall> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return (List<CinemaHall>) session.createQuery("From CinemaHall").list();
        } catch (Exception e) {
            throw new DataProcessingException("Error retrieving all cinema halls ", e);
        }
    }
}

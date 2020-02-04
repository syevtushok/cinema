package mate.academy.cinema.dao.impl;

import java.time.LocalDate;
import java.util.List;

import mate.academy.cinema.dao.MovieSessionDao;
import mate.academy.cinema.exceptions.DataProcessingException;
import mate.academy.cinema.lib.Dao;
import mate.academy.cinema.model.MovieSession;
import mate.academy.cinema.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class MovieSessionDaoImpl implements MovieSessionDao {
    @Override
    public List<MovieSession> findAvailableSessions(Long movieId, LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<MovieSession> query = session.createQuery("From MovieSession where id = "
                    + ":movieId " + "and" + " year(showTime) = :year and month(showTime) = :month "
                    + "and day(showTime) = " + ":day");
            query.setParameter("movieId", movieId);
            query.setParameter("year", date.getYear());
            query.setParameter("month", date.getMonthValue());
            query.setParameter("day", date.getDayOfMonth());
            return query.list();

        } catch (Exception e) {
            throw new DataProcessingException("Error retrieving movie sessions ", e);
        }
    }

    @Override
    public MovieSession add(MovieSession movieSession) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Long moviesSessionId = (Long) session.save(movieSession);
            transaction.commit();
            movieSession.setId(moviesSessionId);
            return movieSession;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot add movie session", e);
        }
    }
}

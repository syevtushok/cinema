package mate.academy.cinema.dao.impl;

import mate.academy.cinema.dao.RoleDao;
import mate.academy.cinema.exceptions.DataProcessingException;
import mate.academy.cinema.model.Role;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl implements RoleDao {
    private final SessionFactory sessionFactory;

    public RoleDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role add(Role role) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long roleId = (Long) session.save(role);
            transaction.commit();
            role.setId(roleId);
            return role;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cannot create role ", e);
        }
    }

    @Override
    public Role getRole(String role) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Role where role = :role",
                    Role.class).setParameter("role", role).uniqueResult();
        } catch (Exception e) {
            throw new DataProcessingException("Can't find role by name", e);
        }
    }
}

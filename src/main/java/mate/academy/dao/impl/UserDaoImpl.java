package mate.academy.dao.impl;

import java.util.Optional;
import mate.academy.dao.UserDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.User;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@Dao
public class UserDaoImpl implements UserDao {
    @Override
    public Optional<User> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("from User u where u.email = :email", User.class);
            query.setParameter("email", email);
            return uniqueResultOptional(query);
        } catch (Exception e) {
            throw new DataProcessingException("Cannot find an user by email: " + email, e);
        }
    }

    private static Optional<User> uniqueResultOptional(Query<User> query) {
        return Optional.ofNullable(query.uniqueResult());
    }

    @Override
    public User persist(User user) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.persist(user);
            transaction.commit();
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataProcessingException("Cannot persist an user: " + user, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}

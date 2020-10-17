package pl.sdaacademy.repository;

import pl.sdaacademy.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class UserRepository {
    final EntityManager entityManager;

    public UserRepository(final EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<User> findCustomersByName(String name) {
        return entityManager.createQuery("select u from User u where u.name = :name", User.class)
                .setParameter("name", name)
                .getResultList();
    }

    public List<User> findCustomers() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public User createUser(User user) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            if (!transaction.isActive()) {
                transaction.begin();
            }
            user.getContacts().stream()
                    .forEach(c -> entityManager.persist(c));
            user.getAddresses().stream()
                    .forEach(a -> entityManager.persist(a));

            entityManager.persist(user);
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            if (transaction.isActive()) {
                transaction.commit();
            }
        }
        return user;
    }
}

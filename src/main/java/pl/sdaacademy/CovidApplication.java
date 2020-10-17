package pl.sdaacademy;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import pl.sdaacademy.model.*;
import pl.sdaacademy.repository.UserRepository;

import java.util.Set;

public class CovidApplication {
    static SessionFactory sessionFactory;
    static UserRepository userRepository;

    public static void main(String... args) {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(Contact.class)
                .addAnnotatedClass(HomeLockDown.class)
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            userRepository = new UserRepository(session);

            userRepository.createUser(User.builder()
                    .name("Jacek")
                    .surname("Sas")
                    .addresses(Set.of(Address.builder()
                            .city("Wroclaw")
                            .street("Plac Legionow").build()))
                    .contacts(Set.of(Contact.builder()
                            .contactType(ContactType.PHONE_NUMBER)
                            .contactValue("111222333").build()))
                    .build()
            );

            User user = userRepository.findCustomersByName("Jacek").get(0);
            System.out.println(user.toString());
        }

    }
}

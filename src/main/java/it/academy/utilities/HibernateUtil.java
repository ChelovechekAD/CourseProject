package it.academy.utilities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtil {

    private static EntityManagerFactory ENTITY_MANAGER_FACTORY;

    private HibernateUtil(){
    }

    public static EntityManager getEntityManager(){
        if (ENTITY_MANAGER_FACTORY == null){
            ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate-mysql");
        }
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static void closeEntityManagerFactory(){
        ENTITY_MANAGER_FACTORY.close();
    }

}

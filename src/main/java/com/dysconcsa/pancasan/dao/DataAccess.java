package com.dysconcsa.pancasan.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.logging.Logger;


public class DataAccess {
    Logger logger = Logger.getLogger(getClass().getName());

    private static DataAccess instance;

    private DataAccess() {

    }

    public static DataAccess getInstance() {
        if (instance == null) {
            instance = new DataAccess();
        }
        return instance;
    }

    public EntityManager entityManager() {
        try {
            EntityManagerFactory emf =
                    Persistence.createEntityManagerFactory("default");
            EntityManager em = emf.createEntityManager();
            logger.info("Se han realizado las consultas de forma correcta");
            return em;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}

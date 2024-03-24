package com.jardvcode.model.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {
	
   private static final String PERSISTENCE_UNIT_NAME = "library";
   protected static PersistenceManager me = null;
   private EntityManagerFactory emf = null;

   private PersistenceManager() {
   }

   public static PersistenceManager getInstance() {
      if (me == null) {
         me = new PersistenceManager();
      }
      return me;
   }

   public void setEntityManagerFactory(EntityManagerFactory myEmf) {
      emf = myEmf;
   }
 
   public EntityManager createEntityManager() {
      if (emf == null) {
         emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
         this.setEntityManagerFactory(emf);
      }
      return emf.createEntityManager();
   }
 
   public void close() {
      if (emf != null)
         emf.close();
         emf = null;
   }
   
}

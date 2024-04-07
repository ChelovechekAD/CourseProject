package it.academy.utilities;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.transaction.TransactionalException;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static it.academy.utilities.Constants.NULL_EXCEPTION_MESSAGE;

public final class TransactionHelper {

    private static TransactionHelper transactionHelper;
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private Metamodel metamodel;

    private TransactionHelper() {
        this.entityManager = HibernateUtil.getEntityManager();
    }

    /**
     * Return <code>TransactionHelper</code> instance, if instance is null - creates new instance.
     *
     * @return <code>TransactionHelper</code> instance
     */
    public static TransactionHelper getTransactionHelper() {
        if (transactionHelper == null) {
            transactionHelper = new TransactionHelper();
        }
        return transactionHelper;
    }

    /**
     * Validate obj on not null value.
     *
     * @param obj object to be validate.
     * @return true - object is not null, else false.
     */
    public static <T> boolean nullValidator(T obj) {
        return obj != null;
    }

    /**
     * Start a resource transaction. Creates new <code>EntityManager</code> instance if the last one was closed.
     *
     * @throws IllegalStateException if <code>isActive()</code> is true
     */
    public void begin() {
        entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
    }

    /**
     * Commit the current resource transaction, writing any
     * unflushed changes to the database.
     *
     * @throws IllegalStateException if <code>isActive()</code> is false
     * @throws RollbackException     if the commit fails
     */
    public void commit() {
        entityManager.getTransaction().commit();
    }

    /**
     * Roll back the current resource transaction. If transaction is active or roll back only.
     *
     * @throws PersistenceException if an unexpected error
     *                              condition is encountered
     */
    public void rollback() {
        if (entityManager.getTransaction().isActive() || entityManager.getTransaction().getRollbackOnly()) {
            entityManager.getTransaction().rollback();
        }
    }

    /**
     * If entity manager is closed then provides new entity manager instance.
     */
    private void getEntityManagerIfClosed() {
        if (!entityManager.isOpen()) {
            entityManager = HibernateUtil.getEntityManager();
        }
    }

    /**
     * Return <code>EntityManager</code> instance. Creates new <code>EntityManager</code> instance if the last one was closed.
     *
     * @return <code>EntityManager</code> instance
     */
    public EntityManager entityManager() {
        getEntityManagerIfClosed();
        return entityManager;
    }

    /**
     * Close an application-managed entity manager.
     * After the close method has been invoked, all methods
     * on the <code>EntityManager</code> instance and any
     * <code>Query</code>, <code>TypedQuery</code>, and
     * <code>StoredProcedureQuery</code> objects obtained from
     * it will throw the <code>IllegalStateException</code>
     * except for <code>getProperties</code>,
     * <code>getTransaction</code>, and <code>isOpen</code> (which will return false).
     * If this method is called when the entity manager is
     * joined to an active transaction, the persistence
     * context remains managed until the transaction completes.
     */
    public void closeEntityManager() {
        entityManager.close();
    }

    /**
     * Return an instance of <code>CriteriaBuilder</code> for the creation of
     * <code>CriteriaQuery</code> objects.
     *
     * @return CriteriaBuilder instance
     * @throws IllegalStateException if the entity manager has
     *                               been closed
     * @since Java Persistence 2.0
     */
    public CriteriaBuilder criteriaBuilder() {
        if (criteriaBuilder == null) {
            criteriaBuilder = entityManager.getCriteriaBuilder();
        }
        return criteriaBuilder;
    }

    /**
     * Return an instance of <code>Metamodel</code> interface for access to the
     * metamodel of the persistence unit.
     *
     * @return Metamodel instance
     * @throws IllegalStateException if the entity manager has
     *                               been closed
     * @since Java Persistence 2.0
     */
    public Metamodel metamodel() {
        if (metamodel == null) {
            metamodel = entityManager.getMetamodel();
        }
        return metamodel;
    }

    /**
     * Validate provided object on not null value, else throw <code>TransactionalException</code>
     *
     * @param obj object to be validate.
     * @throws TransactionalException if provided object is null.
     */
    private <T> void validateObject(T obj) {
        if (!nullValidator(obj)) {
            throw new TransactionalException(NULL_EXCEPTION_MESSAGE, new Exception());
        }
    }

    /**
     * Make an instance managed and persistent.
     *
     * @param obj entity instance
     * @return saved object
     * @throws EntityExistsException        if the entity already exists.
     *                                      (If the entity already exists, the EntityExistsException
     *                                      may be thrown when the persist operation is invoked,
     *                                      or the EntityExistsException or another PersistenceException
     *                                      may be thrown at flush or commit time.)
     * @throws IllegalArgumentException     if instance is not an entity or is a removed entity
     * @throws TransactionRequiredException if there is no transaction when invoked on
     *                                      a container-managed entity manager of that is of type
     *                                      <code>PersistenceContextType.TRANSACTION</code>
     * @throws TransactionalException       if given object is null
     */
    public <T> T persist(T obj) {
        validateObject(obj);
        entityManager.persist(obj);
        return obj;
    }

    /**
     * Find by primary key.
     * Search for an entity of the specified class and primary key.
     * If the entity instance is contained in the persistence context,
     * it is returned from there.
     *
     * @param tClass entity class
     * @param id     primary key
     * @return the found entity instance or null if the entity does
     * not exist
     * @throws IllegalArgumentException if the first argument does
     *                                  not denote an entity type or the second argument is
     *                                  not a valid type for that entity's primary key or
     *                                  is null
     */
    public <T, R> T find(Class<T> tClass, R id) {
        getEntityManagerIfClosed();
        return entityManager.find(tClass, id);
    }

    /**
     * Remove the entity instance.
     *
     * @param obj entity instance
     * @throws IllegalArgumentException     if the instance is not an
     *                                      entity or is a detached entity
     * @throws TransactionRequiredException if invoked on a
     *                                      container-managed entity manager of type
     *                                      <code>PersistenceContextType.TRANSACTION</code> and there is
     *                                      no transaction
     * @throws TransactionalException       if given object is null
     */
    public <T> void remove(T obj) {
        validateObject(obj);
        entityManager.remove(obj);
    }

    /**
     * Merge the state of the given entity into the current persistence context.
     *
     * @param obj entity instance
     * @return the managed instance that the state was merged to
     * @throws IllegalArgumentException     if instance is not an entity or is a removed entity
     * @throws TransactionRequiredException if there is no transaction when invoked on
     *                                      a container-managed entity manager of that is of type
     *                                      <code>PersistenceContextType.TRANSACTION</code>
     * @throws TransactionalException       if given object is null
     */
    public <T> T merge(T obj) {
        validateObject(obj);
        entityManager.merge(obj);
        return obj;
    }

    /**
     * Start new transaction and execute method from args. If method executed successfully
     * then <code>commit</code> the transaction and return object from executed method,
     * else <code>rollback</code> the re-throw handled exception.
     * Also closes <code>EntityManager</code> instance.
     *
     * @param method method to be executed
     * @return object from executed method.
     */
    public <T> T transaction(Supplier<T> method) {
        begin();
        T obj;
        try {
            obj = method.get();
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        }
        closeEntityManager();
        return obj;
    }
    /**
     * Start new transaction and execute method from args. If method executed successfully
     * then <code>commit</code> the transaction,
     * else <code>rollback</code> the re-throw handled exception.
     * Also closes <code>EntityManager</code> instance.
     *
     * @param method method to be executed
     */
    public void transaction(Runnable method) {
        begin();
        try {
            method.run();
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        }
        closeEntityManager();
    }


    public Predicate collectParamsToPredicate(Map<String, Object> hashMap, Root<?> root, Predicate predicate){
        hashMap.forEach((k, v)->
                predicate.getExpressions().add(criteriaBuilder.equal(root.get(k), v))
        );
        return predicate;
    }
    public Predicate collectParamsToPredicate(Map<String, Object> hashMap, Join<?, ?> join, Predicate predicate){
        hashMap.forEach((k, v)->
                predicate.getExpressions().add(criteriaBuilder.equal(join.get(k), v))
        );
        return predicate;
    }

}


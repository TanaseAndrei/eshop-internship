package com.iquestgroup.implementations;

import com.iquestgroup.exceptions.DaoException;
import com.iquestgroup.exceptions.NoUserFoundException;
import com.iquestgroup.interfaces.UserDao;
import com.iquestgroup.models.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class UserDaoImpl<M extends User> extends GenericDaoImpl<User> implements UserDao<M> {
    private Class<M> type = getClassType();

    public UserDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public List<M> getUserList() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<M> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<M> root = criteriaQuery.from(type);
        TypedQuery<M> typedQuery = entityManager.createQuery(criteriaQuery);
        entityManager.close();
        return typedQuery.getResultList();
    }

    @Override
    public M getUserById(long id) throws NoUserFoundException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<M> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<M> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<M> typedQuery = entityManager.createQuery(criteriaQuery);
        try {
            return typedQuery.getSingleResult();
        } catch (NoResultException exception) {
            throw new NoUserFoundException("There is no user matching the search input", exception);
        } finally {
            entityManager.close();
        }
    }

    @Override
    public M getUserByUsername(String username) throws DaoException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<M> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<M> root = criteriaQuery.from(type);
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get("username"), username));
        M typedQuery;
        try {
            typedQuery = entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException noResultException) {
            throw new NoUserFoundException("The user does not exist", noResultException);
        } finally {
            entityManager.close();
        }
        return typedQuery;
    }

    private Class<M> getClassType() {
        Type superclassType = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) superclassType;
        type = (Class) parameterizedType.getActualTypeArguments()[0];
        return type;
    }
}

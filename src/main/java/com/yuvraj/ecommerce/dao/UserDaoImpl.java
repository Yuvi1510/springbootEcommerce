package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.Address;
import com.yuvraj.ecommerce.entity.Users;
import com.yuvraj.ecommerce.exceptionHandling.NotFountException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    private final EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Users saveUser(Users user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public Users findUserByEmail(String email) {
        TypedQuery<Users> query = entityManager.createQuery("FROM Users WHERE email=:userEmail", Users.class);
        query.setParameter("userEmail", email);

        try{
            return query.getSingleResult();
        }catch (NoResultException e){
           throw new NotFountException("User not found with email: "+ email);
        }
    }

    @Override
    public Users findUserById(int id) {
        TypedQuery<Users> query = entityManager.createQuery("FROM Users WHERE userId=:uid", Users.class);
        query.setParameter("uid", id);
        try{
            return query.getSingleResult();
        }catch (NoResultException e){
            throw new NotFountException("User not found with id: "+id);
        }
    }

    @Override
    public List<Users> findAllUsers() {
        TypedQuery<Users> query = entityManager.createQuery("FROM Users ",Users.class);
        return query.getResultList();
    }

    @Override
    public Users updateAddress(int userId, Address address) {
        Users user = findUserById(userId);

        user.updateAddress(address);

        entityManager.persist(user);

        return user;
    }

    @Override
    public Users updateUser(Users user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public void deleteUser(Users user) {
        entityManager.remove(user);
    }

}

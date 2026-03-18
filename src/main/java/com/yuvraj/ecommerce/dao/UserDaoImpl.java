package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.Address;
import com.yuvraj.ecommerce.entity.Users;
import com.yuvraj.ecommerce.exceptionHandling.AlreadyExists;
import com.yuvraj.ecommerce.exceptionHandling.NotFountException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;


//    public UserDaoImpl(EntityManager entityManager) {
//
//        this.entityManager = entityManager;
//    }

    @Override
    public Users saveUser(Users user) {
        if(findUserByEmail(user.getEmail()) != null){
            throw new AlreadyExists("User","email", user.getEmail());
        }
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
            return null;
        }
    }

    @Override
    public Users findUserById(int id) {
        TypedQuery<Users> query = entityManager.createQuery("FROM Users WHERE userId=:uid", Users.class);
        query.setParameter("uid", id);
        try{
            return query.getSingleResult();
        }catch (NoResultException e){
            return null;
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
        if(user == null){
            throw new NotFountException("User not found with id: "+userId);
        }

        user.updateAddress(address);
        entityManager.merge(user);

        return user;
    }

    @Override
    public Users updateUser(Users user, int id) {
        // from request i get a user with updated infos and also the id
        // so first check if user with that id exists or not
        Users userToUpdate = findUserById(id);
        if(userToUpdate == null){
            throw new NotFountException("User not found with id: "+id);
        }
        // if user exists then set the id of the user with updated infos
        user.setUserId(id);
        entityManager.merge(user);
        return user;
    }

    @Override
    public void deleteUser(int id) {
        Users user = findUserById(id);
        if(user == null){
            throw new NotFountException("User not found with id: "+id);
        }
        entityManager.remove(user);
    }

}

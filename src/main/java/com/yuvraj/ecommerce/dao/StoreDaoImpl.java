package com.yuvraj.ecommerce.dao;

import com.yuvraj.ecommerce.entity.Store;
import com.yuvraj.ecommerce.entity.Users;
import com.yuvraj.ecommerce.exceptionHandling.AlreadyExists;
import com.yuvraj.ecommerce.service.CustomUserPrincipal;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

@Repository
public class StoreDaoImpl implements StoreDao{
    private  final EntityManager entityManager;

    @Autowired
    public StoreDaoImpl(EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    @Override
    public Store addStore(Store store) {
        if(findStoreByEmail(store.getEmail()) != null){
            throw new  AlreadyExists("Store","email", store.getEmail());
        }
        if(findStoreByName(store.getName()) != null){
            throw new  AlreadyExists("Store","name", store.getName());
        }

        // get the currently authenticated user form the security context holder
        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // set user for the store so that foreign key will be set in the table
        store.setUser(userPrincipal.getUser());

        entityManager.persist(store);
        return store;
    }

    @Override
    public Store findStoreById(int id) {
        TypedQuery<Store> query = entityManager.createQuery("FROM Store WHERE storeId=:sId", Store.class);
        query.setParameter("sId", id);

        try{
           return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Store findStoreByName(String name) {
        TypedQuery<Store> query = entityManager.createQuery("FROM Store WHERE name=:storeName",Store.class);
        query.setParameter("storeName", name);

        try{
            return query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Store findStoreByEmail(String email) {
        TypedQuery<Store> query = entityManager.createQuery("FROM Store WHERE email=:storeEmail",Store.class);
        query.setParameter("storeEmail", email);

        try{
            return query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Store findStoreByUserId(int userId) {
        TypedQuery<Store> query = entityManager.createQuery("FROM Store s WHERE s.user.userId=:userId", Store.class);
        query.setParameter("userId", userId);

        try{
            return query.getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

}

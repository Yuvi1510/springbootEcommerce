package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.dao.StoreRepository;
import com.yuvraj.ecommerce.entity.Store;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.requests.StoreRegistrationRequest;
import com.yuvraj.ecommerce.service.StoreService;
import com.yuvraj.ecommerce.service.StoreServiceImpl;
import com.yuvraj.ecommerce.service.UserService;
import com.yuvraj.ecommerce.service.UserServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class StoreController {
    private final StoreService storeService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final StoreRepository storeRepository;

    @Autowired
    public StoreController(StoreServiceImpl storeService, UserServiceImpl userService, ModelMapper modelMapper,
                           StoreRepository storeRepository) {
        this.storeService = storeService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.storeRepository = storeRepository;
    }

    @PostMapping
    public ResponseEntity<Store> registerStore(@RequestBody StoreRegistrationRequest request){
        Store store = modelMapper.map(request, Store.class);
        User user = userService.findUserById(1);// hardcoded for testing
        store.setUser(user);

        Store newStore = storeService.registerStore(store);

        return ResponseEntity.ok().body(newStore);
    }
}

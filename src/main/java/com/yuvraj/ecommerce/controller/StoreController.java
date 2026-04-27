package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.dao.StoreRepository;
import com.yuvraj.ecommerce.entity.Product;
import com.yuvraj.ecommerce.entity.Store;
import com.yuvraj.ecommerce.entity.User;
import com.yuvraj.ecommerce.requests.StoreRegistrationRequest;
import com.yuvraj.ecommerce.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores")
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class StoreController {
    private final StoreService storeService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ProductService productService;

    @Autowired
    public StoreController(StoreService storeService, UserService userService, ModelMapper modelMapper,
                           StoreRepository storeRepository, ProductService productService) {
        this.storeService = storeService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Store> registerStore(@RequestBody StoreRegistrationRequest request){
        Store store = modelMapper.map(request, Store.class);
        User user = userService.findUserById(1);// hardcoded for testing
        store.setUser(user);

        Store newStore = storeService.registerStore(store);

        return ResponseEntity.ok().body(newStore);
    }

    @GetMapping("/{storeId}/products")
    public ResponseEntity<?> findProductsByStore(@PathVariable int storeId){
        List<Product> products = productService.findProductsByStore(storeId);
        System.out.println("controller");
        System.out.println(products);
        return ResponseEntity.ok().body(products);
    }
}

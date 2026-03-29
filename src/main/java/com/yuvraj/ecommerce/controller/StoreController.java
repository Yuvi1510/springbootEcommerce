package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.dao.StoreDao;
import com.yuvraj.ecommerce.entity.Users;
import com.yuvraj.ecommerce.responses.StoreDto;
import com.yuvraj.ecommerce.responses.StoreResponseDto;
import com.yuvraj.ecommerce.entity.Store;
import com.yuvraj.ecommerce.service.CustomUserPrincipal;
import com.yuvraj.ecommerce.service.StoreService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;
    private final ModelMapper modelMapper;

    public StoreController(StoreService storeService, ModelMapper modelMapper) {
        this.storeService = storeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/mystore")
    public ResponseEntity<StoreDto> getMyStore(){
        System.out.println("getting authentication");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // get currently authenticated user
        CustomUserPrincipal userPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        Users user = userPrincipal.getUser();

        System.out.println(userPrincipal);

        Store store = storeService.findStoreByUserId(user.getUserId());

        System.out.println(store);
        StoreDto storeDto = modelMapper.map(store, StoreDto.class);

        return ResponseEntity.ok().body(storeDto);

    }

    @PostMapping
    public ResponseEntity<StoreResponseDto> addStore(@RequestBody Store store){
        Store savedStore = storeService.addStore(store);
        StoreResponseDto storeResponseDto =  modelMapper.map(savedStore, StoreResponseDto.class);
        return ResponseEntity.ok().body(storeResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoreResponseDto> getStoreById(@PathVariable int id){
        Store store = storeService.findStoreById(id);
        StoreResponseDto storeResponseDto =  modelMapper.map(store, StoreResponseDto.class);
        return ResponseEntity.ok().body(storeResponseDto);
    }

//    @GetMapping(params = "name")
//    public ResponseEntity<StoreResponseDto> getStoreByName(@RequestParam String name){
//        Store store = storeService.findStoreByName(name);
//        StoreResponseDto storeResponseDto =  modelMapper.map(store, StoreResponseDto.class);
//        return ResponseEntity.ok().body(storeResponseDto);
//    }
//
//    @GetMapping(params = "email")
//    public ResponseEntity<StoreResponseDto> getStoreByEmail(@RequestParam String email){
//        Store store = storeService.findStoreByEmail(email);
//        StoreResponseDto storeResponseDto =  modelMapper.map(store, StoreResponseDto.class);
//
//        return ResponseEntity.ok().body(storeResponseDto);
//    }
}

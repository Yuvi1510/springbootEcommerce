package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.dto.StoreResponseDto;
import com.yuvraj.ecommerce.entity.Store;
import com.yuvraj.ecommerce.service.StoreService;
import org.modelmapper.ModelMapper;
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

    @PostMapping
    public StoreResponseDto addStore(@RequestBody Store store){
        Store savedStore = storeService.addStore(store);
        return modelMapper.map(savedStore, StoreResponseDto.class);
    }

    @GetMapping("/{id}")
    public StoreResponseDto getStoreById(@PathVariable int id){
        Store store = storeService.findStoreById(id);
        return modelMapper.map(store, StoreResponseDto.class);
    }

    @GetMapping(params = "name")
    public StoreResponseDto getStoreByName(@RequestParam String name){
        Store store = storeService.findStoreByName(name);
        return modelMapper.map(store, StoreResponseDto.class);
    }

    @GetMapping(params = "email")
    public StoreResponseDto getStoreByEmail(@RequestParam String email){
        Store store = storeService.findStoreByEmail(email);
        return modelMapper.map(store, StoreResponseDto.class);
    }
}

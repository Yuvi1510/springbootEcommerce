package com.yuvraj.ecommerce.controller;

import com.yuvraj.ecommerce.dto.StoreResponseDto;
import com.yuvraj.ecommerce.entity.Store;
import com.yuvraj.ecommerce.service.StoreService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(params = "name")
    public ResponseEntity<StoreResponseDto> getStoreByName(@RequestParam String name){
        Store store = storeService.findStoreByName(name);
        StoreResponseDto storeResponseDto =  modelMapper.map(store, StoreResponseDto.class);
        return ResponseEntity.ok().body(storeResponseDto);
    }

    @GetMapping(params = "email")
    public ResponseEntity<StoreResponseDto> getStoreByEmail(@RequestParam String email){
        Store store = storeService.findStoreByEmail(email);
        StoreResponseDto storeResponseDto =  modelMapper.map(store, StoreResponseDto.class);

        return ResponseEntity.ok().body(storeResponseDto);
    }
}

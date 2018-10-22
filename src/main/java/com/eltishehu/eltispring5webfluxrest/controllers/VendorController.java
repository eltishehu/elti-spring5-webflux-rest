package com.eltishehu.eltispring5webfluxrest.controllers;

import com.eltishehu.eltispring5webfluxrest.domain.Vendor;
import com.eltishehu.eltispring5webfluxrest.repositories.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by e.sh. on 22-Oct-18
 */
@RestController
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    //Mono is zero or one element
    //Flux is zero or many elements

    @GetMapping("/api/v1/vendors")
    @ResponseStatus(HttpStatus.OK)
    Flux<Vendor> list() {
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Vendor> getVendorById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @PostMapping("/api/v1/vendors")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream) {
        return vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping("/api/v1/vendors/{id}")
    @ResponseStatus(HttpStatus.OK) // this is the default response status; not necessarily required
    Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("/api/v1/vendors/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {

        Vendor foundVendor = vendorRepository.findById(id).block();

        if(!foundVendor.getFirstName().equals(vendor.getFirstName())){
            foundVendor.setFirstName(vendor.getFirstName());
            return vendorRepository.save(foundVendor);
        }

        /*if(!foundVendor.getFirstName().equals(vendor.getFirstName()) ||
                !foundVendor.getLastName().equals(vendor.getLastName())) {

            if(!foundVendor.getFirstName().equals(vendor.getFirstName())){
                foundVendor.setFirstName(vendor.getFirstName());
            }

            if(!foundVendor.getLastName().equals(vendor.getLastName())){
                foundVendor.setLastName(vendor.getLastName());
            }

            return vendorRepository.save(foundVendor);
        }*/

        return Mono.just(foundVendor);
    }
}

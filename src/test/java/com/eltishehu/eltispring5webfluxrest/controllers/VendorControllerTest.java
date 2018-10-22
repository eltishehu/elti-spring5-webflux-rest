package com.eltishehu.eltispring5webfluxrest.controllers;

import com.eltishehu.eltispring5webfluxrest.domain.Vendor;
import com.eltishehu.eltispring5webfluxrest.repositories.VendorRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * Created by e.sh. on 22-Oct-18
 */
public class VendorControllerTest {

    VendorRepository vendorRepository;
    VendorController vendorController;
    WebTestClient webTestClient;

    @Before
    public void setUp() throws Exception {

        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();

    }

    @Test
    public void list() {

        given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("Name1").lastName("Surname1").build(),
                        Vendor.builder().firstName("Name2").lastName("Surname2").build()));

        webTestClient.get().uri("/api/v1/vendors/")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);

    }

    @Test
    public void getVendorById() {

        given(vendorRepository.findById("someId"))
                .willReturn(Mono.just(Vendor.builder().firstName("name").lastName("surname").build()));

        webTestClient.get().uri("/api/v1/vendors/someId")
                .exchange()
                .expectBody(Vendor.class);

    }

    @Test
    public void create() {

        given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorToSaveMono = Mono.just(Vendor.builder()
                .firstName("First Name")
                .lastName("Last Name")
                .build());

        webTestClient.post().uri("/api/v1/vendors")
                .body(vendorToSaveMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void update() {

        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToUpdateMono = Mono.just(Vendor.builder().firstName("Fname").lastName("Lname").build());

        webTestClient.put().uri("/api/v1/vendors/asdfghjkl")
                .body(vendorToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

    }

    @Test
    public void patchWithChanges() {

        given(vendorRepository.findById(any(String.class)))
                .willReturn(Mono.just(Vendor.builder().firstName("Fname").build()));

        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToUpdateMono = Mono.just(Vendor.builder().firstName("New Fname").build());

        webTestClient.patch().uri("/api/v1/vendors/asdfghjkl")
                .body(vendorToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository).save(any());

    }

    @Test
    public void patchWithNoChanges() {

        given(vendorRepository.findById(any(String.class)))
                .willReturn(Mono.just(Vendor.builder().firstName("Steve").build()));

        given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToUpdateMono = Mono.just(Vendor.builder().firstName("Steve").build());

        webTestClient.patch().uri("/api/v1/vendors/asdfghjkl")
                .body(vendorToUpdateMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(vendorRepository, Mockito.never()).save(any());

    }
}
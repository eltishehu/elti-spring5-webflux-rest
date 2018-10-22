package com.eltishehu.eltispring5webfluxrest.repositories;

import com.eltishehu.eltispring5webfluxrest.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by e.sh. on 22-Oct-18
 */
public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {

}

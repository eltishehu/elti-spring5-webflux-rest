package com.eltishehu.eltispring5webfluxrest.repositories;

import com.eltishehu.eltispring5webfluxrest.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * Created by e.sh. on 19-Oct-18
 */
public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {

}

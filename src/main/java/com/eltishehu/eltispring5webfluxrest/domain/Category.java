package com.eltishehu.eltispring5webfluxrest.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by e.sh. on 19-Oct-18
 */
@Data
@Document
public class Category {

    @Id
    private String id;

    private String description;

}

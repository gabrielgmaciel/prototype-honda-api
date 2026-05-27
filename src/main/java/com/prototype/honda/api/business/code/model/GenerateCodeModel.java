package com.prototype.honda.api.business.code.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("generate_code")
public class GenerateCodeModel {

    @Id
    private String id;
    private long sequencial;
    private Integer yearSequential;

}

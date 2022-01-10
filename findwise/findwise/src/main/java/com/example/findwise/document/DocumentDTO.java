package com.example.findwise.document;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Data
@Accessors(chain = true)
@ToString
public class DocumentDTO {

    @NotBlank
    private String id;

    @NotBlank
    private String content;
}

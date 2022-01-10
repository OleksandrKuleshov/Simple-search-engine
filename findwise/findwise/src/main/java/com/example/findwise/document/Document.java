package com.example.findwise.document;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class Document {

    private int id;

    private String content;
}

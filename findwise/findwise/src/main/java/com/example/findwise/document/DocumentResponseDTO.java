package com.example.findwise.document;

import lombok.Data;

@Data
public class DocumentResponseDTO {

    private Document document;
    private double tfIdfScore;

    public DocumentResponseDTO(Document document, double tfIdfScore) {

        this.document = document;
        this.tfIdfScore = tfIdfScore;
    }
}

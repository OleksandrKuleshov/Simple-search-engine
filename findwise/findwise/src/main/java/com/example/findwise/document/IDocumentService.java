package com.example.findwise.document;

import java.util.List;

public interface IDocumentService {

    String DOCUMENT_WITH_ID_EXISTS_ERROR = "Already exists in the mock db, id=";

    void addDocument(DocumentDTO document);
    void addDocuments(List<DocumentDTO> documents);
    List<DocumentResponseDTO> findByTerm(String term);
}

package com.example.findwise.document;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;


@RestController
@RequestMapping("/api/document/")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    public static String SUCCESSFULLY_ADDED_DOCUMENT_MESSAGE = "Successfully added new document";
    public static String SUCCESSFULLY_ADDED_ALL_DOCUMENTS_MESSAGE = "Successfully added all new documents";

    private final DocumentService documentService;

    @PostMapping("/add")
    public ResponseEntity<String> addDocument(@Valid @RequestBody DocumentDTO document) {

        log.info("Received POST request for adding a document={}", document);

        documentService.addDocument(document);
        return ResponseEntity.ok(SUCCESSFULLY_ADDED_DOCUMENT_MESSAGE);

    }

    @PostMapping("/add-list")
    public ResponseEntity<String> addDocuments(@Valid @RequestBody List<DocumentDTO> documentDTOS) {

        log.info("Received POST request for adding a list of documents={}", documentDTOS);

        documentService.addDocuments(documentDTOS);
        return ResponseEntity.ok(SUCCESSFULLY_ADDED_ALL_DOCUMENTS_MESSAGE);

    }

    @GetMapping("/find")
    public ResponseEntity<List<DocumentResponseDTO>> findDocumentsByWord(@NotBlank @RequestParam String term) {

        log.info("Received GET request for search by term={}", term);

        List<DocumentResponseDTO> documentList = documentService.findByTerm(term);
        return ResponseEntity.ok(documentList);
    }

}
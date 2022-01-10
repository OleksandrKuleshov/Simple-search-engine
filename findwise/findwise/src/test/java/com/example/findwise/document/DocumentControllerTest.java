package com.example.findwise.document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static com.example.findwise.document.DocumentController.SUCCESSFULLY_ADDED_ALL_DOCUMENTS_MESSAGE;
import static com.example.findwise.document.DocumentController.SUCCESSFULLY_ADDED_DOCUMENT_MESSAGE;
import static com.example.findwise.document.IDocumentService.DOCUMENT_WITH_ID_EXISTS_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentControllerTest {

    private static final String ID = "1";
    private static final String CONTENT = "Test content";

    @Mock
    private DocumentService documentService;

    private DocumentController documentController;

    @BeforeEach
    void init() {

        documentController = new DocumentController(documentService);
    }

    @Test
    void addDocumentShouldOk() {

        ResponseEntity<String> expectedResponse = ResponseEntity.ok(SUCCESSFULLY_ADDED_DOCUMENT_MESSAGE);

        doNothing().when(documentService).addDocument(any());

        ResponseEntity<String> actualResponse = documentController.addDocument(documentDTO());

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void addDocumentsShouldOk() {

        ResponseEntity<String> expectedResponse = ResponseEntity.ok(SUCCESSFULLY_ADDED_ALL_DOCUMENTS_MESSAGE);

        doNothing().when(documentService).addDocuments(any());

        ResponseEntity<String> actualResponse = documentController.addDocuments(Collections.singletonList(documentDTO()));

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void addDocumentWithExistentDocumentIdShouldThrow() {

        doThrow(new IllegalArgumentException(DOCUMENT_WITH_ID_EXISTS_ERROR + ID)).when(documentService).addDocument(any());

        IllegalArgumentException expectedException = new IllegalArgumentException(DOCUMENT_WITH_ID_EXISTS_ERROR + ID);

        IllegalArgumentException actualException
                = assertThrows(IllegalArgumentException.class, () -> documentController.addDocument(any()));

        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void findDocumentsByWordShouldReturnEmptyList() {

        when(documentService.findByTerm(any())).thenReturn(Collections.emptyList());

        ResponseEntity<List<DocumentResponseDTO>> expectedResponse = ResponseEntity.ok(Collections.emptyList());

        ResponseEntity<List<DocumentResponseDTO>> actualResponse = documentController.findDocumentsByWord("Test");

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void findDocumentsByWordShouldReturnListWithDocuments() {

        when(documentService.findByTerm(any())).thenReturn(documentResponseDTOS());

        ResponseEntity<List<DocumentResponseDTO>> expectedResponse
                = ResponseEntity.ok(documentResponseDTOS());

        ResponseEntity<List<DocumentResponseDTO>> actualResponse = documentController.findDocumentsByWord("Test");

        assertEquals(expectedResponse, actualResponse);
    }

    private List<DocumentResponseDTO> documentResponseDTOS() {

        return Collections.singletonList(new DocumentResponseDTO(document(), 0.5));
    }

    private Document document() {

        return new Document().setId(Integer.parseInt(ID)).setContent(CONTENT);
    }

    private DocumentDTO documentDTO() {

        return new DocumentDTO().setId(ID).setContent(CONTENT);
    }

}

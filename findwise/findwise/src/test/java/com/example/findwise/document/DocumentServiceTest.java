package com.example.findwise.document;


import com.example.findwise.data.InvertedIndex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.findwise.document.IDocumentService.DOCUMENT_WITH_ID_EXISTS_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    private static final int ID = 1;
    private static final String CONTENT = "Test content";

    private static final Document DOCUMENT1 = new Document().setId(1).setContent("The brown fox jumped over the brown dog.");
    private static final Document DOCUMENT2 = new Document().setId(2).setContent("The lazy brown dog, sat in the corner");
    private static final Document DOCUMENT3 = new Document().setId(3).setContent("The Red Fox bit the lazy dog!");

    private Map<Integer, Document> allDocuments;

    @Mock
    private InvertedIndex invertedIndex;

    private DocumentService documentService;

    @BeforeEach
    void init() {

        DocumentMapper documentMapper = new DocumentMapperImpl();

        allDocuments = new HashMap<>();
        documentService = new DocumentService(allDocuments, invertedIndex, documentMapper);
    }

    @Test
    void addDocumentShouldOk() {

        doNothing().when(invertedIndex).addDocument(any());

        documentService.addDocument(documentDTO(ID, CONTENT));
    }

    @Test
    void addDocumentShouldWithDuplicateDocumentIdShouldThrow() {

        Document document = document(ID, CONTENT);

        allDocuments.put(document.getId(), document);

        IllegalArgumentException expectedException = new IllegalArgumentException(DOCUMENT_WITH_ID_EXISTS_ERROR + ID);

        IllegalArgumentException actualException = assertThrows(IllegalArgumentException.class,
                () -> documentService.addDocument(documentDTO(ID, CONTENT)));

        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }

    @Test
    void findByWordShouldReturnEmptyList() {

        Document document = document(ID, CONTENT);

        allDocuments.put(document.getId(), document);

        List<DocumentResponseDTO> actualResult = documentService.findByTerm("Penna");

        assertEquals(Collections.emptyList(), actualResult);
    }

    @ParameterizedTest
    @MethodSource("source")
    void findByWordShouldReturnList(String term, List<Integer> documentIds, List<Document> expectedResult) {

        allDocuments.put(DOCUMENT1.getId(), DOCUMENT1);
        allDocuments.put(DOCUMENT2.getId(), DOCUMENT2);
        allDocuments.put(DOCUMENT3.getId(), DOCUMENT3);

        when(invertedIndex.search(any())).thenReturn(documentIds);

        List<Document> actualResult
                = documentService.findByTerm(term)
                .stream()
                .map(DocumentResponseDTO::getDocument)
                .collect(Collectors.toList());

        assertEquals(expectedResult, actualResult);
    }


    private static Stream<Arguments> source() {

        return Stream.of(
                Arguments.of("fox",
                        List.of(1, 3),
                        List.of(DOCUMENT1,
                                DOCUMENT3),

                        Arguments.of("brown",
                                List.of(1, 2),
                                List.of(DOCUMENT1,
                                        DOCUMENT2
                                )
                        ),

                        Arguments.of("dog",
                                List.of(1, 2, 3),
                                List.of(DOCUMENT3,
                                        DOCUMENT3,
                                        DOCUMENT3
                                )
                        )
                )
        );
    }


    private DocumentResponseDTO documentResponseDTO() {

        return new DocumentResponseDTO(document(ID, CONTENT), 0);
    }

    private Document document(int id, String content) {

        return new Document().setId(id).setContent(content);
    }

    private DocumentDTO documentDTO(int id, String content) {

        return new DocumentDTO().setId(String.valueOf(id)).setContent(content);
    }
}

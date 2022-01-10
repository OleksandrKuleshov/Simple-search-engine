package com.example.findwise.document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentMapperTest {

    private final String TEST_ID = "1";
    private final String TEST_CONTENT = "Test";

    private DocumentMapper documentMapper;

    @BeforeEach
    void init() {

        this.documentMapper = new DocumentMapperImpl();
    }

    @Test
    void convertToDocumentResponseDTOShouldOk() {

        Document document = new Document().setId(Integer.parseInt(TEST_ID)).setContent(TEST_CONTENT);

        DocumentResponseDTO expectedResult = new DocumentResponseDTO(document, 0.2);

        DocumentResponseDTO actualResult = documentMapper.convertToDocumentResponseDto(document, 0.2);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void convertToDocumentShouldOk() {

        Document expectedResult = new Document().setId(Integer.parseInt(TEST_ID)).setContent(TEST_CONTENT);

        DocumentDTO documentDTO = new DocumentDTO().setId(TEST_ID).setContent(TEST_CONTENT);

        Document actualResult = documentMapper.convertToDocument(documentDTO);

        assertEquals(expectedResult, actualResult);
    }

}

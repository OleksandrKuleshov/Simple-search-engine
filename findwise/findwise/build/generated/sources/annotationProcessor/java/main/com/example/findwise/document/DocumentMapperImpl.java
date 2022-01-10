package com.example.findwise.document;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-06T12:27:06+0100",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.3.2.jar, environment: Java 11.0.13 (Amazon.com Inc.)"
)
@Component
public class DocumentMapperImpl extends DocumentMapper {

    @Override
    public Document convertToDocument(DocumentDTO documentDTO) {
        if ( documentDTO == null ) {
            return null;
        }

        Document document = new Document();

        if ( documentDTO.getId() != null ) {
            document.setId( Integer.parseInt( documentDTO.getId() ) );
        }
        document.setContent( documentDTO.getContent() );

        return document;
    }

    @Override
    public DocumentResponseDTO convertToDocumentResponseDto(Document document, double tfIdfScore) {
        if ( document == null ) {
            return null;
        }

        Document document1 = null;
        if ( document != null ) {
            document1 = document;
        }
        double tfIdfScore1 = 0.0d;
        tfIdfScore1 = tfIdfScore;

        DocumentResponseDTO documentResponseDTO = new DocumentResponseDTO( document1, tfIdfScore1 );

        return documentResponseDTO;
    }
}

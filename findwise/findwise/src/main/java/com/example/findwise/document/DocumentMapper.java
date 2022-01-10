package com.example.findwise.document;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class DocumentMapper {

    @Mapping(target = "id", source = "documentDTO.id")
    @Mapping(target = "content", source = "documentDTO.content")
    public abstract Document convertToDocument(DocumentDTO documentDTO);

    @Mapping(target = "document", source = "document")
    @Mapping(target = "tfIdfScore", source = "tfIdfScore")
    public abstract DocumentResponseDTO convertToDocumentResponseDto(Document document, double tfIdfScore);
}

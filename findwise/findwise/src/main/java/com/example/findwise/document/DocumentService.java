package com.example.findwise.document;

import com.example.findwise.data.InvertedIndex;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class DocumentService implements IDocumentService {

    //mock db
    private final Map<Integer, Document> allDocuments;

    private final InvertedIndex invertedIndex;

    private final DocumentMapper documentMapper;

    @Override
    public void addDocument(DocumentDTO documentDto) {

        Integer newDocumentId = Integer.parseInt(documentDto.getId());

        if(ObjectUtils.isEmpty(allDocuments.get(newDocumentId))) {

            allDocuments.put(newDocumentId, documentMapper.convertToDocument(documentDto));
            invertedIndex.addDocument(documentMapper.convertToDocument(documentDto));
        } else {

            log.error("Failed to add new document, documentId={} already exists in the mock db", newDocumentId);
            throw new IllegalArgumentException(DOCUMENT_WITH_ID_EXISTS_ERROR + newDocumentId);
        }
    }

    @Override
    public void addDocuments(List<DocumentDTO> documentDTOS) {

        documentDTOS.forEach(this::addDocument);
    }

    @Override
    public List<DocumentResponseDTO> findByTerm(String term) {

        List<Integer> documentIds = invertedIndex.search(term);

        if (ObjectUtils.isEmpty(documentIds)) {

            return Collections.emptyList();
        }

        return documentIds.stream()
                .map(allDocuments::get)
                .distinct()
                .map(document -> documentMapper.convertToDocumentResponseDto(document, countTfIdf(term, document)))
                .sorted(Comparator.comparingDouble(DocumentResponseDTO::getTfIdfScore).reversed())
                .collect(Collectors.toList());
    }

    private double countTfIdf(String term, Document document) {

        return getTf(term, document) * getIdf(term);
    }

    private double getTf(String term, Document document) {

        String regexRemovesMarks = "[^a-zA-Z ]";
        String[] termsInDocument
                = document.getContent().toLowerCase().replaceAll(regexRemovesMarks, "").split(" ");

        long termsCount = Arrays.stream(termsInDocument).filter(str -> str.equalsIgnoreCase(term)).count();

        return (double) termsCount / allDocuments.size();
    }

    private double getIdf(String term) {

        long numberOfDocumentsWhereTermAppears = allDocuments.values()
                .stream()
                .map(Document::getContent)
                .filter(str -> str.toLowerCase().contains(term.toLowerCase()))
                .count();

        return Math.log10((double) (allDocuments.size() + 1) / numberOfDocumentsWhereTermAppears);
    }
}

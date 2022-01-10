package com.example.findwise.data;

import com.example.findwise.document.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InvertedIndex {

    private final Map<String, List<Integer>> invertedIndexMap;

    public InvertedIndex() {

        invertedIndexMap = new HashMap<>();
    }

    public void addDocument(Document document) {

        processAndAddDocument(document);
    }

    public List<Integer> search(String term) {

       return invertedIndexMap.get(term);
    }

    private void processAndAddDocument(Document document) {

        String regexRemovesMarks = "[^a-zA-Z ]";
        String[] words = document.getContent().toLowerCase().replaceAll(regexRemovesMarks, "").split(" ");

        int documentId = document.getId();

        for (String word : words) {

            List<Integer> existingDocumentList = invertedIndexMap.get(word);

            if (ObjectUtils.isEmpty(existingDocumentList)) {

                List<Integer> newDocumentList = new ArrayList<>();
                newDocumentList.add(documentId);
                invertedIndexMap.put(word, newDocumentList);

            } else if(!existingDocumentList.contains(documentId)) {

                existingDocumentList.add(documentId);
            }
        }

    }
}

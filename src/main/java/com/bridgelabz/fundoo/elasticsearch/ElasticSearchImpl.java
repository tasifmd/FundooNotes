package com.bridgelabz.fundoo.elasticsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoo.notes.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchImpl implements ElasticSearch{
	
	private final String INDEX = "noteindex";
	private final String TYPE = "notetype";  

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	public Note create(Note note) {
		@SuppressWarnings("unchecked")
		Map<String, Object> dataMap = objectMapper.convertValue(note, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX,TYPE,String.valueOf(note.getId())).source(dataMap);
		try {
			client.index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return note;
	}
	
	public Note updateNote(Note note) {
		System.out.println("Update");
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, String.valueOf(note.getId()));
		@SuppressWarnings("unchecked")
		Map<String, Object> documentMapper = objectMapper.convertValue(note, Map.class);
		updateRequest.doc(documentMapper);
		try {
			@SuppressWarnings("unused")
			UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
			System.out.println("Update SuccessFully");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return note;
	}
	
	@SuppressWarnings("unused")
	public void deleteNote(Long NoteId) {
		System.out.println("Delete");
		DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, String.valueOf(NoteId));
		try {
			DeleteResponse response = client.delete(deleteRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public List<Note> searchData(String query, long userId) {
		SearchRequest searchRequest = new SearchRequest(INDEX).types(TYPE);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder queryBuilder = QueryBuilders.boolQuery()
				.must(QueryBuilders.queryStringQuery("*" + query + "*").analyzeWildcard(true).field("title")
				.field("description"))
				.filter(QueryBuilders.termsQuery("userId", String.valueOf(userId)));
		System.out.println();
		searchSourceBuilder.query(queryBuilder);
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = null;
		try {
			searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
			System.out.println(searchResponse);
		} catch (IOException e) {

			e.printStackTrace();
		}

		List<Note> allnote = getSearchResult(searchResponse);

		return allnote;
	}

	private List<Note> getSearchResult(SearchResponse response) {
		SearchHit[] searchHits = response.getHits().getHits();
		List<Note> notes = new ArrayList<>();
		for (SearchHit hit : searchHits) {
			notes.add(objectMapper.convertValue(hit.getSourceAsMap(), Note.class));
		} 
		System.out.println(notes);
		return notes;
	}
}

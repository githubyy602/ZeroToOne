package com.yangy.search.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.mapping.TypeMapping;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexRequest;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.PutMappingRequest;
import co.elastic.clients.elasticsearch.indices.PutMappingResponse;
import co.elastic.clients.json.JsonpMapper;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.json.Json;
import jakarta.json.stream.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Yangy
 * @Date: 2023/7/10 14:47
 * @Description
 * 参考：https://blog.csdn.net/qq_51553982/article/details/127738852
 */
@Service
public class EsServiceImpl {
	
	@Autowired
	@Lazy
	private ElasticsearchClient esClient;
	
	private RestClient restClient;
	
	private RestClientTransport transport;
	
	private ResourceLoader resourceLoader;
	
	private final static String INDEX_NAME = "haha-texts-index";
	
	private final static ObjectMapper JACKSON_MAPPER = new ObjectMapper();

	@Autowired
	public EsServiceImpl(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@Bean("esClient")
	public ElasticsearchClient init(){
		//创建一个低级的客户端
		restClient = RestClient.
				builder(new HttpHost("localhost",9200))
				.build();
		
		//创建JSON对象映射器
        transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        
        //创建API客户端
        final ElasticsearchClient client = new ElasticsearchClient(transport);
        
        return client;
	}
	
	@PreDestroy
	public void shutdown() throws IOException {
		if(Objects.nonNull(esClient)){
			esClient.shutdown();
		}

		if(Objects.nonNull(transport)){
			transport.close();
		}

		if(Objects.nonNull(restClient)){
			restClient.close();
		}
	}
	
	/**
	* @Author Yangy
	* @Description 仅创建索引
	* @Date 18:28 2023/7/17
	* @Param []
	* @return java.lang.String
	**/
	public String operateOfCreateIndex() throws IOException {
		boolean exist = esClient.indices().exists(query->query.index(INDEX_NAME)).value();
		if(exist){
			System.out.println("索引已存在");
			return null;
		}else {
			CreateIndexResponse response = esClient.indices().create(builder->builder.index(INDEX_NAME));
			return response.index();
		}
	}
	
	public boolean operateOfDelIndex() throws IOException {
		boolean exist = esClient.indices().exists(query->query.index(INDEX_NAME)).value();
		if(exist){
			return esClient.indices().delete(del->del.index(INDEX_NAME)).acknowledged();
		}else {
			System.out.println("索引不存在");
			return false;
		}
	}
	
	/**
	* @Author Yangy
	* @Description 既创建索引又创建mapping
	* @Date 18:28 2023/7/17
	* @Param []
	* @return java.lang.String
	**/
	public boolean operateOfCreateAndAddMapping() throws IOException {
		//方式一
		Resource resource = resourceLoader.getResource("classpath:mapping.json");
		JsonpMapper mapper = esClient._transport().jsonpMapper();
		JsonParser parser = Json.createParser(resource.getInputStream());
		CreateIndexRequest requestCreateMapping =  new CreateIndexRequest.Builder()
                .index(INDEX_NAME)
                .mappings(TypeMapping._DESERIALIZER.deserialize(parser, mapper))
                .build();
		CreateIndexResponse response = esClient.indices().create(requestCreateMapping);
		
		//方式二
//		CreateIndexResponse response = esClient.indices().create(builder ->
//                builder.settings(indexSetting -> indexSetting.numberOfReplicas("1").numberOfShards("1")).mappings(
//                        map -> map
//                                .properties("name", propertyBuilder -> propertyBuilder.keyword(keywordProperty -> keywordProperty))
//                                .properties("price", propertyBuilder -> propertyBuilder.double_(doubleNumProperty -> doubleNumProperty))
//                                .properties("des", propertyBuilder -> propertyBuilder.text(textProperty -> textProperty.analyzer("ik_smart").searchAnalyzer("ik_smart")))
//                ).index("produces")
//
//        );
		
		return response.acknowledged();
	}
	
	public boolean operateOfCreateMapping() throws IOException {
		Resource resource = resourceLoader.getResource("classpath:mapping.json");
        
        // 定义 Mapping
        PutMappingRequest mappingRequest = new PutMappingRequest.Builder()
				.index(INDEX_NAME)
				.withJson(resource.getInputStream())
                .build();

        PutMappingResponse mappingResponse = esClient.indices().putMapping(mappingRequest);
		
		return mappingResponse.acknowledged();
	}
	
	/**
	* @Author Yangy
	* @Description 通过json文件导入doc数据
	* @Date 14:10 2023/7/20
	* @Param []
	* @return boolean
	**/
	public boolean operateOfAddDocByFile(){
		Resource docJsonResc = resourceLoader.getResource("classpath:doc1.json");
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			Map<String, Object> dataMap = objectMapper.readValue(docJsonResc.getInputStream(), Map.class);
			esClient.index(builder -> builder.index(INDEX_NAME).
					document(dataMap));
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	class StructInfo {
		public String zh_CN;
		public String en_US;
	}
	
	/**
	* @Author Yangy
	* @Description 通过实体类或map形式新增doc
	* @Date 14:22 2023/7/20
	* @Param []
	* @return long
	**/
	public long operateOfAddDocByClass() throws IOException {
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("id","1");
		dataMap.put("contents",new StructInfo("一条测试内容","test content"));
		dataMap.put("titles",new StructInfo("测试标题","test title"));
		dataMap.put("channels","app");
		
		IndexResponse response = esClient.index(builder -> 
				builder.index(INDEX_NAME).id(dataMap.get("id").toString())
				.document(dataMap));
		return response.version();
	}
	
	public String searchAllData() throws IOException {
		SearchResponse<Map> response = esClient.search(query->query.index(INDEX_NAME),Map.class);
		List<Map> mapList = new ArrayList<>();
		response.hits().hits().stream()
				.forEach(r->{
					mapList.add(r.source());
				});
		return JACKSON_MAPPER.writeValueAsString(mapList);
	}
	
	public String getData() throws IOException {
		GetResponse<Map> response = esClient.get(query->query.index(INDEX_NAME).id("1"),Map.class);
		Map data = response.source();
		return JACKSON_MAPPER.writeValueAsString(data);
	}
	
	public Object updateData() throws IOException {
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("id","1");
		dataMap.put("contents",new StructInfo("一条测试内容 V2","test content V2"));
		dataMap.put("titles",new StructInfo("测试标题 V2","test title V2"));
		dataMap.put("channels","app");
		
		//全覆盖
		return esClient.update(builder -> builder.
				index(INDEX_NAME).id("1").doc(dataMap), Map.class)
				.shards().successful().intValue();
	}
	
	
	public Object updateDataField() throws IOException {
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("id","1");
		dataMap.put("contents",new StructInfo("一条测试内容 V3","test content V3"));
		dataMap.put("titles",new StructInfo("测试标题 V3","test title V3"));
		dataMap.put("channels","app");
		
		//修改部分字段
		return esClient.update(builder -> builder.
				index(INDEX_NAME).id("1").doc(dataMap).docAsUpsert(true), Map.class)
				.shards().successful().intValue();
	}
	
	public Object delData() throws IOException {
		DeleteResponse response = esClient.delete(builder -> builder.index(INDEX_NAME).id("1"));
		return response.shards().successful().intValue();
	}
}

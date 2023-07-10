package com.yangy.search.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;

/**
 * @Author: Yangy
 * @Date: 2023/7/10 14:47
 * @Description
 */
@Service
public class EsServiceImpl {
	
	@Autowired
	private ElasticsearchClient esClient;
	
	@PostConstruct
	@Bean("esClient")
	public ElasticsearchClient init(){
		//创建一个低级的客户端
		final RestClient restClient = RestClient.
				builder(new HttpHost("localhost",9200)).build();
		
		//创建JSON对象映射器
        final RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        
        //创建API客户端
        final ElasticsearchClient client = new ElasticsearchClient(transport);
        
        return client;
	}
	
	@PreDestroy
	public void shutdown(){
		if(Objects.nonNull(esClient)){
			esClient.shutdown();
		}
	}
	
}

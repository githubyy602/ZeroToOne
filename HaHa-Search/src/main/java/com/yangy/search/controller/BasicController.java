package com.yangy.search.controller;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import com.yangy.common.bean.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author: Yangy
 * @Date: 2023/7/10 14:48
 * @Description
 */
@RequestMapping("/basic")
@RestController
public class BasicController {
	
	//todo 1、存入index 2、查询index内的内容 3、更新index再查询 4、删除index
	@Autowired
	private ElasticsearchClient esClient;
	
	@PostMapping("/getData")
	public ResultBean getData() throws IOException {
		GetIndexResponse resp = esClient.indices().get(q -> q.index("_all"));
		return ResultBean.success(resp.toString());
	}
	
	@PostMapping("/insertData")
	public ResultBean insertData() throws IOException {
//		esClient.putScript(p->p.id(""));
		return null;
	}
}

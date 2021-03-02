package com.ygy.study.esdemo;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class EsDemoApplicationTests {


	@Autowired
	RestHighLevelClient restHighLevelClient;

	@Test
	public void test() throws IOException {

		final GetIndexRequest indexRequest = new GetIndexRequest("article");
		final boolean exists = restHighLevelClient.indices().exists(indexRequest,RequestOptions.DEFAULT);
		System.out.println(exists);
	}

	@Test
	void create() throws IOException {

		Article article = new Article();
		article.setId(1);
		article.setTitle("好消息");
		article.setAuthor("张三");
		article.setContent("Hello Word");

		IndexRequest indexRequest = new IndexRequest();
		indexRequest.index("article3").id("3").source(JSON.toJSON(article), XContentType.JSON);

		Cancellable indexResponse = restHighLevelClient.indexAsync(indexRequest, RequestOptions.DEFAULT,new ActionListener<IndexResponse>() {
			@Override
			public void onResponse(IndexResponse indexResponse) {
				System.out.println("sucess----------------------------");
			}

			@Override
			public void onFailure(Exception e) {
				System.out.println("fail----------------------------");
				e.printStackTrace();
			}
		});
		System.in.read();

	}

	@Test
	void get() throws IOException {
		GetRequest getRequest = new GetRequest(
				"article3",
				"3");

		GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
		System.out.println(JSON.toJSON(getResponse));
	}

	@Test
	void search() throws IOException {
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//		sourceBuilder.query(QueryBuilders.termQuery("author", "张三"));
		sourceBuilder.from(0);
		sourceBuilder.size(5);
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("article3").source(sourceBuilder);

		SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		System.out.println(JSON.toJSON(searchResponse));

		for (SearchHit fields : searchResponse.getHits()) {
			System.out.println(JSON.toJSON(fields));
		}

	}

	@Test
	void name() throws IOException {
		BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("author", "张");//这里可以根据字段进行搜索，must表示符合条件的，相反的mustnot表示不符合条件的
		// RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("fields_timestamp"); //新建range条件
		// rangeQueryBuilder.gte("2019-03-21T08:24:37.873Z"); //开始时间
		// rangeQueryBuilder.lte("2019-03-21T08:24:37.873Z"); //结束时间
		// boolBuilder.must(rangeQueryBuilder);
		boolBuilder.must(matchQueryBuilder);
		sourceBuilder.query(boolBuilder); //设置查询，可以是任何类型的QueryBuilder。
		sourceBuilder.from(0); //设置确定结果要从哪个索引开始搜索的from选项，默认为0
		sourceBuilder.size(100); //设置确定搜素命中返回数的size选项，默认为10
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); //设置一个可选的超时，控制允许搜索的时间。


//		sourceBuilder.fetchSource(new String[] {"fields.port","fields.entity_id","fields.message"}, new String[] {}); //第一个是获取字段，第二个是过滤的字段，默认获取全部
		SearchRequest searchRequest = new SearchRequest("article3"); //索引
//		searchRequest.types("doc"); //类型
		searchRequest.source(sourceBuilder);


		SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
		SearchHits hits = response.getHits();  //SearchHits提供有关所有匹配的全局信息，例如总命中数或最高分数：
		SearchHit[] searchHits = hits.getHits();
		for (SearchHit hit : searchHits) {
			System.out.println(hit.getSourceAsString());
		}
	}

}

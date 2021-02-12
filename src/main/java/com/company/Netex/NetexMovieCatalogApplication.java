package com.company.Netex;



import com.company.Netex.Controller.MovieCatalogController;
import com.company.Netex.Model.MovieCatalogModel;

import com.company.Netex.Repo.MovieCatalogRepository;
import com.company.Netex.Service.MovieCatalogService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import com.sun.xml.bind.v2.runtime.output.SAXOutput;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.apache.solr.common.params.MapSolrParams;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


@SpringBootApplication
public class NetexMovieCatalogApplication  {

	public void query() {
		String urlString = "http://localhost:8983/solr/mycol1";
		HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
		solr.setParser(new XMLResponseParser());


	}
	public static void main(String[] args) throws IOException, SolrServerException {
			SpringApplication.run(NetexMovieCatalogApplication.class, args);




		try {
			NetexMovieCatalogApplication.convert_json_to_java();
		}catch (Exception e){
			System.out.println(e);
		}


		}
	@Bean
	CommandLineRunner runner(MovieCatalogService movieCatalogService) {
		return args -> {
			//read json and write to mariadb
			String urlString = "http://localhost:8983/solr/mycol1";
			HttpSolrClient solr = new HttpSolrClient.Builder(urlString).build();
			solr.setParser(new XMLResponseParser());

			// constructs a MapSolrParams instance
			final Map<String, String> queryParamMap = new HashMap<String, String>();
			queryParamMap.put("q", "title:avengers"); // search articles written by Kevin Yang
			queryParamMap.put("fl", "id, type");
			queryParamMap.put("sort", "id asc");
			MapSolrParams queryParams = new MapSolrParams(queryParamMap);

			// sends search request and gets the response
			QueryResponse response = null;
			try {
				response = solr.query(queryParams);
			} catch (SolrServerException | IOException e) {
				System.err.printf("Failed to search articles: %s", e.getMessage());
			}

			// print results to stdout
			if (response != null) {
				System.out.println(response.getResults());

			}


			ObjectMapper mapper = new ObjectMapper();
			TypeReference<List<MovieCatalogModel>> typeReference = new TypeReference<List<MovieCatalogModel>>() {
			};
			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/users.json");
			try {
				List<MovieCatalogModel> movies = mapper.readValue(inputStream, typeReference);

				for (int i = 0; i < movies.size(); i++) {

					SolrInputDocument document = new SolrInputDocument();
					MovieCatalogModel obj = new MovieCatalogModel();
					obj = movies.get(i);
					document.addField("id", i);
					document.addField("title", obj.getTitle());
					document.addField("year", obj.getYear());
					document.addField("imdbID", obj.getImdb());
					document.addField("type", obj.getType());
					document.addField("poster", obj.getPoster());
					//solr.add(document);

					//solr.commit();
				}
				System.out.println("Movies Saved!");
			} catch (IOException e) {
				System.out.println("Unable to save movies: " + e.getMessage());
			}
			
		};



	}

	public static void convert_json_to_java()throws Exception{
		MovieCatalogService movieCatalogService = new MovieCatalogService();
		FileWriter file = new FileWriter("/home/johnny/InteliJProjects/Netex2/src/main/resources/json/users.json");

		file.write("[");


		for (int j = 1; j < 11; j++) {


			ObjectMapper mapper = new ObjectMapper();
			String url = "http://www.omdbapi.com/?s=avengers&apikey=1ecf6bbe&page="+j;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			JSONObject jsonObject = new JSONObject(response.toString());
			JSONArray jsonArray = jsonObject.getJSONArray("Search");

			for (int i = 0; i < jsonArray.length(); i++) {
				String jsonObject1 = jsonArray.getJSONObject(i).toString();

				file.write(jsonObject1 + "," + " \n");

			}


		}

		file.write("{\"Type\":\"game\",\"Year\":\"2020\",\"imdbID\":\"tt6468680\",\"Poster\":\"https://m.media-amazon.com/images/M/MV5BNWYyYjc0OWItNmVkOS00NDViLWE0MjAtNjg3NjNjZjQ1YTA2XkEyXkFqcGdeQXVyNjg2NjQwMDQ@._V1_SX300.jpg\",\"Title\":\"Marvel's Avengers\"} \n");
		file.write("]");
		file.close();
	}
	}




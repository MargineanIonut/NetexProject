package com.company.Netex.Model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.jfr.Enabled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "movies")
@JsonIgnoreProperties(value = {"totalResults","Response"})
public class MovieCatalogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Field("id")
    private int id;
    @JsonProperty("Title")
    @Field("title")
    private String title;
    @JsonProperty("Year")
    @Field("year")
    private String year;
    @JsonProperty("imdbID")
    @Field("imdbID")
    private String imdb;
    @JsonProperty("Type")
    @Field("type")
    private String type;
    @JsonProperty("Poster")
    @Field("poster")
    private String poster;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}

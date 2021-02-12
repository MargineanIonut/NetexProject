package com.company.Netex.Repo;

import com.company.Netex.Model.MovieCatalogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.Optional;

public interface MovieCatalogRepository extends JpaRepository<MovieCatalogModel, Integer> {

    Optional<MovieCatalogModel> findByTitle(String title);
}

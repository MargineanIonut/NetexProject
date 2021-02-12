package com.company.Netex.Service;

import com.company.Netex.Model.MovieCatalogModel;
import com.company.Netex.Model.QMovieCatalogModel;
import com.company.Netex.Repo.MovieCatalogRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

@Service
public class MovieCatalogService {

    @Autowired
    private MovieCatalogRepository repository;

    public JPAQueryFactory queryFactory;

    public MovieCatalogModel saveMovie(MovieCatalogModel movie){
        return repository.save(movie);
    }



    public List<MovieCatalogModel> getAlphabeticTitleOrder(){

        List<MovieCatalogModel> movies = queryFactory.selectFrom( QMovieCatalogModel.movieCatalogModel).orderBy(QMovieCatalogModel.movieCatalogModel.title.asc()).fetch();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Movies");
        EntityManager em = emf.createEntityManager();
        queryFactory = new JPAQueryFactory(em);
        return movies;
        }

        public List<MovieCatalogModel> getYear(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Movies");
        EntityManager em = emf.createEntityManager();
        queryFactory = new JPAQueryFactory(em);
        List<MovieCatalogModel> movies = queryFactory.selectFrom( QMovieCatalogModel.movieCatalogModel).where(QMovieCatalogModel.movieCatalogModel.type.eq("movie")).fetch();

        return movies;
        }




    public List<MovieCatalogModel> saveMovie(List<MovieCatalogModel> movie){
        return repository.saveAll(movie);
    }

    public List<MovieCatalogModel> getMovies(){
        return repository.findAll();
    }

    public MovieCatalogModel getMoviesById(int id){
        return repository.findById(id).orElse(null);
    }


    public String deleteMovie(int id){
        repository.deleteById(id);
        return "movie was removed!" +id;
    }

    public MovieCatalogModel updateMovie(MovieCatalogModel movie){
        MovieCatalogModel existingMovie  = repository.findById(movie.getId()).orElse(null);
        existingMovie.setTitle(movie.getTitle());
        return repository.save(existingMovie);
    }


}

package com.company.Netex.Controller;

import com.company.Netex.Model.MovieCatalogModel;
import com.company.Netex.Repo.MovieCatalogRepository;
import com.company.Netex.Service.MovieCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class MovieCatalogController {

    @Autowired
    private MovieCatalogRepository repository;

    @Autowired
    private MovieCatalogService service;

    @PostMapping("/addMovie")
    public MovieCatalogModel addMovie(@RequestBody MovieCatalogModel movie){
        return service.saveMovie(movie);
    }


    @PostMapping("/addMovies")
    public List<MovieCatalogModel> addMovie(@RequestBody List<MovieCatalogModel> movie){
        return service.saveMovie(movie);
    }


    @GetMapping("/movies")
    public List<MovieCatalogModel> findAllMovies(){
        return service.getMovies();
    }

    @GetMapping("/movieById/{id}")
    public MovieCatalogModel findMoviesById(@PathVariable int id){
        return service.getMoviesById(id);
    }


    @PutMapping("/update")
    public MovieCatalogModel updateMovie(@RequestBody MovieCatalogModel movie){
        return service.updateMovie(movie);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteMovie(@PathVariable int id){
        return service.deleteMovie(id);
    }

    @GetMapping("/getOrderedTitle")
    public List<MovieCatalogModel> getTitle(){

        return service.getAlphabeticTitleOrder();
    }

    @GetMapping("/getYearOrder/{first}/{second}")
    public List<MovieCatalogModel> getYear(@PathVariable Integer first, @PathVariable Integer second){

        List<MovieCatalogModel> movie = new ArrayList<>();

        List<MovieCatalogModel> lista1 = service.getYear();

        for (int i = 0; i < lista1.size(); i++) {
            if(first <  Integer.parseInt(lista1.get(i).getYear()) && second >  Integer.parseInt(lista1.get(i).getYear())){
                 movie.add(lista1.get(i));
            }
        }
        return  movie;
    }



}

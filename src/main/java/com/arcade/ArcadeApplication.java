package com.arcade;

import com.arcade.model.request.CategoryRequest;
import com.arcade.service.CategoriesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ArcadeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArcadeApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(CategoriesService service) {
        return args -> {
            service.insert(new CategoryRequest("Lanches", 1));
            service.insert(new CategoryRequest("Bebidas", 2));
            service.insert(new CategoryRequest("Porções", 3));
            service.insert(new CategoryRequest("Fichas", 4));
        };
    }

}

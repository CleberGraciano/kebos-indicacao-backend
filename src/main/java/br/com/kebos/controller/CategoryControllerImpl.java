package br.com.kebos.controller;

import br.com.kebos.model.Category;
import br.com.kebos.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/categories")
@SecurityRequirement(name = "kebosapi")
public class CategoryControllerImpl {

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Category>> findAllCategories(){
        return ResponseEntity.ok(categoryService.findAllCategories());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Category> saveCategory(@Valid @RequestBody Category category){
        return ResponseEntity.ok(categoryService.saveCategory(category));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> findByIdCategory(@PathVariable("id") long id){
        return ResponseEntity.ok(categoryService.findById(id));
    }

}

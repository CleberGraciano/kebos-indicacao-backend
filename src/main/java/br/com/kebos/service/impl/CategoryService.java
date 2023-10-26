package br.com.kebos.service.impl;

import br.com.kebos.model.Category;
import br.com.kebos.model.Item;
import br.com.kebos.repository.CategoryRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category saveCategory(Category category){
        return categoryRepository.save(category);
    }

    public List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).get();
    }

    public Category update(Long itemId, Category newCategory)  throws NotFoundException {
        Category existingCategory = null;
        existingCategory = categoryRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Item not found"));
        existingCategory.setNome(newCategory.getNome());
        existingCategory.setDescricao(newCategory.getDescricao());
        return categoryRepository.save(existingCategory);
    }
}

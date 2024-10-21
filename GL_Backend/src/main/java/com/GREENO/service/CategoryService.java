package com.GREENO.service;

import com.GREENO.domain.Category;
import com.GREENO.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public Set<Category> getAll(){
        return new HashSet<>(categoryRepository.findAll());
    }

}

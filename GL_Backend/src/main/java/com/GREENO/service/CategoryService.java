package com.GREENO.service;

<<<<<<< HEAD:GL_Backend/src/main/java/com/agropharm/service/CategoryService.java
import com.agropharm.Entities.Category;
import com.agropharm.repository.CategoryRepository;
=======
import com.GREENO.domain.Category;
import com.GREENO.repository.CategoryRepository;
>>>>>>> de9fc5b09f3308f3c484e231cf77dd7f463a83c3:GL_Backend/src/main/java/com/GREENO/service/CategoryService.java
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

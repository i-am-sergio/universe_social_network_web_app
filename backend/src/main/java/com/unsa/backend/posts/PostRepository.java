package com.unsa.backend.posts;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostModel, Long> {
    
}

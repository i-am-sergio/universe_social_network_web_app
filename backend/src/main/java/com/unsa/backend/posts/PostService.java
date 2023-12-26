package com.unsa.backend.posts;

import java.util.List;

public interface PostService {
    public List<PostModel> getPosts();

    public PostModel getPostById(Long id);

    public PostModel createPost(PostModel newPost);

    public void updatePost(PostModel existingPost, PostModel updatedPost);

    public boolean deletePost(Long postId);

    public void addLike(PostModel post, Long userId);

    public void removeLike(PostModel post, Long userId);
}

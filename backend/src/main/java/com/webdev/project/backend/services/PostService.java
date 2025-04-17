package com.webdev.project.backend.services;

import com.webdev.project.backend.entities.Hashtag;
import com.webdev.project.backend.entities.Post;
import com.webdev.project.backend.entities.User;
import com.webdev.project.backend.repositories.HashtagRepository;
import com.webdev.project.backend.repositories.PostRepository;
import com.webdev.project.backend.repositories.UserRepository;
import com.webdev.project.backend.requests.CreatePostRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final HashtagService hashtagService;
    private final HashtagRepository hashtagRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, HashtagService hashtagService, HashtagRepository hashtagRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.hashtagService = hashtagService;
        this.hashtagRepository = hashtagRepository;
    }

    public Post createPost(CreatePostRequest request, MultipartFile imageFile, User currentUser) {
        Post post = new Post();
        post.setUser(currentUser);
        post.setTitle(request.getTitle());
        post.setBody(request.getBody());
        post.setPrivate(Boolean.TRUE.equals(request.getIsPrivate()));
        post.setImage(null); // Image handling can be implemented later

        // Process hashtags
        List<String> parsedHashtags = request.getHashtags();
        Set<Hashtag> hashtagEntities = hashtagService.extractOrCreateHashtags(parsedHashtags);
        post.setHashtags(hashtagEntities);

        // Ensure reverse link is established
        for (Hashtag hashtag : hashtagEntities) {
            hashtag.getPosts().add(post);
            hashtagRepository.save(hashtag); // Persist updated hashtag relationship
        }

        return postRepository.save(post);
    }

    public Optional<Post> getPostById(Long postId, User currentUser) {
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isEmpty()) return Optional.empty();

        Post post = optionalPost.get();

        if (post.getPrivate() && !post.getUser().getId().equals(currentUser.getId())) {
            // Future: check follower status
            return Optional.empty();
        }

        return Optional.of(post);
    }

    public Optional<Post> updatePost(Long postId, User currentUser, Post updatedData) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) return Optional.empty();

        Post existingPost = optionalPost.get();
        if (!existingPost.getUser().getId().equals(currentUser.getId())) return Optional.empty();

        existingPost.setTitle(updatedData.getTitle());
        existingPost.setBody(updatedData.getBody());
        existingPost.setImage(updatedData.getImage());
        existingPost.setPrivate(updatedData.getPrivate());
        // Hashtag update can be implemented later

        return Optional.of(postRepository.save(existingPost));
    }

    public List<Post> getPostsByUser(User user) {
        return postRepository.findByUserAndIsPrivateFalse(user);
    }

    public List<Post> getMyPosts(User currentUser) {
        return postRepository.findByUser(currentUser);
    }

    public Boolean deletePost(Long postId, User currentUser) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isEmpty()) return false;

        Post post = optionalPost.get();
        if (!post.getUser().getId().equals(currentUser.getId())) return false;

        postRepository.delete(post);
        return true;
    }

    public List<Post> getFeedPosts(User user) {
        return postRepository.findByUserIsNotAndIsPrivateFalse(user);
    }

    public List<Post> searchPosts(String query, String hashtag) {
        if (hashtag != null && !hashtag.isBlank()) {
            return postRepository.searchPublicPosts(
                    (query != null && !query.isBlank()) ? query.toLowerCase() : hashtag.toLowerCase()
            );
        }
        return postRepository.searchPublicPosts(query == null ? "" : query.toLowerCase());
    }
}

package xyz.rtxux.TBlog.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import xyz.rtxux.TBlog.Exception.ResourceNotFoundException;
import xyz.rtxux.TBlog.Model.BlogPost;
import xyz.rtxux.TBlog.Payload.ApiResponse;
import xyz.rtxux.TBlog.Payload.BlogPostRequest;
import xyz.rtxux.TBlog.Payload.BlogPostResponse;
import xyz.rtxux.TBlog.Repository.BlogPostRepository;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class BlogPostController {

    @Autowired
    BlogPostRepository blogPostRepository;

    @GetMapping("")
    public List<BlogPostResponse> getBlogPosts(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "15") int size) {
        Pageable pageable = new PageRequest(page, size);
        return blogPostRepository.findAll(pageable).getContent().stream().map((blogPost -> new BlogPostResponse(blogPost.getId(), blogPost.getName(), blogPost.getDescription(), ""))).collect(Collectors.toList());
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> postBlogPost(@RequestBody BlogPostRequest blogPostRequest) {
        var blogPost = new BlogPost();
        blogPost.setName(blogPostRequest.getName());
        blogPost.setDescription(blogPostRequest.getDescription());
        blogPost.setContent(blogPostRequest.getContent());
        blogPost = blogPostRepository.save(blogPost);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/posts/{id}")
                .buildAndExpand(blogPost.getId()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "successfully posted"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBlogPost(@PathVariable(value = "id") Long blogPostId) {
        var blogPost = blogPostRepository.findById(blogPostId).orElseThrow(() -> new ResourceNotFoundException("Blog Post", "Id", blogPostId));
        return ResponseEntity.ok(new BlogPostResponse(blogPost.getId(), blogPost.getName(), blogPost.getDescription(), blogPost.getContent()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateBlogPost(@RequestBody BlogPostRequest blogPostRequest, @PathVariable("id") Long id) {
        var blogPost = blogPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog Post", "Id", id));
        blogPost.setName(blogPostRequest.getName());
        blogPost.setDescription(blogPostRequest.getDescription());
        blogPost.setContent(blogPostRequest.getContent());
        blogPost = blogPostRepository.save(blogPost);
        return ResponseEntity.ok(new ApiResponse(true, "successfully updated Post"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteBlogPost(@PathVariable("id") Long id) {
        var blogPost = blogPostRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Blog Post", "Id", id));
        blogPostRepository.delete(blogPost);
        return ResponseEntity.ok(new ApiResponse(true, "successfully deleted Post"));
    }

}

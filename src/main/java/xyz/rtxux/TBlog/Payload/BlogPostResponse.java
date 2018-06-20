package xyz.rtxux.TBlog.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogPostResponse {
    private Long id;
    private String name;
    private String description;
    private String content;
}

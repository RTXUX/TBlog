package xyz.rtxux.TBlog.Model;


import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.rtxux.TBlog.Model.Audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class BlogPost extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 60)
    private String name;

    @NotBlank
    @Size(max = 140)
    private String description;

    @NotBlank
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;

    @OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Comment> comments;
}

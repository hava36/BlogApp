package com.skillbox.blogapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * A PostComment.
 */
@Entity
@Table(name = "post_comment")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class PostComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(name = "time", nullable = false)
    private Instant time;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne
    @JsonIgnoreProperties(value = {"parent", "user", "post"}, allowSetters = true)
    private PostComment parent;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = {"moderator", "user", "tags"}, allowSetters = true)
    private Post post;

}

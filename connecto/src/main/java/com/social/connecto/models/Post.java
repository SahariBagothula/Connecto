package com.social.connecto.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String caption;
    private String image;
    private String video;
    private LocalDateTime createdAt;

    @ManyToOne
    @JsonBackReference
    private User user;

    @ManyToMany
    @JsonIgnoreProperties({ "likedPosts", "savedPost" })
    private List<User> liked = new ArrayList<>();

}

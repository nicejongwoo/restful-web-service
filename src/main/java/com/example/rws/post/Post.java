package com.example.rws.post;

import com.example.rws.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue
    private Integer id;

    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

}

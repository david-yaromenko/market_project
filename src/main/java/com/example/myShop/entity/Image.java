package com.example.myShop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Image")
@Setter
@Getter
@NoArgsConstructor
public class Image {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "contentType")
    private String contentType;
    @Column(name = "sizes")
    private Long sizes;
    @Column(name = "original_file_name")
    private String originalFileName;

    @Lob
    @Column(name = "bytes", columnDefinition = "BYTEA")
    private byte[] bytes;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "product_id")
    private Product product;
}

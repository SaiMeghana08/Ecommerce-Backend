package com.JWT.demo.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Length;
import org.jspecify.annotations.Nullable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long image_id;
    private String name;
    private String type;
    @Lob
    @Column(length=10000)
    private byte[] imageBytes;

    public ProductImages(@Nullable String originalFilename, @Nullable String contentType, byte[] bytes) {
        this.name=originalFilename;
        this.type=contentType;
        this.imageBytes=bytes;
    }
}

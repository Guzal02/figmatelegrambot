package com.example.figmatelegrambot.model.figma.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.data.util.ProxyUtils;

import java.time.LocalDateTime;
import java.util.Objects;
//, uniqueConstraints = {@UniqueConstraint(columnNames = "figma_file_key")}
@Entity
@Table(name = "files")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FigmaFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 128)
    @Column(name = "figma_file_key")
    private String key;

    @NotBlank
    @Size(max = 128)
    @Column(name = "name")
    private String name;

    @NotBlank
    @Size(max = 1000)
    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @NotNull
    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @ToString.Exclude
    private FigmaProject figmaProject;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        FigmaFile that = (FigmaFile) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}

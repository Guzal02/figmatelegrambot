package com.example.figmatelegrambot.model.figma.entity;

import com.example.figmatelegrambot.model.bot.telegram.entity.Chat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.util.ProxyUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects", uniqueConstraints = {@UniqueConstraint(columnNames = "figma_project_id")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FigmaProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 128)
    @Column(name = "figma_project_id")
    private String figmaProjectId;

    @NotBlank
    @Size(max = 128)
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    @ToString.Exclude
    private FigmaTeam figmaTeam;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "figmaProject")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<FigmaFile> figmaFileList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "figmaProject")
    private List<Chat> chat = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        FigmaProject that = (FigmaProject) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}

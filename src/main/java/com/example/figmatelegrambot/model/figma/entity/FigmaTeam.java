package com.example.figmatelegrambot.model.figma.entity;

import com.example.figmatelegrambot.model.bot.telegram.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.util.ProxyUtils;

import java.util.List;

@Entity
@Table(name = "teams", uniqueConstraints = {@UniqueConstraint(columnNames = "figma_team_id")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FigmaTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 128)
    @Column(name = "figma_team_id")
    private String figmaTeamId;

    @NotBlank
    @Size(max = 64)
    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_team",
            joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<User> users;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "figmaTeam", orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private List<FigmaProject> figmaProjects;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        FigmaTeam that = (FigmaTeam) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}

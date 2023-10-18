package com.example.figmatelegrambot.model.bot.telegram.entity;

import com.example.figmatelegrambot.model.figma.entity.FigmaTeam;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "user_id")})
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotBlank
    @Size(max = 128)
    @Column(name = "user_name", nullable = false)
    private String userName;

    @NotBlank
    @Size(max = 64)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Size(max = 64)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_team",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"))
    @ToString.Exclude
    private List<FigmaTeam> teams;

    public User(Long userId, String userName, String firstName, String lastName) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User that = (User) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, firstName, lastName);
    }
}

package com.example.figmatelegrambot.model.bot.telegram.entity;

import com.example.figmatelegrambot.model.figma.entity.FigmaProject;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "chats", uniqueConstraints = {@UniqueConstraint(columnNames = "chat_id")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default false")
    private boolean enabled = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "chat_type")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ChatType chatTypes;

    @NotNull
    @Column(name = "chat_id")
    private Long chatId;

    @NotNull
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id ", nullable = false)
    @ToString.Exclude
    private FigmaProject figmaProject;
}

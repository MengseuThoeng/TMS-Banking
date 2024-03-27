package com.seu.tms_mobile_banking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDateTime transacted_at;

    @OneToOne
    private Transaction transaction;

    @ManyToOne
    private Account sender;

    @ManyToOne
    private Account receiver;
}

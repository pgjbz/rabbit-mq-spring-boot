package com.pgjbz.subscription.consumer.model;

import com.pgjbz.subscription.consumer.enums.StatusNotification;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EventHistory {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusNotification type;
    @Getter
    @ManyToOne
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;
    @Column(name = "created_at")
    @ToString.Exclude
    @CreationTimestamp
    private Date created;

    @Deprecated
    public EventHistory() {
    }

    public EventHistory(Subscription subscription, StatusNotification type) {
        this.subscription = subscription;
        this.type = type;
    }
}

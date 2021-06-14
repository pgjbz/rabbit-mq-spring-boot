package com.pgjbz.subscription.consumer.model;

import com.pgjbz.subscription.consumer.enums.StatusNotification;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@Table(name = "subscriptions")
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Subscription {

    @Getter
    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    @Column(name = "id")
    private String id;
    @Getter
    @ToString.Include
    @Column(name = "status_id")
    private Integer status;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date created;
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updated;

    @Deprecated
    public Subscription() {
    }

    public Subscription(@NonNull String id, @NonNull StatusNotification status) {
        this.id = Objects.requireNonNull(id);
        setStatus(status);
    }



    public void setStatus(@NonNull StatusNotification status) {
        this.status = Objects.requireNonNull(status).getStatus();
    }
}

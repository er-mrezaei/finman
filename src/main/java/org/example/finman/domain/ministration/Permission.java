package org.example.finman.domain.ministration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.finman.domain.user.SimpleUser;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private SimpleUser user;

    @ManyToOne
    @JoinColumn(name = "ministration_id")
    private Ministration ministration;

    private boolean granted;

    private int usageCount;
}

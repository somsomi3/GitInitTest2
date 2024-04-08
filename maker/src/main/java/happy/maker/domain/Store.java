package happy.maker.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Store {
    @Id
    @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "store_name")
    private String name;

    @OneToMany(mappedBy = "store")
    private List<Reservation> reservationList = new ArrayList<>();

    private String location;
    private String description;

    private double x;
    private double y;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

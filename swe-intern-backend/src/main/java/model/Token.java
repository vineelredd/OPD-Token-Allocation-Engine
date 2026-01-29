package com.MEDOC.HEALTH.sweinternbackend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token implements Comparable<Token> {
    @Id
    private String id;
    private String doctorId;
    private String slotId; // [cite: 5]
    private String patientName;

    @Enumerated(EnumType.STRING)
    private Source source; // [cite: 6]

    @Enumerated(EnumType.STRING)
    private Status status; // [cite: 18]

    public enum Source {
        EMERGENCY(1),      // [cite: 11]
        PAID_PRIORITY(2),  // [cite: 9]
        FOLLOW_UP(3),      // [cite: 10]
        ONLINE_BOOKING(4), // [cite: 7]
        WALK_IN(5);        // [cite: 8]

        public final int weight;
        Source(int weight) { this.weight = weight; }
    }

    public enum Status { BOOKED, COMPLETED, CANCELLED }

    @Override
    public int compareTo(Token other) {
        // Prioritizes between different token sources [cite: 17]
        return Integer.compare(this.source.weight, other.source.weight);
    }
}
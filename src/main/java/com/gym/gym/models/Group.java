package com.gym.gym.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "groupss")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="group_id")
    private long id;
    
    private String day;
    
    @OneToOne
    @JoinColumn(name="coach_id")
    private Coach coach;
    
    private int startTime;
    private int endTime;

}

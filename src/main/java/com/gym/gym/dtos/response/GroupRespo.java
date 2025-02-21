package com.gym.gym.dtos.response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRespo {
    private long id;
    private String day;
    private long coachId;
    private int startTime;
    private int endTime;
    
}
package com.gym.gym.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCoachDto {
   
    private long userId;  // Change field name to 'userId'
}

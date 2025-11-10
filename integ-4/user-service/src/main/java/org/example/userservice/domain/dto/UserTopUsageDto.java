package org.example.userservice.domain.dto;

import lombok.*;
import org.example.userservice.domain.enums.State;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserTopUsageDto {
    private Long id;
    private String name;
    private String last_name;
    private String mail;
    private String phone_number;
    private State state;
    private int cantJourneys;
    public UserTopUsageDto(UserDto user, int cantJourneys) {
        this.id = user.getId();
        this.name = user.getName();
        this.last_name = user.getLast_name();
        this.mail = user.getMail();
        this.phone_number = user.getPhone_number();
        this.state = user.getState();
        this.cantJourneys = cantJourneys;
    }
}

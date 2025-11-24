package org.example.movementservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.movementservice.domain.entities.Movement;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class MovementDTO {
    private Long movementId;
    private Long accountId;
    private Long userId;
    private Long amount;
    private LocalDate date;
    private String paymentId;
    private String paymentStatus;
    private String paymentStatusDetail;
    private String authorizationCode;

    public MovementDTO(Movement movement){
        if (movement != null) {
            this.movementId = movement.getMovementId();
            this.accountId = movement.getAccountId();
            this.userId = movement.getUserId();
            this.amount = movement.getAmount();
            this.date = movement.getDate();
            this.paymentId = movement.getPaymentId();
            this.paymentStatus = movement.getPaymentStatus();
            this.paymentStatusDetail = movement.getPaymentStatusDetail();
            this.authorizationCode = movement.getAuthorizationCode();
        }
    }

    public Movement DTOToEntity() {
        Movement movement = new Movement(this.getAccountId(), this.getUserId(), this.getAmount());
        return movement;
    }
}
package org.example.userservice.domain.dto;

import lombok.Data;

@Data
public class PaymentMadeDto {
    private Long idAccount;
    private Long idJourney;
    private Long idUser;
    private float priceJourney;
    private Float amountAccount;
    public PaymentMadeDto(Long idAccount, Long userId, Long idJourney, float totalPrice, Float amount) {
        this.idAccount=idAccount;
        this.idJourney=idJourney;
        this.idUser=userId;
        this.priceJourney=totalPrice;
        this.amountAccount=amount;
    }
}

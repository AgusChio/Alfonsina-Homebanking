package com.mindhub.Homebranking.dto;

import java.time.LocalDateTime;

public class PdfGeneratorDTO {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String accountNumber;

    public PdfGeneratorDTO(LocalDateTime startDate, LocalDateTime endDate, String accountNumber) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.accountNumber = accountNumber;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

package models.services;

import models.entities.Contract;
import models.entities.Installment;

import java.time.LocalDate;

public class ContractService {

    private OnlinePaymentService onlinePaymentService;

    public ContractService(OnlinePaymentService onlinePaymentService) {
        this.onlinePaymentService = onlinePaymentService;
    }

    public void processContract (Contract contract, Integer months){
        Double basicQuota = contract.getTotalValue() / months;

        for (int i = 1; i <= months; i++) {
            Double updatedQuota = basicQuota +
                    onlinePaymentService.interest(basicQuota, i);
            Double finalQuota = updatedQuota +
                    onlinePaymentService.paymentFee(updatedQuota);

            LocalDate paymentDate = contract.getDate().plusMonths(i);
            
            contract.addInstallment(new Installment(paymentDate, finalQuota));
        }
    }
}

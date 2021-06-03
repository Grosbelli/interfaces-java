package application;

import models.entities.Contract;
import models.services.ContractService;
import models.services.OnlinePaymentPaypal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter contract data ");
        System.out.print("Number: ");
        int number = sc.nextInt();
        sc.nextLine();
        System.out.print("Date (dd/MM/yyyy): ");
        LocalDate date = LocalDate.parse(sc.next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.print("Contract value: ");
        double contractValue = sc.nextDouble();
        sc.nextLine();
        Contract contract = new Contract(number, date, contractValue);

        System.out.print("Enter number of installments: ");
        int months = sc.nextInt();

        ContractService cs = new ContractService(new OnlinePaymentPaypal());
        cs.processContract(contract, months);

        System.out.println("Installments: ");

        contract.getInstallments().forEach(installments -> {
            System.out.println(installments.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " - " + String.format("%.2f", installments.getAmount()));
        });

    }
}

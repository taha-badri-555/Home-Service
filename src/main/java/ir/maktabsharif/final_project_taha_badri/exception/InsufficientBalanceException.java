package ir.maktabsharif.final_project_taha_badri.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super("Insufficient balance to complete the transaction.\n" +
                "Please visit the following address to make a payment:\n" +
                "http://localhost:63342/Final_Project_Taha_Badri/Final_Project_Taha_Badri%20(1)/static/Payment.html");
    }
}


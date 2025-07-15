package ir.maktabsharif.final_project_taha_badri.domain.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record PaymentRequest(
        @Digits(integer = 16, fraction = 0, message = "must be Integer."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @Size(min = 16, max = 16
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        String cardNumber,

        @Min(value = 0,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        Double amount,

        @Digits(integer = 4, fraction = 0, message = "must be Integer."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @Size(min = 3, max = 4
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        String cvv2,

        @Future(groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        String expirationDate,

        @Digits(integer = 7, fraction = 0, message = "must be Integer."
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @Size(min = 6, max = 7
                ,groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        String otp,

        String recaptcha,

        int clientTimeLeft
) {
}

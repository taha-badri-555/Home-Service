package ir.maktabsharif.final_project_taha_badri.domain.dto.request.user;


import ir.maktabsharif.final_project_taha_badri.domain.dto.Identifiable;
import ir.maktabsharif.final_project_taha_badri.domain.dto.ValidationGroup;
import ir.maktabsharif.final_project_taha_badri.domain.enums.ExpertStatus;
import ir.maktabsharif.final_project_taha_badri.util.Utility;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public record ExpertRequest(

        @NotBlank(groups = ValidationGroup.Save.class, message = "firstName is null.")
        @Null(groups = ValidationGroup.Update.class, message = "firstName must be null..")
        String firstName,

        @NotBlank(message = "lastName is blank."
                , groups = {ValidationGroup.Save.class})
        @Null(groups = ValidationGroup.Update.class, message = "lastName must be null..")
        String lastName,

        @NotBlank(message = "password is blank.", groups = {ValidationGroup.Save.class})
        @Size(min = 8, message = "Password must be at least 8 characters long.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "Password must contain both letters and numbers."
                , groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        String password,

        @Email(groups = {ValidationGroup.Update.class, ValidationGroup.Save.class})
        @NotBlank(message = "email is blank.", groups = {ValidationGroup.Save.class})
        String email,

        @Size(max = 300_000)
        byte[] image,

        @Null(groups = ValidationGroup.Save.class, message = "score must be null..")
        Double score,

        ExpertStatus status,

        Double avgScore
) implements Identifiable<Long> {

    public ExpertRequest {

        if (email != null) {
            email = email.toLowerCase();
        }
        if (firstName != null) {
            firstName = Utility.formatName(firstName);
        }
        if (lastName != null) {
            lastName = Utility.formatName(lastName);
        }
    }

    public ExpertRequest(String firstName,
                         String lastName,
                         String password,
                         String email,
                         MultipartFile image,
                         Double score,
                         ExpertStatus status,
                         Double avgScore) throws IOException {
        this(firstName, lastName, password, email, image.getBytes(), score, status, avgScore);
    }


    public ExpertRequest(ExpertStatus status) {
        this(null, null, null, null,(byte[]) null, 0.0, status, null);
    }

    public ExpertRequest(Double avgScore) {
        this( null, null,null,  null, (byte[]) null, 0.0, null, avgScore);
    }

    public ExpertRequest(MultipartFile imagePath) throws IOException {
        this(null, null, null, null, imagePath.getBytes(), null, null, null);
    }

    @Override
    public Long getId() {
        return null;
    }
}


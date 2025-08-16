package ir.maktabsharif.final_project_taha_badri.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public record ChangeImagePatch (
        @NotNull
        @Size(max = 300_000)
        byte[] image
) {
        public ChangeImagePatch(MultipartFile image) throws IOException {
               this(image.getBytes());
        }
}

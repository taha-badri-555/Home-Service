package ir.maktabsharif.final_project_taha_badri.exception;

public class UserWithSameEmailExistsException extends RuntimeException {
    public UserWithSameEmailExistsException() {
        super("User with same email already exists");
    }
}

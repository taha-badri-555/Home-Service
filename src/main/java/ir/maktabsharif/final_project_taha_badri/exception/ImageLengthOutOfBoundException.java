package ir.maktabsharif.final_project_taha_badri.exception;

public class ImageLengthOutOfBoundException extends RuntimeException {
    public ImageLengthOutOfBoundException() {
        super("Image is  bigger than 300 kb.");
    }
}

package edu.kh.project.board.model.exception;

public class ImageUpdateException extends RuntimeException {

	public ImageUpdateException() {
		super("Error Update Image");
	}
	
	public ImageUpdateException(String message) {
		super(message);
	}
}

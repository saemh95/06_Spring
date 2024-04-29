package edu.kh.project.board.model.exception;

public class ImageDeleteException extends RuntimeException {

	public ImageDeleteException() {
		super("Error Deleting Image");
	}
	
	public ImageDeleteException(String message ) {
		super(message);
	}
	
}

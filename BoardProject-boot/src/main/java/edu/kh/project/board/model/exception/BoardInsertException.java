package edu.kh.project.board.model.exception;

public class BoardInsertException extends RuntimeException {

	public BoardInsertException() {
		super("Board Upload Error");
	}
	
	public BoardInsertException(String message) {
		super(message);
	}
	
}

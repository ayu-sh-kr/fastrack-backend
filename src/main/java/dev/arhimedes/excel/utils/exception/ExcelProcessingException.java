package dev.arhimedes.excel.utils.exception;

public class ExcelProcessingException extends RuntimeException{

    public ExcelProcessingException() {
        super();
    }

    public ExcelProcessingException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return super.getLocalizedMessage();
    }

}

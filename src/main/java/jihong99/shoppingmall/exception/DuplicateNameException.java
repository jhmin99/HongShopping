package jihong99.shoppingmall.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicateNameException extends RuntimeException{

    public DuplicateNameException(String message){
        super(message);
    }
}

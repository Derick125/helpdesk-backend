package com.turmab.helpdesk.resources.exceptions;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// A LINHA DE IMPORT CORRETA É ESTA:
import com.turmab.helpdesk.resources.exceptions.DataIntegrityViolationException;

/**
 * Manipulador de exceções global para a aplicação.
 * A anotação @ControllerAdvice permite que esta classe intercepte exceções
 * lançadas por qualquer @RestController e as trate de forma centralizada.
 */
@ControllerAdvice
public class ResourceExceptionHandler {

    /**
     * Manipula a exceção {@link ObjectNotFoundException}.
     * Retorna uma resposta HTTP com status 404 (Not Found) e um corpo
     * padronizado com os detalhes do erro.
     *
     * @param ex A exceção lançada.
     * @param request A requisição HTTP que causou o erro.
     * @return Um ResponseEntity com o status e o corpo do erro.
     */
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFoundException(ObjectNotFoundException ex,
            HttpServletRequest request) {
        
        StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
                "Object Not Found", ex.getMessage(), request.getRequestURI());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    /**
     * Manipula a exceção {@link DataIntegrityViolationException}.
     * Retorna uma resposta HTTP com status 400 (Bad Request) e um corpo
     * padronizado com os detalhes do erro.
     *
     * @param ex A exceção lançada.
     * @param request A requisição HTTP que causou o erro.
     * @return Um ResponseEntity com o status e o corpo do erro.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrityViolationException(DataIntegrityViolationException ex,
            HttpServletRequest request) {

        StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
                "Violação de Dados", ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
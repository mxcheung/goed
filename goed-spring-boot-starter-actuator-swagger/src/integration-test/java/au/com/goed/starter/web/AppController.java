package au.com.goed.starter.web;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for test application used in integration testing.
 * 
 * @author Goed Bezig
 */
@RestController
@RequestMapping("/integration")
@Validated
public class AppController {
    
    @ResponseStatus(OK)
    @GetMapping(params = { "message" })
    public AppModel runtimeException(@RequestParam(required = true) String message) {
        return new AppModel(message);
    }
   
}
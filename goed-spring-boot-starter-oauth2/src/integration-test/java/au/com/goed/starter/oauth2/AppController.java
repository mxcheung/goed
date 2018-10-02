package au.com.goed.starter.oauth2;

import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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


    @ResponseStatus(CREATED)
    @PostMapping
    public String createPost(@RequestBody String str) {
        return "Hello";
    }
}


package com.example.lab6_s2_api.Controller;

import com.example.lab6_s2_api.Domain.Greeting;
import com.example.lab6_s2_api.ErrorInfo;
import com.example.lab6_s2_api.LanguageNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private final AtomicLong counter = new AtomicLong();

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody

    public ErrorInfo handleBadRequest(HttpServletRequest request, Exception ex) {
        return new ErrorInfo("Unsupported language");
    }

    @GetMapping("greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name, @RequestParam(value = "language", defaultValue = "english") String language) {
        String content;
        switch (language) {
            case "italian":
                content = String.format("Ciao, %s!", name.equals("World") ? "mondo" : name);
                break;
            case "french":
                content = String.format("Bonjour, %s!", name.equals("World") ? "monde" : name);
                break;
            case "spanish":
                content = String.format("Hola, %s!", name.equals("World") ? "mundo" : name );
                break;
            case "german":
                content = String.format("Hallo, %s!", name.equals("World") ? "Welt" : name);
                break;
            case "japanese":
                content = String.format("こんにちは, %s!", name.equals("World") ? "世界" : name);
                break;
            case "russian":
                content = String.format("Привет, %s!", name.equals("World") ? "мир" : name);
                break;
            case "portuguese":
                content = String.format("Olá, %s!", name.equals("World") ? "mundo" : name);
                break;
            case "dutch":
                content = String.format("Hallo, %s!", name.equals("World") ? "wereld" : name);
                break;
            default:
                throw new LanguageNotFoundException();
        }
        return new Greeting(counter.incrementAndGet(), content, language);
    }
}

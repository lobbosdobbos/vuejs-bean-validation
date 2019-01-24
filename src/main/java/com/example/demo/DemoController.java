package com.example.demo;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/validate")
public class DemoController {

    private final Validator validator;

    public DemoController(Validator validator) {
        this.validator = validator;
    }

    @PostMapping
    public Map<String, String> validate(@RequestBody Claim claim) {
        return validator.validate(claim).stream()
                .collect(Collectors.toMap(
                        o -> o.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                ));
    }

}

@Data
class Claim {
    @NotBlank
    private String message;

    @Valid
    private Address address;
}

@Data
class Address {
    @NotBlank
    @Size(max = 40)
    private String street;
    @NotBlank
    @Size(max = 30)
    private String city;
}

package com.code.challenge.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
public class MovieValidationTests {
    private static Validator validator;
    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var movie = Movie.of("1234567890", "Rambo",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
        assertThat(violations).isEmpty();
    }


    @Test
    void whenEidrNotDefinedThenValidationFails() {
        var movie = Movie.of("", "Rambo",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
        assertThat(violations).hasSize(2);
        List<String> constraintViolationMessages = violations.stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());
        assertThat(constraintViolationMessages)
                .contains("The movie EIDR must be defined.")
                .contains("The EIDR format must be valid.");
    }

    @Test
    void whenEidrDefinedButIncorrectThenValidationFails() {
        var movie = Movie.of("a234567890", "Rambo",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The EIDR format must be valid.");
    }

    @Test
    void whenTitleIsNotDefinedThenValidationFails() {
        var movie = Movie.of("1234567890", "",
                "Ted Kotcheff", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The Movie title must be defined.");
    }

    @Test
    void whenDirectorIsNotDefinedThenValidationFails() {
        var movie = Movie.of("1234567890", "Rambo",
                "", 1982,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The Movie Director must be defined.");
    }

    @Test
    void whenReleaseYearIsNotDefinedThenValidationFails() {
        var movie = Movie.of("1234567890", "Rambo",
                "Ted Kotcheff", null,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The Movie release year must be defined.");
    }

    @Test
    void whenReleaseYearDefinedButOutOfMinRangeThenValidationFails() {
        var movie = Movie.of("1234567890", "Rambo",
                "Ted Kotcheff", 999,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("must be greater than or equal to 1000");
    }

    @Test
    void whenReleaseYearDefinedButOutOfMaxRangeThenValidationFails() {
        var movie = Movie.of("1234567890", "Rambo",
                "Ted Kotcheff", 10000,"studiocanal",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("must be less than or equal to 9999");
    }

    @Test
    void whenPublisherIsNotDefinedThenValidationFails() {
        var movie = Movie.of("1234567890", "Rambo",
                "Director", 1982,"",
                "In the film, Rambo is a troubled ...",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The Movie Publisher must be defined.");
    }

    @Test
    void whenSinopsisIsNotDefinedThenValidationFails() {
        var movie = Movie.of("1234567890", "Rambo",
                "Director", 1982,"publisher",
                "",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/bGIDYYOX7Cj1o7W8JiwHd3TzJVw.jpg");
        Set<ConstraintViolation<Movie>> violations = validator.validate(movie);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The Movie Sinopsis must be defined.");
    }


}

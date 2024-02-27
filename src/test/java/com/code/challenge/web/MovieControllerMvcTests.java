package com.code.challenge.web;

import com.code.challenge.controllers.MovieController;
import com.code.challenge.domain.MovieNotFoundException;
import com.code.challenge.services.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(MovieController.class)
class MovieControllerMvcTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MovieService movieService;
    @Test
    void whenGetMovieNotExistingThenShouldReturn404() throws Exception {
        String eidr = "73737313940";
        given(movieService.viewMovieDetails(eidr))
                .willThrow(MovieNotFoundException.class);
        mockMvc
                .perform(get("/movies/" + eidr))
                .andExpect(status().isNotFound());
    }
}


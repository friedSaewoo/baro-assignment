package com.example.baroassignment.domain.admin.service;

import com.example.baroassignment.domain.User;
import com.example.baroassignment.domain.auth.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
class AdminServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserRepository userRepository;


    @Test
    @WithMockUser(roles = "ADMIN")
    void 관리자가_권한_부여_요청_성공() throws Exception {
        // Given
        Long userId = 2L;
        User user = new User(userId, "testuser", "tester", "password");
        given(userRepository.findById(userId)).willReturn(user);

        // When
        ResultActions result = mockMvc.perform(patch("/admin/users/{userId}/roles", userId)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.nickname").value("tester"))
                .andExpect(jsonPath("$.roles[0].role").value("ROLE_ADMIN"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void 일반_유저가_권한_부여_요청_실패() throws Exception {
        // Given
        Long userId = 2L;

        // When
        ResultActions result = mockMvc.perform(patch("/admin/users/{userId}/roles", userId)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void 관리자가_존재하지_않는_유저에게_권한_부여_요청() throws Exception {
        // Given
        Long nonExistingUserId = 999L;
        given(userRepository.findById(nonExistingUserId)).willReturn(null);

        // When
        ResultActions result = mockMvc.perform(patch("/admin/users/{userId}/roles", nonExistingUserId)
                .contentType(MediaType.APPLICATION_JSON));

        // Then
        result.andExpect(status().isNotFound());
        result.andExpect(jsonPath("$.error.code").value("USER_NOT_FOUND"));
    }
}
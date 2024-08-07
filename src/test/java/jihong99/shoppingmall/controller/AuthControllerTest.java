package jihong99.shoppingmall.controller;

import jakarta.servlet.http.HttpServletRequest;
import jihong99.shoppingmall.exception.GlobalExceptionHandler;
import jihong99.shoppingmall.exception.InvalidTokenException;
import jihong99.shoppingmall.exception.NotFoundException;
import jihong99.shoppingmall.service.IAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static jihong99.shoppingmall.constants.Constants.*;
import static jihong99.shoppingmall.utils.JsonUtils.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ActiveProfiles("test")
class AuthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IAuthService authService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private AuthController authController;

    /**
     * Initialize mocks and setup MockMvc before each test
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(authController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();
    }

    /**
     * Test for successfully refreshing access token with a valid refresh token
     * @throws Exception
     */
    @Test
    void refreshAccessToken_Return_OK() throws Exception {
        // given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("refreshToken", "validRefreshToken");
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("accessToken", "newAccessToken");

        when(authService.refreshAccessToken(anyString())).thenReturn(responseBody);

        // when & then
        mockMvc.perform(post("/api/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestBody)))
                .andExpect(status().isOk());

        verify(authService, times(1)).refreshAccessToken(anyString());
    }

    /**
     * Test for bad request response when refresh token is invalid
     * @throws Exception
     */
    @Test
    void refreshAccessToken_Return_BadRequest() throws Exception {
        // given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("refreshToken", "invalidRefreshToken");

        when(authService.refreshAccessToken(anyString())).thenThrow(new InvalidTokenException(MESSAGE_400_InvalidRefreshToken));

        // when & then
        mockMvc.perform(post("/api/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestBody)))
                        .andExpect(status().isBadRequest())
                        .andExpect(jsonPath("$.errorMessage").value(MESSAGE_400_InvalidRefreshToken));

        verify(authService, times(1)).refreshAccessToken(anyString());
    }

    /**
     * Test for not found response when user is not found
     * @throws Exception
     */
    @Test
    void refreshAccessToken_Return_NotFoundException() throws Exception {
        // given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("refreshToken", "validRefreshToken");

        when(authService.refreshAccessToken(anyString())).thenThrow(NotFoundException.class);

        // when & then
        mockMvc.perform(post("/api/refresh-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(requestBody)))
                .andExpect(status().isNotFound());

        verify(authService, times(1)).refreshAccessToken(anyString());
    }

    /**
     * Test for successfully retrieving CSRF token
     * @throws Exception
     */
    @Test
    void csrfToken_Return_OK() throws Exception {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        CsrfToken csrfToken = new DefaultCsrfToken("headerName", "_csrf", "tokenValue");
        request.setAttribute(CsrfToken.class.getName(), csrfToken);

        // when & then
        mockMvc.perform(get("/api/csrf-token").requestAttr(CsrfToken.class.getName(), csrfToken))
                .andExpect(status().isOk());
    }
}

package jihong99.shoppingmall.dto;

import jihong99.shoppingmall.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private String statusCode;
    private String statusMessage;
    private String accessToken;
    private String refreshToken;
    private Long userId;

    public static LoginResponseDto success(String accessToken, String refreshToken, Long userId){
        return  new LoginResponseDto(Constants.STATUS_200, Constants.MESSAGE_200_LoginSuccess, accessToken,refreshToken,userId);
    }

    public static LoginResponseDto error(String statusCode, String statusMessage){
        return new LoginResponseDto(statusCode, statusMessage, null, null, null);
    }
}

package jihong99.shoppingmall.service;
import jihong99.shoppingmall.dto.LoginRequestDto;
import jihong99.shoppingmall.dto.MyPageResponseDto;
import jihong99.shoppingmall.dto.SignUpDto;
import jihong99.shoppingmall.dto.UserSummaryDto;
import jihong99.shoppingmall.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {

    void signUpAccount(SignUpDto signUpDto);

    void checkDuplicateIdentification(String identification);


    Users loginByIdentificationAndPassword(LoginRequestDto loginRequestDto);

    String generateAccessToken(Users user);

    String generateRefreshToken(Users user);

    MyPageResponseDto getUserDetails(Long userId);

    void signUpAdminAccount(SignUpDto signUpDto);
    Page<UserSummaryDto> getUsers(Pageable pageable);


}

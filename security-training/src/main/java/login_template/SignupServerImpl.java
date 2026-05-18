package login_template;

import login_template.jwt.JwtTokenHelper;
import login_template.jwt.TokenDto;
import login_template.form.SignUpForm;
import login_template.model.User;
import login_template.repo.UserRepository;
import login_template.service.CaptchaService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

// @Service
public class SignupServerImpl {

    private CaptchaService captchaService;

    private PasswordEncoder passwordEncoder;
    private JwtTokenHelper jwtTokenHelper;

    private UserRepository userRepository;
    private UserDetailsService userDetailsService;

    public TokenDto signUp(SignUpForm form) {
        captchaService.processResponse(form.getCaptchaResponse());

        String passwordHash = passwordEncoder.encode(form.getPassword());
        User userCandidate = userRepository.save(User.builder()
                .login(form.getLogin())
                .hashPassword(passwordHash)
                .enabled(Boolean.TRUE)
                .role(User.UserRole.USER)
                .state(User.UserState.ACTIVE)
                .registeringTime(LocalDateTime.now())
                .lastUpdateTime(LocalDateTime.now())
                .build());

        UserDetails userDetails = userDetailsService.loadUserByUsername(form.getLogin());
        return TokenDto.builder()
                .token(jwtTokenHelper.generateToken(userDetails))
                .build();
    }
}

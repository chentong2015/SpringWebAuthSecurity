package main.backend.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jwt.JwtTokenProvider;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class SessionController {

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {
        String authToken = CookieHandler.getTokenFromAuthHeader(request);
        if (authToken == null) {
            SessionTokenState tokenState = new SessionTokenState();
            return ResponseEntity.accepted().body(tokenState);
        }

        String refreshedToken = JwtTokenProvider.refreshJwtToken(authToken);
        CookieHandler.addTokenToCookieStoreOfResponse(response, refreshedToken);

        SessionTokenState tokenState = new SessionTokenState(refreshedToken, 600);
        return ResponseEntity.ok().body(tokenState);
    }
}

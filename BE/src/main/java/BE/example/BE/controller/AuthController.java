package BE.example.BE.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import BE.example.BE.Util.SecurityUtil;
import BE.example.BE.Util.annotation.ApiMessage;
import BE.example.BE.domain.User;
import BE.example.BE.domain.dto.LoginDTO;
import BE.example.BE.domain.dto.ResLoginDTO;
import BE.example.BE.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

        @Value("${be.jwt.refresh-token-validity-in-seconds}")
        private long refreshTokenExpiration;

        private AuthenticationManagerBuilder authenticationManagerBuilder;
        private SecurityUtil securityUtil;
        private UserService userService;

        public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil,
                        UserService userService) {
                this.authenticationManagerBuilder = authenticationManagerBuilder;
                this.securityUtil = securityUtil;
                this.userService = userService;

        }

        @PostMapping("/auth/login")
        public ResponseEntity<ResLoginDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                loginDTO.getUsername(), loginDTO.getPassword());
                // xác thực người dùng => cần viết hàm loadUserByUserName
                Authentication authentication = authenticationManagerBuilder.getObject()
                                .authenticate(authenticationToken);
                // set thong tin người dùng đăng nhập vào con text để sử dụng cho sau này
                SecurityContextHolder.getContext().setAuthentication(authentication);
                User resUser = this.userService.HandleGetUserByUserName(loginDTO.getUsername());
                ResLoginDTO res = new ResLoginDTO();
                ResLoginDTO.UserLogin user = new ResLoginDTO.UserLogin(resUser.getId(), resUser.getName(),
                                resUser.getEmail());
                res.setUser(user);

                // create access_token
                String access_Token = this.securityUtil.createAccessToken(authentication, res.getUser());
                res.setAccessToken(access_Token);

                // create refresh_token
                String refresh_token = this.securityUtil.createRefreshToken(loginDTO.getUsername(), res);
                this.userService.updateUserToken(refresh_token, loginDTO.getUsername());
                ResponseCookie responseCookie = ResponseCookie
                                .from("refresh_token", refresh_token)
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .maxAge(refreshTokenExpiration)
                                .build();
                return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                                .body(res);
        }

        //
        @GetMapping("/auth/account")
        @ApiMessage("Fetch account")
        public ResponseEntity<ResLoginDTO.UserLogin> getAccount() {
                String email = SecurityUtil.getCurrentUserLogin().isPresent()
                                ? SecurityUtil.getCurrentUserLogin().get()
                                : "";
                User currentUserDB = this.userService.HandleGetUserByUserName(email);
                ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin();
                if (currentUserDB != null) {
                        userLogin.setId(currentUserDB.getId());
                        userLogin.setName(currentUserDB.getName());
                        userLogin.setEmail(currentUserDB.getEmail());
                }
                return ResponseEntity.ok().body(userLogin);

        }

}

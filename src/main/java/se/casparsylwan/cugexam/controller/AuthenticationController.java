package se.casparsylwan.cugexam.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import se.casparsylwan.cugexam.security.AutenticationRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import se.casparsylwan.cugexam.security.AutenticationResponse;
import se.casparsylwan.cugexam.security.CugExamUserDetailsService;
import se.casparsylwan.cugexam.security.JwtUtil;

@RestController
@RequestMapping("v1/auth")
@CrossOrigin(origins = "http://localhost:8081")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager autenticationManger;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CugExamUserDetailsService cugExamUserDetailsService;

    @Value("${tokensecret}")
    private String testToken;


    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody AutenticationRequest authRequest) throws Exception
    {
        try
        {
            autenticationManger.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUserEmail(), authRequest.getPassword())
            );
        }
        catch (BadCredentialsException e)
        {
            throw new Exception("Incorrect email or password", e);
        }

        final UserDetails userDetails =  cugExamUserDetailsService.loadUserByUsername(authRequest.getUserEmail());
        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AutenticationResponse(jwt));
    }

    @GetMapping("wakeup")
    public String wakeUp()
    {
        return testToken;
    }
}

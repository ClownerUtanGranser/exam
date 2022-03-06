package se.casparsylwan.cugexam.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;

import org.springframework.stereotype.Service;

import se.casparsylwan.cugexam.entity.CugExamUser;
import se.casparsylwan.cugexam.repository.CugExamUserRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CugExamUserDetailsService implements UserDetailsService {

    @Autowired
    private CugExamUserRepository userRepository;

    @Override
    public CugUserDetail loadUserByUsername(String userEmail) throws UsernameNotFoundException {

        Optional<CugExamUser> cugExamUser = userRepository.findByEmail(userEmail);

        cugExamUser.orElseThrow(() -> new UsernameNotFoundException("Email not found: " + userEmail));
        CugExamUser cugExamUserExist = null;

        if(cugExamUser.isPresent())
        {
            cugExamUserExist = cugExamUser.get();
        }

        return new CugUserDetail(cugExamUserExist);
    }
}

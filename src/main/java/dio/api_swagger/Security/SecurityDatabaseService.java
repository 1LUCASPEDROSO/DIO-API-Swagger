package dio.api_swagger.Security;

import dio.api_swagger.Entity.User;
import dio.api_swagger.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class SecurityDatabaseService  implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        User userEntity = userRepository.findByUsername(username);
        if (userEntity == null){
            throw new UsernameNotFoundException(username);
        }
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        userEntity.getRoles().forEach(role ->{
            authorities.add( new SimpleGrantedAuthority("ROLE_" + role));
        });
        UserDetails user = new org.springframework.security.core.userdetails.User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                authorities
        );
        return user;
    }


}

package tqs.backend.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Implemente o código para carregar os detalhes do usuário com base no nome de
		// usuário fornecido
		// por exemplo, consultando o banco de dados ou outro sistema de armazenamento

		// Se o usuário não for encontrado, você pode lançar uma exceção
		// UsernameNotFoundException
		throw new UsernameNotFoundException("User not found with username: " + username);
	}
}

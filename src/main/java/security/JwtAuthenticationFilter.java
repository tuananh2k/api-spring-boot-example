//package security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//@Component
//public class JwtAuthenticationFilter extends BasicAuthenticationFilter {
//
//    @Autowired
//    private JwtTokenProvider tokenProvider;
//
//    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
//        super(authenticationManager);
//        this.tokenProvider = tokenProvider;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException, ServletException {
//        String token = getTokenFromRequest(request);
//
//        if (token != null && tokenProvider.validateToken(token)) {
//            String username = tokenProvider.getUsernameFromJWT(token);
//            // You should look up the user from your user details service and create an Authentication object
//            // Set the Authentication object to the SecurityContext
//
//            // For example:
//            // UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            // UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            // SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    private String getTokenFromRequest(HttpServletRequest request) {
//        String bearerToken = request.getHeader("Authorization");
//        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//}
//

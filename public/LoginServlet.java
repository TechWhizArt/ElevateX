import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class LoginServlet extends HttpServlet {
    
    private FirebaseAuth firebaseAuth;

    @Override
    public void init() throws ServletException {
        super.init();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get ID Token from request
        String idToken = request.getParameter("idToken");

        try {
            // Verify the ID Token
            FirebaseToken decodedToken = firebaseAuth.verifyIdToken(idToken);
            String uid = decodedToken.getUid();
            
            // Now you have the UID of the authenticated user
            response.getWriter().write("{\"message\":\"User authenticated successfully\", \"uid\":\"" + uid + "\"}");
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception e) {
            // If the ID token is invalid or expired
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid ID token: " + e.getMessage());
        }
    }
}

package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LogoutAction")
public class LogoutAction extends Action {

	private static final long serialVersionUID = 1L;
	private HttpSession httpSession;

	public LogoutAction() {
		super();
	}

	@Override
	public void createObejcts(HttpServletRequest request) {
		httpSession = request.getSession();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		createObejcts(request);
		httpSession.invalidate();
		LoginJSP(response);
	}
	
}

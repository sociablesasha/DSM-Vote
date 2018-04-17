package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import dto.UserDTO;

@WebServlet("/LoginAction")
public class LoginAction extends Action {

	private static final long serialVersionUID = 1L;
	private HttpSession httpSession;
	protected UserDTO userDTO;
	private UserDAO userDAO;

	public LoginAction() {
		super();
	}

	@Override
	public void createObejcts(HttpServletRequest request) {
		httpSession = request.getSession();
		userDTO = new UserDTO();
		userDAO = new UserDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		createObejcts(request);
		request.setCharacterEncoding("UTF-8");

		boolean result;
		Object moveObject;
		
		result = (userDTO = getUserForSession(httpSession, userDTO)) == null;
		moveObject = result ? null : MainJSP(response);
		
		result = result ? (userDTO = getUserForRequest(request, userDTO)) != null : false;
		moveObject = moveObject == null ? (result ? null : BackJSP(response)) : moveObject;

		result = result ? userDAO.doLogin(userDTO) : false;
		moveObject = moveObject == null ? (result ? null : BackJSP(response)) : moveObject;
		
		result = result ? setUserForSession(httpSession, userDTO) : false;
		moveObject = moveObject == null ? (result ? MainJSP(response) : null) : moveObject;
	}
	
}

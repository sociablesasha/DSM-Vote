package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dto.UserDTO;

@WebServlet("/DownloadAction")
public class DownloadAction extends Action {
	
	private static final long serialVersionUID = 1L;
	private HttpSession httpSession;
	private UserDTO userDTO;
	
    public DownloadAction() {
        super();
    }

	@Override
	public void createObejcts(HttpServletRequest request) {
		httpSession = request.getSession();
		userDTO = new UserDTO();
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		createObejcts(request);
		request.setCharacterEncoding("UTF-8");

		boolean result;
		Object moveObject;
		
		result = (userDTO = getUserForSession(httpSession, userDTO)) != null;
		moveObject = result ? userDTO.getIdentification().equals("admin") ? null : MainJSP(response) : LoginJSP(response);
		
		result = result ? fileDownload(response) : false;
		moveObject = moveObject == null ? (result ? null : MainJSP(response)) : moveObject;
	}

}

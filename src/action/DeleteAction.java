package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CandidateDAO;
import dto.CandidateDTO;
import dto.UserDTO;

@WebServlet("/DeleteAction")
public class DeleteAction extends Action {
	
	private static final long serialVersionUID = 1L;
	private HttpSession httpSession;
	private UserDTO userDTO;
	private CandidateDTO candidateDTO;
	private CandidateDAO candidateDAO;
	
    public DeleteAction() {
        super();
    }

	@Override
	public void createObejcts(HttpServletRequest request) {
		httpSession = request.getSession();
		userDTO = new UserDTO();
		candidateDTO = new CandidateDTO();
		candidateDAO = new CandidateDAO();
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		createObejcts(request);
		request.setCharacterEncoding("UTF-8");

		boolean result;
		Object moveObject;
		
		String saveDirectory = getServletContext().getRealPath("/Upload");
		
		result = (userDTO = getUserForSession(httpSession, userDTO)) != null;
		moveObject = result ? userDTO.getIdentification().equals("admin") ? null : MainJSP(response) : LoginJSP(response);
		
		result = result ? (candidateDTO = getCandidateForRequest(request, candidateDTO)) != null : false;
		moveObject = moveObject == null ? (result ? null : MainJSP(response)) : moveObject;
		
		result = result ? candidateDAO.doDelete(candidateDTO) : false;
		moveObject = moveObject == null ? (result ? null : MainJSP(response)) : moveObject;
		
		moveObject = moveObject == null ? (result && fileMove(candidateDTO, saveDirectory) ? AdminMainJSP(response) : AdminMainJSP(response)) : moveObject;
		
		noCache(response);
	}

}

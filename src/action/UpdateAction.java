package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.CandidateDAO;
import dto.CandidateDTO;
import dto.UserDTO;

@WebServlet("/UpdateAction")
public class UpdateAction extends Action {

	private static final long serialVersionUID = 1L;
	private HttpSession httpSession;
	private UserDTO userDTO;
	private CandidateDTO candidateDTO;
	private CandidateDAO candidateDAO;

	public UpdateAction() {
		super();
	}

	@Override
	public void createObejcts(HttpServletRequest request) {
		httpSession = request.getSession();
		userDTO = new UserDTO();
		candidateDTO = new CandidateDTO();
		candidateDAO = new CandidateDAO();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		createObejcts(request);
		request.setCharacterEncoding("UTF-8");

		boolean result;
		Object moveObject;
		
		String saveDirectory = getServletContext().getRealPath("/Upload");
		int maxPostSize = 20 * 1024 * 1024;
		String encoding = "UTF-8";
		
		MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());
		
		result = (userDTO = getUserForSession(httpSession, userDTO)) != null;
		moveObject = result ? userDTO.getIdentification().equals("admin") ? null : MainJSP(response) : LoginJSP(response);
	
		result = result ? (candidateDTO = getCandidateForRequest(multipartRequest, candidateDTO)) != null : false;
		moveObject = moveObject == null ? (result ? null : BackJSP(response)) : moveObject;
		
		if (candidateDTO != null)  { candidateDTO.setNumber(Integer.valueOf(multipartRequest.getParameter("number"))); }
		
		result = result ? candidateDAO.doUpdate(candidateDTO) : false;
		moveObject = moveObject == null ? (result ? null : BackJSP(response)) : moveObject;
		
		result = result ? fileUpload(multipartRequest, candidateDTO, saveDirectory) : false;
		moveObject = moveObject == null ? (result ? null : BackJSP(response)) : moveObject;

		result = result ? fileDelete(multipartRequest, saveDirectory) : fileDelete(multipartRequest, saveDirectory);
		moveObject = moveObject == null ? (result ? MainJSP(response) : BackJSP(response)) : moveObject;
	}

}

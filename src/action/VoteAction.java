package action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import dao.VoteDAO;
import dto.UserDTO;
import dto.VoteDTO;

@WebServlet("/VoteAction")
public class VoteAction extends Action {

	private static final long serialVersionUID = 1L;
	private HttpSession httpSession;
	private UserDTO userDTO;
	private UserDAO userDAO;
	private VoteDTO voteDTO;
	private VoteDAO voteDAO;

	public VoteAction() {
		super();
	}

	@Override
	public void createObejcts(HttpServletRequest request) {
		httpSession = request.getSession();
		userDTO = new UserDTO();
		userDAO = new UserDAO();
		voteDTO = new VoteDTO();
		voteDAO = new VoteDAO();
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		createObejcts(request);
		request.setCharacterEncoding("UTF-8");
		
		boolean result;
		Object moveObject;
		
		result = (userDTO = getUserForSession(httpSession, userDTO)) != null;
		moveObject = result ? null : LoginJSP(response);

		result = result ? (voteDTO = getVoteForRequest(request, voteDTO)) != null : false;
		moveObject = moveObject == null ? (result ? null : VoteJSP(response)) : moveObject;

		result = result ? (userDTO = userDAO.doGet(userDTO)).getDone().equals("n") : false;
		moveObject = moveObject == null ? (result ? null : MainJSP(response)) : moveObject;
		
		result = result ? voteDAO.doVote(userDTO, voteDTO) : false;
		moveObject = moveObject == null ? (result ? DoneJSP(response) : VoteJSP(response)) : moveObject;
	}

}

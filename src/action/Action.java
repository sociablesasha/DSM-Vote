package action;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.oreilly.servlet.MultipartRequest;

import dao.CandidateDAO;
import dao.VoteDAO;
import dto.CandidateDTO;
import dto.UserDTO;
import dto.VoteDTO;

public class Action extends HttpServlet {

	public static final long serialVersionUID = 1L;

	public void createObejcts(HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

	// Session <- UserDTO
	public boolean setUserForSession(HttpSession httpSession, UserDTO userDTO) {
		boolean result;

		try {
			httpSession.setAttribute("identification", userDTO.getIdentification());
			httpSession.setAttribute("password", userDTO.getPassword());
			httpSession.setAttribute("name", userDTO.getName());
			result = true;
		} catch (Exception exception) {
			result = false;
		}

		return result;
	}

	// UserDTO <- Session
	public UserDTO getUserForSession(HttpSession httpSession, UserDTO userDTO) {
		try {
			userDTO = new UserDTO();
			userDTO.setIdentification((String) httpSession.getAttribute("identification"));
			userDTO.setPassword((String) httpSession.getAttribute("password"));
			userDTO.setName((String) httpSession.getAttribute("name"));
			userDTO = userDTO.getIdentification() == null || userDTO.getPassword() == null || userDTO.getName() == null
					? null
					: userDTO;
		} catch (Exception exception) {
			userDTO = null;
		}

		return userDTO;
	}

	// UserDTO <- Request
	public UserDTO getUserForRequest(HttpServletRequest request, UserDTO userDTO) {
		try {
			userDTO = new UserDTO();
			userDTO.setIdentification(request.getParameter("identification"));
			userDTO.setPassword(request.getParameter("password"));
			userDTO.setName(request.getParameter("name"));

			userDTO = userDTO.getIdentification() == null || userDTO.getPassword() == null || userDTO.getName() == null
					? null
					: userDTO;
		} catch (Exception exception) {
			userDTO = null;
		}

		return userDTO;
	}

	// VoteDTO <- Request
	public VoteDTO getVoteForRequest(HttpServletRequest request, VoteDTO voteDTO) {
		try {
			voteDTO = new VoteDTO();
			voteDTO.setGovernment(Integer.valueOf(request.getParameter("government")));
			voteDTO.setMinistry(Integer.valueOf(request.getParameter("ministry")));
			voteDTO = String.valueOf(voteDTO.getGovernment()) == null || String.valueOf(voteDTO.getMinistry()) == null
					? null
					: voteDTO;
		} catch (Exception exception) {
			voteDTO = null;
		}

		return voteDTO;
	}

	// CandidateDTO <- Request
	public CandidateDTO getCandidateForRequest(HttpServletRequest request, CandidateDTO candidateDTO) {
		try {
			candidateDTO = new CandidateDTO();
			candidateDTO.setDivision(request.getParameter("division"));
			candidateDTO.setNumber(Integer.valueOf(request.getParameter("number")));
			candidateDTO = candidateDTO.getDivision() == null || String.valueOf(candidateDTO.getNumber()) == null ? null
					: candidateDTO;
		} catch (Exception exception) {
			candidateDTO = null;
		}

		return candidateDTO;
	}

	// CandidateDTO <- Request
	public CandidateDTO getCandidateForRequest(MultipartRequest multipartRequest, CandidateDTO candidateDTO) {
		try {
			candidateDTO = new CandidateDTO();
			candidateDTO.setDivision(multipartRequest.getParameter("division"));
			if (candidateDTO.getDivision().equals("학생회")) {
				candidateDTO.setMaster(multipartRequest.getParameter("masterGovernment"));
				candidateDTO.setSlave1(multipartRequest.getParameter("slave1Government"));
				candidateDTO.setSlave2(multipartRequest.getParameter("slave2Government"));
				candidateDTO.setSlave3(multipartRequest.getParameter("slave3Government"));
				candidateDTO.setCommitment(multipartRequest.getParameter("commitment"));
				candidateDTO = candidateDTO.getMaster() == null || candidateDTO.getSlave1() == null
						|| candidateDTO.getSlave2() == null || candidateDTO.getSlave3() == null
						|| candidateDTO.getCommitment() == null ? null : candidateDTO;
			} else if (candidateDTO.getDivision().equals("자치부")) {
				candidateDTO.setMaster(multipartRequest.getParameter("masterMinistry"));
				candidateDTO.setSlave1(multipartRequest.getParameter("slaveMinistry"));
				candidateDTO.setCommitment(multipartRequest.getParameter("commitment"));
				candidateDTO = candidateDTO.getMaster() == null || candidateDTO.getSlave1() == null
						|| candidateDTO.getCommitment() == null ? null : candidateDTO;
			} else {
				candidateDTO = null;
			}
		} catch (Exception exception) {
			candidateDTO = null;
		}

		return candidateDTO;
	}

	// About Multi File Upload
	public boolean fileUpload(MultipartRequest multipartRequest, CandidateDTO candidateDTO, String saveDirectory) {
		boolean result;

		try {
			if (candidateDTO.getDivision().equals("학생회")) {
				result = fileUpload(multipartRequest, candidateDTO, saveDirectory, "Picture", "masterGovernmentPicture")
						|| fileUpload(multipartRequest, candidateDTO, saveDirectory, "Picture",
								"slave1GovernmentPicture")
						|| fileUpload(multipartRequest, candidateDTO, saveDirectory, "Picture",
								"slave2GovernmentPicture")
						|| fileUpload(multipartRequest, candidateDTO, saveDirectory, "Picture",
								"slave3GovernmentPicture")
						|| fileUpload(multipartRequest, candidateDTO, saveDirectory, "Poster", "poster1Poster")
						|| fileUpload(multipartRequest, candidateDTO, saveDirectory, "Poster", "poster2Poster") ? true
								: false;
			} else if (candidateDTO.getDivision().equals("자치부")) {
				result = fileUpload(multipartRequest, candidateDTO, saveDirectory, "Picture", "masterMinistryPicture")
						|| fileUpload(multipartRequest, candidateDTO, saveDirectory, "Picture", "slaveMinistryPicture")
						|| fileUpload(multipartRequest, candidateDTO, saveDirectory, "Poster", "poster1Poster")
						|| fileUpload(multipartRequest, candidateDTO, saveDirectory, "Poster", "poster2Poster") ? true
								: false;
			} else {
				result = false;
			}

		} catch (Exception exception) {
			result = false;
		}

		return result;
	}

	public boolean fileUpload(MultipartRequest multipartRequest, CandidateDTO candidateDTO, String saveDirectory,
			String type, String fileBeforeName) {
		boolean result;

		try {
			String folderName;
			String fileAfterName;

			File folder;
			File beforeFile;
			File afterFile;

			folderName = saveDirectory + "/" + type + "/" + candidateDTO.getDivision() + "/" + candidateDTO.getNumber();
			fileAfterName = saveDirectory + "/" + type + "/" + candidateDTO.getDivision() + "/"
					+ candidateDTO.getNumber() + "/" + fileBeforeName + ".png";

			folder = new File(folderName);
			beforeFile = multipartRequest.getFile(fileBeforeName);
			afterFile = new File(fileAfterName);

			if (!folder.exists()) {
				folder.mkdir();
			}

			if (beforeFile != null && beforeFile.exists()) {
				if (afterFile.exists()) {
					afterFile.delete();
				}
				result = beforeFile.renameTo(afterFile);
			} else {
				result = false;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			result = false;
		}

		return result;
	}

	// About File Download
	public boolean fileDownload(HttpServletResponse response) {
		boolean result;
		try {
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
			String year = simpleDateFormat.format(date);

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("testSheet");

			HSSFRow row;
			HSSFCell cell;

			CandidateDTO candidateDTO = new CandidateDTO();
			CandidateDAO candidateDAO = new CandidateDAO();
			VoteDAO voteDAO = new VoteDAO();
			ArrayList<CandidateDTO> list = new ArrayList<CandidateDTO>();
			String division = null;
			int index;

			division = "학생회";
			list = candidateDAO.doList(division);
			index = 0;
			row = sheet.createRow(index);
			cell = row.createCell(index);
			cell.setCellValue(division);
			cell = row.createCell(1);
			cell.setCellValue("후보 " + list.size() + "명");

			index = 1;
			row = sheet.createRow(index);
			cell = row.createCell(0);
			cell.setCellValue("기호");
			cell = row.createCell(1);
			cell.setCellValue("학생 회장");
			cell = row.createCell(2);
			cell.setCellValue("3학년 부회장");
			cell = row.createCell(3);
			cell.setCellValue("2학년 부회장");
			cell = row.createCell(4);
			cell.setCellValue("1학년 부회장");
			cell = row.createCell(5);
			cell.setCellValue("투표");

			if (list.size() == 1) {
				candidateDTO.setDivision(division);
				candidateDTO.setNumber(1);
				candidateDTO = candidateDAO.doDetail(candidateDTO);
				index = index + 1;
				row = sheet.createRow(index);
				cell = row.createCell(0);
				cell.setCellValue("찬성");
				cell = row.createCell(1);
				cell.setCellValue(candidateDTO.getMaster());
				cell = row.createCell(2);
				cell.setCellValue(candidateDTO.getSlave1());
				cell = row.createCell(3);
				cell.setCellValue(candidateDTO.getSlave2());
				cell = row.createCell(4);
				cell.setCellValue(candidateDTO.getSlave3());
				cell = row.createCell(5);
				cell.setCellValue(voteDAO.doResult(candidateDTO));

				candidateDTO.setDivision(division);
				candidateDTO.setNumber(2);
				candidateDTO = candidateDAO.doDetail(candidateDTO);
				index = index + 1;
				row = sheet.createRow(index);
				cell = row.createCell(0);
				cell.setCellValue("반대");
				cell = row.createCell(1);
				cell.setCellValue(candidateDTO.getMaster());
				cell = row.createCell(2);
				cell.setCellValue(candidateDTO.getSlave1());
				cell = row.createCell(3);
				cell.setCellValue(candidateDTO.getSlave2());
				cell = row.createCell(4);
				cell.setCellValue(candidateDTO.getSlave3());
				cell = row.createCell(5);
				cell.setCellValue(voteDAO.doResult(candidateDTO));
			} else {
				for (int temp = 0; temp < list.size(); temp++) {
					candidateDTO = list.get(temp);
					index = index + 1;
					row = sheet.createRow(index);
					cell = row.createCell(0);
					cell.setCellValue(candidateDTO.getNumber());
					cell = row.createCell(1);
					cell.setCellValue(candidateDTO.getMaster());
					cell = row.createCell(2);
					cell.setCellValue(candidateDTO.getSlave1());
					cell = row.createCell(3);
					cell.setCellValue(candidateDTO.getSlave2());
					cell = row.createCell(4);
					cell.setCellValue(candidateDTO.getSlave3());
					cell = row.createCell(5);
					cell.setCellValue(voteDAO.doResult(candidateDTO));
				}
			}
			
			division = "자치부";
			list = candidateDAO.doList(division);
			index = index + 2;
			row = sheet.createRow(index);
			cell = row.createCell(0);
			cell.setCellValue(division);
			cell = row.createCell(1);
			cell.setCellValue("후보 " + list.size() + "명");

			index = index + 1;
			row = sheet.createRow(index);
			cell = row.createCell(0);
			cell.setCellValue("기호");
			cell = row.createCell(1);
			cell.setCellValue("자치 부장");
			cell = row.createCell(2);
			cell.setCellValue("자치 차장");
			cell = row.createCell(5);
			cell.setCellValue("투표");

			if (list.size() == 1) {
				candidateDTO.setDivision(division);
				candidateDTO.setNumber(1);
				candidateDTO = candidateDAO.doDetail(candidateDTO);
				index = index + 1;
				row = sheet.createRow(index);
				cell = row.createCell(0);
				cell.setCellValue("찬성");
				cell = row.createCell(1);
				cell.setCellValue(candidateDTO.getMaster());
				cell = row.createCell(2);
				cell.setCellValue(candidateDTO.getSlave1());
				cell = row.createCell(5);
				cell.setCellValue(voteDAO.doResult(candidateDTO));

				candidateDTO.setDivision(division);
				candidateDTO.setNumber(2);
				index = index + 1;
				row = sheet.createRow(index);
				cell = row.createCell(0);
				cell.setCellValue("반대");
				cell = row.createCell(1);
				cell.setCellValue(candidateDTO.getMaster());
				cell = row.createCell(2);
				cell.setCellValue(candidateDTO.getSlave1());
				cell = row.createCell(5);
				cell.setCellValue(voteDAO.doResult(candidateDTO));
			} else {
				for (int temp = 0; temp < list.size(); temp++) {
					candidateDTO = list.get(temp);
					index = index + 1;
					row = sheet.createRow(index);
					cell = row.createCell(0);
					cell.setCellValue(candidateDTO.getNumber());
					cell = row.createCell(1);
					cell.setCellValue(candidateDTO.getMaster());
					cell = row.createCell(2);
					cell.setCellValue(candidateDTO.getSlave1());
					cell = row.createCell(5);
					cell.setCellValue(voteDAO.doResult(candidateDTO));
				}
			}
			String fileName = "DSM" + year + "년투표결과.xls";
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
			response.setContentType("application/vnd.ms.excel");

			ServletOutputStream servletOutputStream = response.getOutputStream();
			workbook.write(servletOutputStream);

			workbook.close();
			servletOutputStream.close();
			result = true;
		} catch (Exception exception) {
			exception.printStackTrace();
			result = false;
		}
		return result;
	}

	// About Multi File Delete
	public boolean fileDelete(MultipartRequest multipartRequest, String saveDirectory) {
		boolean result;

		try {
			Enumeration<?> fileNames;
			File file;
			String fileName;

			fileNames = multipartRequest.getFileNames();

			while (fileNames.hasMoreElements()) {
				fileName = (String) fileNames.nextElement();
				fileName = multipartRequest.getFilesystemName(fileName);
				file = fileName != null ? new File(saveDirectory + "/" + fileName) : null;
				if (file != null && file.exists()) {
					file.delete();
				}
			}
			result = true;
		} catch (Exception exception) {
			exception.printStackTrace();
			result = false;
		}

		return result;
	}

	// About File Check
	public boolean fileCheck(CandidateDTO candidateDTO, String saveDirectory, String type, String picture) {
		boolean result;
		try {
			String fileName;
			File file;

			fileName = saveDirectory + "/" + type + "/" + candidateDTO.getDivision() + "/" + candidateDTO.getNumber()
					+ "/" + picture + type + ".png";
			file = new File(fileName);

			result = file.exists();
		} catch (Exception exception) {
			result = false;
		}

		return result;
	}

	public boolean fileMove(CandidateDTO candidateDTO, String saveDirectory) {
		boolean result;
		try {
			CandidateDTO tempCandidateDTO;
			String folderName, tempFolderName;
			File file, tempFile;
			File[] innerFiles;

			folderName = saveDirectory + "/Picture/" + candidateDTO.getDivision() + "/" + candidateDTO.getNumber();
			file = new File(folderName);
			innerFiles = file.listFiles();

			for (int i = 0; i < innerFiles.length; i++) {
				innerFiles[i].delete();
			}
			file.delete();

			folderName = saveDirectory + "/Poster/" + candidateDTO.getDivision() + "/" + candidateDTO.getNumber();
			file = new File(folderName);
			innerFiles = file.listFiles();

			for (int i = 0; i < innerFiles.length; i++) {
				innerFiles[i].delete();
			}
			file.delete();

			ArrayList<CandidateDTO> renameList = new CandidateDAO().doList(candidateDTO.getDivision());
			for (int i = 0; i < renameList.size(); i++) {
				tempCandidateDTO = renameList.get(i);
				if (tempCandidateDTO.getNumber() >= candidateDTO.getNumber()) {
					tempFolderName = saveDirectory + "/Picture/" + tempCandidateDTO.getDivision() + "/"
							+ tempCandidateDTO.getNumber();
					tempFile = new File(tempFolderName);
					if (!tempFile.exists()) {
						tempFile.mkdirs();
					}

					if (tempCandidateDTO.getDivision().equals("학생회")) {
						folderName = saveDirectory + "/Picture/" + tempCandidateDTO.getDivision() + "/"
								+ (tempCandidateDTO.getNumber() + 1) + "/masterGovernmentPicture.png";
						tempFolderName = saveDirectory + "/Picture/" + tempCandidateDTO.getDivision() + "/"
								+ tempCandidateDTO.getNumber() + "/masterGovernmentPicture.png";
						file = new File(folderName);
						tempFile = new File(tempFolderName);
						if (file != null && file.exists()) {
							file.renameTo(tempFile);
						}

						folderName = saveDirectory + "/Picture/" + tempCandidateDTO.getDivision() + "/"
								+ (tempCandidateDTO.getNumber() + 1) + "/slave1GovernmentPicture.png";
						tempFolderName = saveDirectory + "/Picture/" + tempCandidateDTO.getDivision() + "/"
								+ tempCandidateDTO.getNumber() + "/slave1GovernmentPicture.png";
						file = new File(folderName);
						tempFile = new File(tempFolderName);
						if (file != null && file.exists()) {
							file.renameTo(tempFile);
						}

						folderName = saveDirectory + "/Picture/" + tempCandidateDTO.getDivision() + "/"
								+ (tempCandidateDTO.getNumber() + 1) + "/slave2GovernmentPicture.png";
						tempFolderName = saveDirectory + "/Picture/" + tempCandidateDTO.getDivision() + "/"
								+ tempCandidateDTO.getNumber() + "/slave2GovernmentPicture.png";
						file = new File(folderName);
						tempFile = new File(tempFolderName);
						if (file != null && file.exists()) {
							file.renameTo(tempFile);
						}

						folderName = saveDirectory + "/Picture/" + tempCandidateDTO.getDivision() + "/"
								+ (tempCandidateDTO.getNumber() + 1) + "/slave3GovernmentPicture.png";
						tempFolderName = saveDirectory + "/Picture/" + tempCandidateDTO.getDivision() + "/"
								+ tempCandidateDTO.getNumber() + "/slave3GovernmentPicture.png";
						file = new File(folderName);
						tempFile = new File(tempFolderName);
						if (file != null && file.exists()) {
							file.renameTo(tempFile);
						}
					} else if (tempCandidateDTO.getDivision().equals("자치부")) {
						folderName = saveDirectory + "/Picture/" + tempCandidateDTO.getDivision() + "/"
								+ (tempCandidateDTO.getNumber() + 1) + "/masterMinistryPicture.png";
						tempFolderName = saveDirectory + "/Picture/" + tempCandidateDTO.getDivision() + "/"
								+ tempCandidateDTO.getNumber() + "/masterMinistryPicture.png";
						file = new File(folderName);
						tempFile = new File(tempFolderName);
						if (file != null && file.exists()) {
							file.renameTo(tempFile);
						}

						folderName = saveDirectory + "/Picture/" + tempCandidateDTO.getDivision() + "/"
								+ (tempCandidateDTO.getNumber() + 1) + "/slaveMinistryPicture.png";
						tempFolderName = saveDirectory + "/Picture/" + tempCandidateDTO.getDivision() + "/"
								+ tempCandidateDTO.getNumber() + "/slaveMinistryPicture.png";
						file = new File(folderName);
						tempFile = new File(tempFolderName);
						if (file != null && file.exists()) {
							file.renameTo(tempFile);
						}
					}

					/// error 고쳐야함
					folderName = saveDirectory + "/Poster/" + tempCandidateDTO.getDivision() + "/"
							+ (tempCandidateDTO.getNumber() + 1) + "/poster1Poster.png";
					tempFolderName = saveDirectory + "/Poster/" + tempCandidateDTO.getDivision() + "/"
							+ tempCandidateDTO.getNumber() + "/poster1Poster.png";
					file = new File(folderName);
					tempFile = new File(tempFolderName);
					if (file != null && file.exists()) {
						file.renameTo(tempFile);
					}

					System.out.println(folderName);
					System.out.println(tempFolderName);

					folderName = saveDirectory + "/Poster/" + tempCandidateDTO.getDivision() + "/"
							+ (tempCandidateDTO.getNumber() + 1) + "/poster2Poster.png";
					tempFolderName = saveDirectory + "/Poster/" + tempCandidateDTO.getDivision() + "/"
							+ tempCandidateDTO.getNumber() + "/poster2Poster.png";
					file = new File(folderName);
					tempFile = new File(tempFolderName);
					if (file != null && file.exists()) {
						file.renameTo(tempFile);
					}

					System.out.println(folderName);
					System.out.println(tempFolderName);

					folderName = saveDirectory + "/Picture/" + tempCandidateDTO.getDivision() + "/"
							+ tempCandidateDTO.getNumber();
					file = new File(folderName);
					if (file != null && file.exists()) {
						file.delete();
					}

					folderName = saveDirectory + "/Poster/" + tempCandidateDTO.getDivision() + "/"
							+ tempCandidateDTO.getNumber();
					file = new File(folderName);
					if (file != null && file.exists()) {
						file.delete();
					}
				}
			}

			result = true;
		} catch (Exception exception) {
			exception.printStackTrace();
			result = false;
		}

		return result;
	}

	// URL Handler
	public Object toMoveJSP(HttpServletResponse response, String URL) {
		try {
			response.sendRedirect(URL);
		} catch (Exception exception) {
			ErrorJSP(response);
		}
		return new Object();
	}

	public Object ErrorJSP(HttpServletResponse response) {
		String URL = "/vote/Error.jsp";
		return toMoveJSP(response, URL);
	}

	public Object BackJSP(HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("<script> history.back(); </script>");
		} catch (Exception exception) {
			ErrorJSP(response);
		}
		return new Object();
	}

	public Object MainJSP(HttpServletResponse response) {
		String URL = "/vote/Main.jsp";
		return toMoveJSP(response, URL);
	}

	public Object LoginJSP(HttpServletResponse response) {
		String URL = "/vote/Login.jsp";
		return toMoveJSP(response, URL);
	}

	public Object LogoutJSP(HttpServletResponse response) {
		String URL = "/vote/LogoutAction";
		return toMoveJSP(response, URL);
	}

	public Object VoteJSP(HttpServletResponse response) {
		String URL = "/vote/Vote.jsp";
		return toMoveJSP(response, URL);
	}

	public Object DoneJSP(HttpServletResponse response) {
		String URL = "/vote/Done.jsp";
		return toMoveJSP(response, URL);
	}

	// 수정 필요
	public Object DetailJSP(HttpServletResponse response, CandidateDTO candidateDTO) {
		String URL = "/vote/Detail.jsp?division=" + candidateDTO.getDivision() + "&number=" + candidateDTO.getNumber();
		return toMoveJSP(response, URL);
	}

	public Object AdminMainJSP(HttpServletResponse response) {
		String URL = "/vote/Admin/Main.jsp";
		return toMoveJSP(response, URL);
	}

	public void noCache(HttpServletResponse response) {
		response.setHeader("cache-control", "no-cache");
		response.setHeader("expires", "0");
		response.setHeader("pragma", "no-cache");
	}
}

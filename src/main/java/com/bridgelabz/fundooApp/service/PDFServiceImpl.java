package com.bridgelabz.fundooApp.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundooApp.exception.UserException;
import com.bridgelabz.fundooApp.model.Note;
import com.bridgelabz.fundooApp.model.User;
import com.bridgelabz.fundooApp.repository.NoteRepository;
import com.bridgelabz.fundooApp.repository.UserRepository;
import com.bridgelabz.fundooApp.utility.JWTTokenGenerator;

@Service
public class PDFServiceImpl implements PDFService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private JWTTokenGenerator tokenGenerator;

	public String CREATED_PDF = "/home/admin1/Downloads/Vish/bdzl/MyPDFs/";
	static double currentHeight = 0;
	static PDPageContentStream cs = null;

	@SuppressWarnings("resource")
	@Override
	public String createDocument(String token, String fileName) {
		// TODO Auto-generated method stub
		try {
			PDDocument document = new PDDocument();

			String userId = tokenGenerator.verifyToken(token);
			Optional<User> optUser = userRepository.findByUserId(userId);
			PDDocumentInformation pdfAttribute = document.getDocumentInformation();

			if (optUser.isPresent()) {
				User user = optUser.get();
				String userName = user.getName();
				pdfAttribute.setAuthor(userName);
			} else {
				throw new UserException("user dose not exist");
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String currentDate = dateFormat.format(date);
			document.save(CREATED_PDF + fileName + "_" + currentDate + ".pdf");
			document.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		return "PDF created sucessfully.......";
	}

	@Override
	public String extractNoteToPDF(String token, String fileName) {
		// TODO Auto-generated method stub
		try {
			String userId = tokenGenerator.verifyToken(token);
			Optional<User> optUser = userRepository.findByUserId(userId);

			User user = optUser.get();
			String imgPath = user.getProfilePicture();
			System.out.println(imgPath);
			PDDocument document = new PDDocument();
			PDPage page = new PDPage();
			document.addPage(page);
			String line;

			PDImageXObject profilePic = PDImageXObject.createFromFile(imgPath, document);
			cs = new PDPageContentStream(document, page);
			cs.drawImage(profilePic, 520, 670,100,100);

			cs.beginText();
			cs.setFont(PDType1Font.TIMES_ROMAN, 12);
			cs.newLineAtOffset(20, 750);
			cs.setLeading(12f + 12);
			List<Note> notes = noteRepository.findAll();
			for (Note note : notes) {
				String title = note.getTitle();
				showMultiLineText(document, title, 20, 750, 750, 580, page, PDType1Font.TIMES_ROMAN, 16);
				String description = note.getDescription();
				showMultiLineText(document, description, 20, 750, 750, 580, page, PDType1Font.TIMES_ROMAN, 16);
				cs.showText("--------------------------------------------------------------------------------------");
				cs.newLine();
			}
			if (cs != null) {
				cs.endText();
				cs.close();
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			String currentDate = dateFormat.format(date);

			document.save(CREATED_PDF + fileName + "_" + currentDate + ".pdf");
			document.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		return "Notes are extracted to PDF";
	}

	private static void showMultiLineText(PDDocument document, String text, int x, int y, int allowedWidth,
			double allowedHeight, PDPage page, PDFont font, int fontSize) {
		// TODO Auto-generated method stub
		try {
			List<String> lines = new ArrayList<String>();
			String line = "";

			String[] words = text.split(" ");
			for (String word : words) {
				if (!line.isEmpty()) {
					line += " ";
				}
				int size = (int) (fontSize * font.getStringWidth(line + word) / 1000);
				if (size > allowedWidth) {
					lines.add(line);
					line = word;
				} else {
					line += word;
				}
			}
			lines.add(line);
			for (String ln : lines) {
				currentHeight = currentHeight + 1.2 * fontSize;

				if (currentHeight >= allowedHeight) {
					page = new PDPage();
					document.addPage(page);
					currentHeight = 0;
					cs.endText();
					cs.close();
					cs = new PDPageContentStream(document, page);
					cs.beginText();
					cs.setFont(PDType1Font.TIMES_ROMAN, 20);
					cs.newLineAtOffset(20, 750);
					cs.setLeading(12 + 20);
				}
				cs.showText(ln);
				cs.newLine();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
	}

}

package com.bridgelabz.fundooApp.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.bridgelabz.fundooApp.dto.*;
import com.bridgelabz.fundooApp.dto.LoginDto;
import com.bridgelabz.fundooApp.dto.UserDto;
import com.bridgelabz.fundooApp.exception.UserException;
import com.bridgelabz.fundooApp.model.User;
import com.bridgelabz.fundooApp.repository.UserRepository;
import com.bridgelabz.fundooApp.utility.EncryptUtil;
import com.bridgelabz.fundooApp.utility.ITokenGenerator;
import com.bridgelabz.fundooApp.utility.RabbitMQSender;

/**
 * @author admin123
 *
 */
@Service
public class UserServiceImpl implements UserService {

	/**
	 * 
	 */
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	RabbitMQSender rabbitMQMailSender;

	@Autowired
	private ITokenGenerator tokenGenerator;
	
	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	private final String fileBasePath = "/home/admin1/Downloads/Vish/bdzl/ProfilePics/";

	private final static String Key = "userOne";
	@Override
	public String registrationUser(UserDto userDto, StringBuffer requestUrl) {

		boolean ismail = userRepository.findByEmail(userDto.getEmail()).isPresent();
		if (!ismail) {
			User user = mapper.map(userDto, User.class);
			user.setPassword(encoder.encode(userDto.getPassword()));
			// user.setCreationTime(LocalTime.now());
			try {
				user.setCreationTime(LocalDateTime.now());
				user.setUpdateTime(LocalDateTime.now());
				User savedUser = userRepository.save(user);
				String token = tokenGenerator.generateToken(savedUser.getUserid());
				// put token into redis
				String activationUrl = getLink(requestUrl, "/verification/", token);
				Email email = new Email();
				email.setTo("iamvish.net@gmail.com");
				email.setSubject("Account verification");
				email.setBody("Please verify your email id by using below link \n" + activationUrl);
				rabbitMQMailSender.sendMessageToQueue(email);
				return "Verification mail send successfully";
			} catch (Exception e) {
				e.printStackTrace();
				throw new UserException("Something not right");
			}
		} else {
			throw new UserException("User already exist");
		}
	}

	@Override
	public String loginUser(LoginDto loginDto) {

		Optional<User> optUser = userRepository.findByEmail(loginDto.getEmail());
		if (optUser.isPresent()) {
			User user = optUser.get();
			try {

				if (EncryptUtil.isPassword(loginDto, user)) {
					if (user.isVerified()) {
						String token = tokenGenerator.generateToken(user.getUserid());
						String emailId = user.getEmail();
						redisTemplate.opsForHash().put(Key, emailId, token);
						return token;
					} else {
						throw new UserException("please verify your email");
					}
				} else {
					throw new UserException("incorrect password");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new UserException("something went wrong");
			}
		} else {
			throw new UserException("User not found");
		}

	}

	@Override
	public String forgetPassword(String emailId, StringBuffer requestUrl) {
		Optional<User> optUser = userRepository.findByEmail(emailId);
		if (optUser.isPresent()) {
			User user = optUser.get();
			String id = user.getUserid();
			try {
				String token = tokenGenerator.generateToken(id);
				String resetUrl = getLink(requestUrl, "/resetpassword/", token);
				Email email = new Email();
				email.setTo("iamvish.net@gmail.com");
				email.setSubject("resetPassword");
				email.setBody("reset your password \n" + resetUrl);
				rabbitMQMailSender.sendMessageToQueue(email);
				return "Mail sent";

			} catch (Exception e) {

				e.printStackTrace();
				throw new UserException("internal server error");
			}
		} else {
			throw new UserException("User not present..");
		}

	}

	@Override
	public String restSetPassword(String emailId, String password) {
		String token = (String) redisTemplate.opsForHash().get(Key, emailId);
		String userid = tokenGenerator.verifyToken(token);
		Optional<User> optUser = userRepository.findByUserId(userid);
		if (optUser.isPresent()) {
			User user = optUser.get();
			// Encoded password
			user.setPassword(encoder.encode(password));
			// user.setPassword(password);
			user.setUpdateTime(LocalDateTime.now());
			userRepository.save(user);
			// return new Response(200, "password changed successfully..", null);
			return "Password changed successfully..";

		} else {
			throw new UserException("User not verified");
		}
	}

	@Override
	public String validateUser(String token) {
		String id = tokenGenerator.verifyToken(token);
		Optional<User> optionalUser = userRepository.findByUserId(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setVerified(true);
			userRepository.save(user);
			return "User verified";
		} else {
			throw new UserException("User not verified");
		}
	}

	@SuppressWarnings("unused")
	private String getLink(StringBuffer requestUrl, String mappingUrl, String token) {
		String url = requestUrl.substring(0, requestUrl.lastIndexOf("/")) + mappingUrl + token;
		return url;
	}

	public String uploadProfilePicture(String token, MultipartFile file) {
		try {
			String userId = tokenGenerator.verifyToken(token);
			Optional<User> user = userRepository.findByUserId(userId);
			String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			Path path = Paths.get(fileBasePath, fileName);
			String imgPath = path.toString();
			if (!user.isPresent()) {
				throw new UserException("user not exist");
			}
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			user.get().setProfilePicture(imgPath);
			userRepository.save(user.get());

			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/home/admin1/Aimage")
					.path(fileName).toUriString();
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		return "Profile picture uploaded...";
	}

	public String getProfilePicture(String token, String fileName) {
		String id = tokenGenerator.verifyToken(token);
		Optional<User> user = userRepository.findByUserId(id);

		if (user.isPresent()) {
			try {
				Path path = Paths.get(fileBasePath + fileName);

				Resource resource = new UrlResource(path.toUri());

				if (resource.exists() || resource.isReadable()) {
					String resoucePath = resource.toString();
					return resoucePath;
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.getMessage();
			}
		}
		throw new UserException("user not exist");
	}

}
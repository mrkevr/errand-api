package dev.mrkevr.errandapi.user;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import dev.mrkevr.errandapi.library.exception.ApiException;
import dev.mrkevr.errandapi.library.exception.ResourceNotFoundException;
import dev.mrkevr.errandapi.util.ImageFileManager;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final ImageFileManager imageFileManager;

	public List<UserResponse> getAll(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<User> users = userRepository.findAll(pageRequest);
		return userMapper.map(users.getContent());
	}
	
	public UserResponse getById(String id) {
		 User user = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not found user with that id"));
		 return userMapper.map(user);
	}

	public UserResponse getByUsername(String username) {
		 User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("Could not found user with that username"));
		 return userMapper.map(user);
	}

	public UserResponse getByEmail(String email) {
		 User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Could not found user with that email"));
		 return userMapper.map(user);
	}
	
	public String getAvatarById(String id) {
		 String avatar = userRepository.getAvatarById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Could not found avatar with that id"));
		 return avatar;
	}
	
	@Transactional
	public UserResponse addUser(UserCreationRequest userCreationRequest, MultipartFile avatarImageFile) {
		
		User user = User.builder()
			.username(userCreationRequest.getUsername())
			.password(passwordEncoder.encode(userCreationRequest.getPassword()))
			.name(userCreationRequest.getName())
			.phone(userCreationRequest.getPhone())
			.email(userCreationRequest.getEmail())
			.aboutMe(userCreationRequest.getAboutMe())
			.rating(0)
			.build();
		
		String avatarFilePath = this.saveImageToDirectory(userCreationRequest.getUsername(), avatarImageFile);	
		user.setAvatar(avatarFilePath);
		
		User savedUser = userRepository.save(user);
		return userMapper.map(savedUser);
	}
	
	// Save the image file to target directory and return the file path
	private String saveImageToDirectory(String username, MultipartFile imageFile) {
		String filePath = "";
		try {
			filePath= imageFileManager.uploadImageFileToDirectory(username, imageFile);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApiException(e.getMessage());
		}
		return filePath;
	}
}

package dev.mrkevr.errandapi.user.api;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import dev.mrkevr.errandapi.imagefile.api.ImageFileService;
import dev.mrkevr.errandapi.user.dto.UserCreationRequest;
import dev.mrkevr.errandapi.user.dto.UserResponse;
import dev.mrkevr.errandapi.user.exception.UserNotFoundException;
import dev.mrkevr.errandapi.user.util.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserService {

	UserRepository userRepository;
	UserMapper userMapper;
	PasswordEncoder passwordEncoder;
	ImageFileService imageFileService;

	public List<UserResponse> getAll(int page, int size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<User> users = userRepository.findAll(pageRequest);
		return userMapper.map(users.getContent());
	}
	
	public List<UserResponse> getAllWithQuery(int page, int size, String query) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<User> users = userRepository.findByQuery(query, pageRequest);
		return userMapper.map(users.getContent());
	}
	
	public UserResponse getById(String id) {
		 User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Could not found user with that id"));
		 return userMapper.map(user);
	}

	public UserResponse getByUsername(String username) {
		 User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("Could not found user with that username"));
		 return userMapper.map(user);
	}

	public UserResponse getByEmail(String email) {
		 User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Could not found user with that email"));
		 return userMapper.map(user);
	}
	
	@Transactional
	public void incrementErrandsWorkedById(String id) {
		userRepository.incrementErrandsWorkedById(id);
	}
	
	@Transactional
	public UserResponse add(UserCreationRequest userCreationRequest, MultipartFile avatarImageFile) throws IOException {
		
		User user = User.builder()
			.username(userCreationRequest.getUsername())
			.password(passwordEncoder.encode(userCreationRequest.getPassword()))
			.name(userCreationRequest.getName())
			.title(userCreationRequest.getTitle())
			.phone(userCreationRequest.getPhone())
			.email(userCreationRequest.getEmail())
			.aboutMe(userCreationRequest.getAboutMe())
			.errandsWorked(0)
			.build();
		
		// Save the image file and save the image url to user
		String avatarUrl = imageFileService.save(avatarImageFile);
		user.setAvatarUrl(avatarUrl);
		
		User savedUser = userRepository.saveAndFlush(user);

		return userMapper.map(savedUser);
	}
}

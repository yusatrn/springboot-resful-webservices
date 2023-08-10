package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.EmailAlreadyExistsException;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.mapper.AutoUserMapper;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto userDto) {

//        User user = UserMapper.mapToUser(userDto);
      //  User user = modelMapper.map(userDto, User.class);

        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());
        if (optionalUser.isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists for user ");
        }
       User user = AutoUserMapper.mapper.mapToUser(userDto);
        User savedUser = userRepository.save(user);
//        UserDto savedUserDto = UserMapper.mapToUserDto(savedUser);
//        UserDto savedUserDto = modelMapper.map(savedUser, UserDto.class);
        UserDto savedUserDto = AutoUserMapper.mapper.mapToUserDto(savedUser);
        return savedUserDto;
    }

    @Override
    public UserDto getUserById(Long UserId) {
        User user = userRepository.findById(UserId).orElseThrow(
                ()->new ResourceNotFoundException("User","id",UserId)
        );
       // return UserMapper.mapToUser(user);
//        return modelMapper.map(user,UserDto.class);
        return AutoUserMapper.mapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User>  users = userRepository.findAll();
//        return  users.stream().map(UserMapper::mapToUserDto).collect(Collectors.toList()) ;
//        return  users.stream().map((user -> modelMapper.map(user, UserDto.class))).collect(Collectors.toList()) ;
        return  users.stream().map((user) -> AutoUserMapper.mapper.mapToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto user) {
        User existingUser = userRepository.findById(user.getId()).orElseThrow(
                ()-> new ResourceNotFoundException("User","id", user.getId())
        );
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        User updatedUser = userRepository.save(existingUser);
//        return UserMapper.mapToUserDto(updatedUser);
//        return modelMapper.map(updatedUser,UserDto.class);
        return AutoUserMapper.mapper.mapToUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Long UserId) {
        User existingUser = userRepository.findById(UserId).orElseThrow(
                ()-> new ResourceNotFoundException("User","id", UserId)
        );
        userRepository.deleteById(UserId);

    }
}

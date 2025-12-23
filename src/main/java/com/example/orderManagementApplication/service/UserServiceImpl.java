package com.example.orderManagementApplication.service;

import com.example.orderManagementApplication.dto.UserInputDto;
import com.example.orderManagementApplication.dto.UserOutputDto;
import com.example.orderManagementApplication.entity.User;
import com.example.orderManagementApplication.entity.enums.UserStatus;
import com.example.orderManagementApplication.exception.ResourceNotFoundException;
import com.example.orderManagementApplication.mapper.EntityDtoMapper;
import com.example.orderManagementApplication.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EntityDtoMapper entityDtoMapper;

    public UserServiceImpl(UserRepository userRepository, EntityDtoMapper entityDtoMapper) {
        this.userRepository = userRepository;
        this.entityDtoMapper = entityDtoMapper;
    }


    @Override
    public UserOutputDto createUser(UserInputDto inputDto) {
        User user = entityDtoMapper.toUserEntity(inputDto);
        if (user.getUserStatus() == null) {
            user.setUserStatus(UserStatus.ACTIVE);
        }
        User savedUser = userRepository.save(user);
        return entityDtoMapper.toUserOutputDto(user);
    }

    @Override
    public UserOutputDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        return entityDtoMapper.toUserOutputDto(user);
    }

    @Override
    public List<UserOutputDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(entityDtoMapper::toUserOutputDto)
                .toList();
    }
}

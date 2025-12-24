package com.example.orderManagementApplication.service;

import com.example.orderManagementApplication.dto.UserInputDto;
import com.example.orderManagementApplication.dto.UserOrderDto;
import com.example.orderManagementApplication.dto.UserOutputDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserOutputDto createUser(UserInputDto inputDto);

    UserOutputDto getUserById(Long id);

    List<UserOutputDto> getAllUsers();

    UserOrderDto getUserWithOrders(Long userId);

    Boolean checkUserInactivityStatus(Long userId);
}

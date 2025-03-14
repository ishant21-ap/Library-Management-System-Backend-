package com.project.Library_management_system.service;

import com.project.Library_management_system.dto.UserRequest;
import com.project.Library_management_system.model.Operator;
import com.project.Library_management_system.model.User;
import com.project.Library_management_system.model.UserFilterType;
import com.project.Library_management_system.model.UserType;
import com.project.Library_management_system.repository.UserRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import java.util.List;

@Service
public class UserService {

    private static final Log logger = LogFactory.getLog(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public User addStudent(UserRequest userRequest) {
        User user = userRequest.toUser();
        user.setUserType(UserType.STUDENT);
        return userRepository.save(user);
    }

    public List<User> filter(String filterBy, String operator, String value) {
        String[] filters = filterBy.split(",");
        String[] operators = operator.split(",");
        String[] values = value.split(",");
        StringBuilder query = new StringBuilder();

        for(int i = 0; i < operators.length; i++) {
            UserFilterType userFilterType = UserFilterType.valueOf(filters[i]);
            Operator operator1 = Operator.valueOf(operators[i]);
            String finalValue = values[i];
            query = query.append(userFilterType)
                    .append(operator1.getValue())
                    .append("'").append(finalValue)
                    .append("'").append(" and ");
        }

        logger.info("query is : " + query.substring(0, query.length() - 4));
        return userRepository.findUsersByNativeQuery(query.substring(0, query.length() - 4).toString());
    }

    public User getStudentByPhoneNo(String userPhoneNo) {
        return userRepository.findByPhoneNoAndUserType(userPhoneNo, UserType.STUDENT);  //by default only student can get book
    }
}

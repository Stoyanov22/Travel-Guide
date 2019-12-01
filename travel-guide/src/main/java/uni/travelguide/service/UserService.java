package uni.travelguide.service;

import uni.travelguide.model.User;

public interface UserService {

    User findUserByEmail(String email);

    void saveUser(User user);
}

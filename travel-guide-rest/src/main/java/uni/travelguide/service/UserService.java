package uni.travelguide.service;

import uni.travelguide.model.User;

public interface UserService {

    public User findUserByEmail(String email);

    public User findUserById(long id);

    public void saveUser(User user);
}
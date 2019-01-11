package fantasticcouscous.users.controller;

import fantasticcouscous.users.model.UserData;
import fantasticcouscous.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/user/{login}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public UserData getUserInfo(@PathVariable("login") String login) {
        return userRepository.getUserDatabyLogin(login);
    }

}

package fantasticcouscous.users.repository;

import fantasticcouscous.users.model.UserData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<UserData, String> { // Was a class when the implementation was done "by hand"
/* If we remove @Component, the service_info endpoint still works but not the user endpoint.
*  Oddly enough, the only test that fails is HttpRequestTest/userServiceShouldReturnUser
*  UserControllerTest/shouldReturnUserData passes -> actually it makes sense because we mock the repository in this one
* */

    List<UserData> findByFirstName(String firstName);

    UserData findOneByLogin(String login);

}

// https://spring.io/guides/gs/accessing-data-jpa/
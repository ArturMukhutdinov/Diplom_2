import io.restassured.response.Response;
import model.AuthResponseModel;
import model.UserRequestModel;
import org.junit.Test;
import util.BaseTest;

import static org.junit.Assert.assertEquals;

public class UserCreationTest extends BaseTest {

    @Test
    public void createUniqueUserTest() {
        UserRequestModel artur = new UserRequestModel("mukh" + getUniqueId() + "@mail.ru", "1234", "Artur");

        postNewUser(artur);
    }

    @Test
    public void createAlreadyExistingUserTest() {
        String uniqueId = getUniqueId();
        UserRequestModel artur = new UserRequestModel("mukh" + uniqueId + "@mail.ru", "1234", "Artur");
        usersToDelete.add(artur);

        Response response = createUser(artur);

        assertEquals("User should have been created!",
                200,
                response.getStatusCode());

        Response failedResponse = createUser(artur);

        assertEquals("Should have received status code 403 because user already exists!",
                403,
                failedResponse.getStatusCode());
    }

    @Test
    public void createUniqueUser1WithoutRequiredFieldTest() {
        UserRequestModel user1 = new UserRequestModel("mukh" + getUniqueId() + "@mail.ru", "1234", null);

        Response failedResponse = createUser(user1);
        assertEquals("Should have received status code 403 because there are missing required fields!",
                403,
                failedResponse.getStatusCode());
    }

    @Test
    public void createUniqueUser2WithoutRequiredFieldTest() {
        UserRequestModel user2 = new UserRequestModel("mukh" + getUniqueId() + "@mail.ru", null, "Artur");

        Response failedResponse = createUser(user2);
        assertEquals("Should have received status code 403 because there are missing required fields!",
                403,
                failedResponse.getStatusCode());
    }

    @Test
    public void createUniqueUser3WithoutRequiredFieldTest() {
      UserRequestModel user3 = new UserRequestModel(null, "1234", "Artur");

        Response failedResponse = createUser(user3);
        assertEquals("Should have received status code 403 because there are missing required fields!",
                403,
                failedResponse.getStatusCode());
    }
}
package dsredaTZ;

import ApiClass.SpacexMembers;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Tag("API")
public class ApiTests {

    @BeforeAll
    public static void setBaseUrl() {
        RestAssured.baseURI = "https://api.spacexdata.com/v4";
    }

    @Test
    @DisplayName("Проверка информации о компании SpaceX")
    public void checkCompanyInformation() {
        given().get("/company")
                .then().log().body()
                .body("name", equalTo("SpaceX"))
                .body("ceo", equalTo("Elon Musk"))
                .body("headquarters.address", equalTo("Rocket Road"))
                .body("headquarters.city", equalTo("Hawthorne"))
                .body("headquarters.state", equalTo("California"))
                .body("links.website", equalTo("https://www.spacex.com/"));
    }

    @Test
    @DisplayName("Проверить что имя третьего сотрудника Shannon Walker")
    public void thirdMemberIsShannon() {
        ArrayList members = given().get("/crew")
                .then()
                .extract().body().as(ArrayList.class);

        LinkedHashMap thirdMember = (LinkedHashMap) members.get(2);
        String id = (String) thirdMember.get("id");

        Response response = given().get("/crew/" + id)
                .then().log().body()
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        String name = jsonPath.getString("name");

        Assertions.assertEquals("Shannon Walker", name);
    }

    @Test
    @DisplayName("Проверить есть ли запуски у сотрудника Michael S. Hopkins")
    public void lastMemberHasNoLaunches() {
        List<SpacexMembers> members = given().get("/crew")
                .then()
                .extract().body().jsonPath().getList("", SpacexMembers.class);

        String HopkinsMemberId = members.get(5).getId();

        SpacexMembers HopkinsMember = given().get("/crew/" + HopkinsMemberId)
                .then().log().body()
                .extract().as(SpacexMembers.class);

        Assertions.assertTrue(HopkinsMember.getLaunches().size() == 1);
        String hopkinsLaunches = HopkinsMember.getLaunches().toString();
        Assertions.assertEquals("[5eb87d4dffd86e000604b38e]", hopkinsLaunches);
    }

}

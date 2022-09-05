package dsredaTZ;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

@Tag("UI")
public class UiTests {

    @BeforeAll
    public static void setUp(){
        Configuration.headless = false;
        Configuration.browser = "Chrome";
        Configuration.browserSize = "1800x900";
    }


    @Test
    @DisplayName("Проверка отправки электронного обращения в электронной приемной Администрации г.Магнитогорска")
    void checkAppealSending() {

        step("Открываем сайт Администрации г.Магнитогорска в разделе 'Интернет приемная'", () -> {
            open("https://www.magnitogorsk.ru/component/internet-reception");
        });

        step("Выбираем тип обращения 'Оформить обращение(для физических лиц)'", () -> {
            $(By.xpath("//a[text()='Оформить обращение (для физических лиц)']")).click();
        });

        step("Устанавливаем галочку в чек боксе Я хочу получить ответ: 'В письменом виде' ", () -> {
            $$(By.xpath("//ins[@class='iCheck-helper']")).get(1).click();
        });

        step("Заполняем поле 'Фамилия*'", () -> {
            $(By.xpath("//input[@name='surname']")).setValue("Петров");
        });

        step("Заполняем поле 'Имя*'", () -> {
            $(By.xpath("//input[@id='receptionName']")).setValue("Петр");
        });

        step("Заполняем поле 'Отчество'", () -> {
            $(By.xpath("//input[@name='patronymic']")).setValue("Петрович");
        });

        step("Выбираем социальное положение 'Безработный'", () -> {
            $(By.xpath("//span[@aria-labelledby='select2-receptionSocialStatus-container']")).scrollTo().click();
            $(By.xpath("//input[@class='select2-search__field']")).setValue("Безработный").pressEnter();
        });

        step("Заполняем поле 'Страна'", () -> {
            $(By.xpath("//input[@name='country']")).setValue("Россия");
        });

        step("Заполняем поле 'Индекс*'", () -> {
            $(By.xpath("//input[@name='post_index']")).setValue("455000");
        });

        step("Заполняем поле 'Город, район, поселок*'", () -> {
            $(By.xpath("//input[@name='city']")).setValue("Магнитогорск");
        });

        step("Заполняем поле 'Улица*'", () -> {
            $(By.xpath("//input[@name='street']")).setValue("Пушкина");
        });

        step("Заполняем поле 'Дом/Корпус*'", () -> {
            $(By.xpath("//input[@name='house']")).setValue("21");
        });

        step("Заполняем поле 'Квартира*'", () -> {
            $(By.xpath("//input[@name='apartment']")).setValue("77");
        });

        step("Заполняем поле 'Email*'", () -> {
            $(By.xpath("//input[@name='email']")).setValue("test@email.ru");
        });

        step("Заполняем поле 'Телефон'", () -> {
            $(By.xpath("//input[@name='phone']")).setValue("89090990909");
        });

        step("Заполняем поле 'Текст обращения'", () -> {
            $(By.xpath("//textarea[@name='text']")).scrollTo().setValue("Хочу поблагодарить мэра города за прекрасную работу!.");
        });

        step("Прикрепить файл-вложение", () -> {
            $(By.xpath("//input[@name='files[]']")).uploadFromClasspath("files/upload.txt");
        });

        step("Установить флаг в чекбоксе 'Я согласен с условиями обработки персональных данных'", () -> {
            $$(By.xpath("//ins[@class='iCheck-helper']")).get(3).click();
        });

        step("Нажать кнопку 'Отправить сообщение'", () -> {
            $(By.xpath("//button[@class='__ps _button btn btn--blue']")).click();
        });

        step("Убедиться что появилось сообщение об успешной отправке обращения", () -> {
            $(By.xpath("//div[@class='swal-text']")).shouldBe(visible).shouldHave(text("Ваше обращение успешно отправлено"));
        });
    }

    @Test
    @DisplayName("Проверка xls файла формы аккредитации журналистов")
    void checkAccreditationFile() {

        step("Открываем сайт Администрации г.Магнитогорска", () -> {
            open("https://www.magnitogorsk.ru/");
        });

        step("Нажимаем на знак поиска", () -> {
            $(By.xpath("//a[@class='top-menu__link ps-search-link']")).click();
        });

        step("В поиске вводим слово 'Журналисты'", () -> {
            $(By.xpath("//input[@class='swal-content__input']")).setValue("Журналисты");
        });

        step("Нажимаем кнопку 'Поиск'", () -> {
            $(By.xpath("//button[@class='swal-button swal-button--confirm']")).click();
        });

        step("Открываем статью 'Журналисты, готовимся к этапу Кубка мира по сноуборду в Магнитогорске'", () -> {
            $(By.xpath("//a[@href='https://www.magnitogorsk.ru/news/zhurnalisty-gotovimsya-k-etapu-kubka-mira-po-snoubordu-v-magnitogorske']")).click();
        });

    }


}

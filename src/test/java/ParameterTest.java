
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;


import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.*;


public class ParameterTest {

    @Tag("Critical")
    @ValueSource(strings = {"понедельник", "вторник"})
    @DisplayName("Поиск в википедии")
    @ParameterizedTest(name = "Поиск в википедии {0}")
    void daysTest(String dayQuery){
        open("https://ru.wikipedia.org/w/index.php?search=");
        $("input[type='search']").setValue(dayQuery).pressEnter();
        $x("//li[@class=\"mw-search-result\"]//*[.='"+dayQuery+"']")
                .shouldBe(Condition.visible);
    }
    @Tag("Critical")
    @CsvSource(value = {
            "понедельник | — день недели между воскресеньем и вторником. Слово «",
            "вторник | является третьим. Слово «"
    },
    delimiter = '|')
    @DisplayName("Поиск в википедии")
    @ParameterizedTest(name = "Поиск в википедии {0}")
    void descriptionTest(String dayQuery, String expectedResult){
        open("https://ru.wikipedia.org/w/index.php?search=");
        $("input[type='search']").setValue(dayQuery).pressEnter();
        $(byText(expectedResult)).shouldBe(Condition.visible);
    }

    @Tag("Critical")
    @DisplayName("Поиск в википедии")
    static Stream<Arguments> StreamDescriptionTest(){
        return Stream.of(
                Arguments.of("понедельник", List.of("— день недели между воскресеньем и вторником. Слово «")),
                Arguments.of("вторник",List.of("является третьим. Слово «") )
        );
    }
    @MethodSource()
    @ParameterizedTest(name = "Поиск в википедии")
    void StreamDescriptionTest(String dayQuery, List<String> expectedResult){
        open("https://ru.wikipedia.org/w/index.php?search=");
        $("input[type='search']").setValue(dayQuery).pressEnter();
        $(byText(expectedResult.get(0))).shouldBe(Condition.visible);
    }

    @Tag("Critical")
    @EnumSource(value = SearchQuery.class, names = {"Понедельник", "Вторник"})
    @DisplayName("Поиск в википедии")
    @ParameterizedTest(name = "Поиск в википедии")
    void EnumTest(SearchQuery dayQuery){
        open("https://ru.wikipedia.org/w/index.php?search=");
        $("input[type='search']").setValue(dayQuery.name()).pressEnter();
        $x("//li[@class=\"mw-search-result\"]//*[.='"+dayQuery.name()+"']")
                .shouldBe(Condition.visible);
    }




}

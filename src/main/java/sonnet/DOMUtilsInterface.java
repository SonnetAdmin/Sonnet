package sonnet;

//import sonnet.reference.playwright.Enums;

import java.util.List;

public interface DOMUtilsInterface {
    CommonPageObject urlEquals(String url);

    CommonPageObject urlContains(String url);

    CommonPageObject urlStartsWith(String url);

    CommonPageObject urlEndsWith(String url);

    CommonPageObject titleEquals(String title);

    CommonPageObject titleContains(String title);

    CommonPageObject titleStartsWith(String title);

    CommonPageObject titleEndsWith(String title);

    CommonPageObject textEquals(String text);

    CommonPageObject textDiffers(String text);

    CommonPageObject textContains(String text);

    CommonPageObject textStartsWith(String text);

    CommonPageObject textEndsWith(String text);

    CommonPageObject textPresent();

    CommonPageObject textNotPresent();

    CommonPageObject textHasLength(int length);

//    CommonPageObject attributeEquals(Enums.ElementTrait attribute, String value);
//
//    CommonPageObject attributeContains(Enums.ElementTrait attribute, String value);
//
//    CommonPageObject attributeStartsWith(Enums.ElementTrait attribute, String value);
//
//    CommonPageObject attributeEndsWith(Enums.ElementTrait attribute, String value);

//    CommonPageObject elementPresent(Target target);
//
//    CommonPageObject elementNotPresent(Target target);

    CommonPageObject assemblyPresent();

    CommonPageObject assemblyNotPresent();

    CommonPageObject assemblySize(int size);

    int assembled();

    CommonPageObject assemblyLargerThan(int size);

    CommonPageObject assemblySmallerThan(int size);

    CommonPageObject assemblyIs(List<String> expected);

    CommonPageObject assemblyIs(List<String> expected, boolean strict);

    CommonPageObject assemblyContains(String item);

    CommonPageObject assemblyContains(List<String> items);

    CommonPageObject assemblyDoesNotContain(String item);

    CommonPageObject assemblyDoesNotContain(List<String> items);

//    CommonPageObject tagIs(Enums.Tag tag);
//
//    CommonPageObject tagIsNot(Enums.Tag tag);

    CommonPageObject elementPrint();

    CommonPageObject assemblyPrint();
}

package sonnet;

import sonnet.reference.playwright.Enums;

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

    CommonPageObject textPresent(String text);

    CommonPageObject textNotPresent();

    CommonPageObject textNotPresent(String text);

    CommonPageObject textHasLength(int length);

    CommonPageObject attributeEquals(Enums.ElementTrait attribute, String value);

    CommonPageObject attributeContains(Enums.ElementTrait attribute, String value);

    CommonPageObject attributeStartsWith(Enums.ElementTrait attribute, String value);

    CommonPageObject attributeEndsWith(Enums.ElementTrait attribute, String value);

    CommonPageObject elementPresent(Target target);

    CommonPageObject elementNotPresent(Target target);

    CommonPageObject collectionPresent();

    CommonPageObject collectionNotPresent();

    CommonPageObject collectionHasSize(int size);

    int size();

    CommonPageObject collectionLargerThan(int size);

    CommonPageObject collectionSmallerThan(int size);

    CommonPageObject collectionIs(List<String> expected);

    CommonPageObject collectionIs(List<String> expected, boolean strict);

    CommonPageObject collectionContains(String item);

    CommonPageObject collectionContains(List<String> items);

    CommonPageObject collectionDoesNotContain(String item);

    CommonPageObject collectionDoesNotContain(List<String> items);

    CommonPageObject tagIs(Enums.Tag tag);

    CommonPageObject tagIsNot(Enums.Tag tag);

    CommonPageObject elementPrint();

    CommonPageObject collectionPrint();
}

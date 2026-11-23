package sonnet;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommonPageObject extends TargetFactory implements DOMUtilsInterface {
    CommonPage commonPage;

    public CommonPageObject() {
        commonPage = CommonPage.getInstance();
    }

    public CommonPageObject ascend() {
        commonPage.ascend();
        return this;
    }

    public CommonPageObject ascend(int level) {
        commonPage.ascend(level);
        return this;
    }

    public CommonPageObject ascend(Enum.Index level) {
        commonPage.ascend(level);
        return this;
    }

    public CommonPageObject ascend(Enum.HTMLTag tag) {
        commonPage.ascend(tag);
        return this;
    }

    public CommonPageObject ascend(Target target) {
        commonPage.ascend(target);
        return this;
    }

    public CommonPageObject box(Target target) {
        commonPage.box(target);
        return this;
    }

    public CommonPageObject box() {
        commonPage.box();
        return this;
    }

    public CommonPageObject unbox() {
        commonPage.unbox();
        return this;
    }


    public CommonPageObject click() {
        commonPage.click();
        return this;
    }

    public CommonPageObject choose(int index) {
        commonPage.choose(index);
        return this;
    }

    public CommonPageObject choose(Enum.Index index) {
        commonPage.choose(index);
        return this;
    }

    public CommonPageObject choose(Enum.ListIndex index) {
        commonPage.choose(index);
        return this;
    }

    public CommonPageObject choose(String name) {
        commonPage.choose(name);
        commonPage.printElement();
        return this;
    }

    public CommonPageObject cognate(Target destination) {
        commonPage.cognate(destination);
        return this;
    }

    public CommonPageObject cognate(Target origin, Target destination) {
        commonPage.cognate(origin, destination);
        return this;
    }

    public CommonPageObject assemble(Target target) {
        commonPage.assemble(target);
        return this;
    }

    public CommonPageObject compose(Enum.Action action) {
        commonPage.compose(action);
        return this;
    }

    public CommonPageObject compose(String text) {
        commonPage.compose(text);
        return this;
    }

    public CommonPageObject check() {
        commonPage.check();
        return this;
    }

    public CommonPageObject descend() {
        commonPage.descend();
        return this;
    }

    public CommonPageObject descend(int level) {
        commonPage.descend(level);
        return this;
    }

    public CommonPageObject descend(Enum.Index level) {
        commonPage.descend(level);
        return this;
    }


    public CommonPageObject descend(Enum.HTMLTag tag) {
        commonPage.descend(tag);
        return this;
    }

    public CommonPageObject descend(Target target) {
        commonPage.descend(target);
        return this;
    }


    public CommonPageObject uncheck() {
        commonPage.uncheck();
        return this;
    }

    public CommonPageObject checked() {
        commonPage.checked();
        return this;
    }

    public CommonPageObject unchecked() {
        commonPage.unchecked();
        return this;
    }

    public CommonPageObject end() {
        commonPage.end();
        return this;
    }

    public CommonPageObject focus(Target target) {
        commonPage.focus(target);
        return this;
    }

    public String get(Enum.Trait trait) {
        return commonPage.get(trait);
    }


    public CommonPageObject go(String url) {
        commonPage.go(url);
        return this;
    }

    public CommonPageObject go(Enum.Direction direction) {
        commonPage.go(direction);
        return this;
    }

    public CommonPageObject open() {
        commonPage.open();
        return this;
    }

    public CommonPageObject perform(Performable runner) {
        commonPage.perform(runner);
        return this;
    }

    public CommonPageObject printElement() {
        commonPage.printElement();
        return this;
    }

    public CommonPageObject printElements() {
        commonPage.printElements();
        return this;
    }

    public CommonPageObject probe(Target target) {
        commonPage.probe(target);
        return this;
    }

    public CommonPageObject submit() {
        commonPage.submit();
        return this;
    }

    public CommonPageObject log(String message) {
        commonPage.log(message);
        return this;
    }

    public CommonPageObject reverse(int index){
        commonPage.reverse(index);
        return this;
    }

    public CommonPageObject reverse(Enum.NodeEnum index){
        commonPage.reverse(index);
        return this;
    }

    public CommonPageObject store(String key, String value) {
        commonPage.store(key, value);
        return this;
    }

    public CommonPageObject scroll(){
        commonPage.scroll();
        return this;
    }
    public CommonPageObject scroll(Target target){
        commonPage.scroll(target);
        return this;
    }
    public CommonPageObject scroll(Enum.Scroll direction){
        commonPage.scroll(direction);
        return this;
    }

    public CommonPageObject traverse(int index){
        commonPage.traverse(index);
        return this;
    }

    public CommonPageObject traverse(Enum.NodeEnum index){
        commonPage.traverse(index);
        return this;
    }



    public String retrieve(String key) {
        return commonPage.retrieve(key);
    }

    ///////////////
    // DOM UTILS //
    ///////////////

    @Override
    public CommonPageObject urlEquals(String expectedUrl) {
        String actualUrl = commonPage.get(Enum.Trait.URL);
        assertEquals(expectedUrl, actualUrl);
        return this;
    }

    @Override
    public CommonPageObject urlContains(String expectedUrl) {
        String actualUrl = commonPage.get(Enum.Trait.URL);
        assertTrue(actualUrl.contains(expectedUrl), "actualUrl : " + actualUrl + " - expectedUrl : " + expectedUrl);
        return this;
    }

    @Override
    public CommonPageObject urlStartsWith(String expectedUrl) {
        String actualUrl = commonPage.get(Enum.Trait.URL);
        assertTrue(actualUrl.startsWith(expectedUrl), "actualUrl : " + actualUrl + " - expectedUrl : " + expectedUrl);
        return this;
    }

    @Override
    public CommonPageObject urlEndsWith(String expectedUrl) {
        String actualUrl = commonPage.get(Enum.Trait.URL);
        assertTrue(actualUrl.endsWith(expectedUrl), "actualUrl : " + actualUrl + " - expectedUrl : " + expectedUrl);
        return this;
    }

    @Override
    public CommonPageObject titleEquals(String expectedTitle) {
        String actualTitle = commonPage.get(Enum.Trait.TITLE);
        assertEquals(expectedTitle, actualTitle);
        return this;
    }

    @Override
    public CommonPageObject titleContains(String expectedTitle) {
        String actualTitle = commonPage.get(Enum.Trait.TITLE);
        assertTrue(actualTitle.contains(expectedTitle), "actualTitle : " + actualTitle + " - expectedTitle : " + expectedTitle);
        return this;
    }

    @Override
    public CommonPageObject titleStartsWith(String expectedTitle) {
        String actualTitle = commonPage.get(Enum.Trait.TITLE);
        assertTrue(actualTitle.startsWith(expectedTitle), "actualTitle : " + actualTitle + " - expectedTitle : " + expectedTitle);
        return this;
    }

    @Override
    public CommonPageObject titleEndsWith(String expectedTitle) {
        String actualTitle = commonPage.get(Enum.Trait.TITLE);
        assertTrue(actualTitle.endsWith(expectedTitle), "actualTitle : " + actualTitle + " - expectedTitle : " + expectedTitle);
        return this;
    }

    @Override
    public CommonPageObject textEquals(String expectedText) {
        String actualText = commonPage.get(Enum.Trait.TEXT);
        assertEquals(expectedText, actualText);
        return this;
    }

    @Override
    public CommonPageObject textDiffers(String expectedText) {
        String actualText = commonPage.get(Enum.Trait.TEXT);
        assertNotEquals(expectedText, actualText);
        return this;
    }

    @Override
    public CommonPageObject textContains(String expectedText) {
        String actualText = commonPage.get(Enum.Trait.TEXT);
        assertTrue(actualText.contains(expectedText), "actualText : " + actualText + " - expectedText : " + expectedText);
        return this;
    }

    @Override
    public CommonPageObject textStartsWith(String expectedText) {
        String actualText = commonPage.get(Enum.Trait.TEXT);
        assertTrue(actualText.startsWith(expectedText), "actualText : " + actualText + " - expectedText : " + expectedText);
        return this;
    }

    @Override
    public CommonPageObject textEndsWith(String expectedText) {
        String actualText = commonPage.get(Enum.Trait.TEXT);
        assertTrue(actualText.endsWith(expectedText), "actualText : " + actualText + " - expectedText : " + expectedText);
        return this;
    }

    @Override
    public CommonPageObject textPresent() {
        String actualText = commonPage.get(Enum.Trait.TEXT);
        assertFalse(actualText.isEmpty());
        return this;
    }

    @Override
    public CommonPageObject textNotPresent() {
        String actualText = commonPage.get(Enum.Trait.TEXT);
        assertTrue(actualText.isEmpty());
        return this;
    }

    @Override
    public CommonPageObject textHasLength(int expectedLength) {
        int actualLength = commonPage.get(Enum.Trait.TEXT).length();
        assertEquals(actualLength, expectedLength, "actualLength : " + actualLength + " - expectedLength : " + expectedLength);
        return this;
    }

//    @Override
//    public CommonPageObject attributeEquals(Enums.ElementTrait attribute, String value) {
//        return this;
//    }
//
//    @Override
//    public CommonPageObject attributeContains(Enums.ElementTrait attribute, String value) {
//        return this;
//    }
//
//    @Override
//    public CommonPageObject attributeStartsWith(Enums.ElementTrait attribute, String value) {
//        return this;
//    }
//
//    @Override
//    public CommonPageObject attributeEndsWith(Enums.ElementTrait attribute, String value) {
//        return this;
//    }

//    @Override
//    public CommonPageObject elementPresent(Target target) {
//        return this;
//    }                                                                             //REPLACED BY PEEK
//
//    @Override
//    public CommonPageObject elementNotPresent(Target target) {
//        return this;
//    }

    @Override
    public CommonPageObject assemblyPresent() {

        return this;
    }

    @Override
    public CommonPageObject assemblyNotPresent() {
        return this;
    }

    @Override
    public CommonPageObject assemblySize(int expectedSize) {
        int actualSize = commonPage.assembled();
        assertEquals(actualSize, expectedSize, "actualSize : " + actualSize + " - expectedSize : " + expectedSize);
        return this;
    }

    public int assembled() {
        return commonPage.assembled();
    }

    @Override
    public CommonPageObject assemblyLargerThan(int size) {
        return this;
    }

    @Override
    public CommonPageObject assemblySmallerThan(int size) {
        return this;
    }

    @Override
    public CommonPageObject assemblyIs(List<String> expected) {
        return this;
    }

    @Override
    public CommonPageObject assemblyIs(List<String> expected, boolean strict) {
        return this;
    }

    @Override
    public CommonPageObject assemblyContains(String item) {
        return this;
    }

    @Override
    public CommonPageObject assemblyContains(List<String> items) {
        return this;
    }

    @Override
    public CommonPageObject assemblyDoesNotContain(String item) {
        return this;
    }

    @Override
    public CommonPageObject assemblyDoesNotContain(List<String> items) {
        return this;
    }

//    @Override
//    public CommonPageObject tagIs(Enums.Tag tag) {
//        return this;
//    }
//
//    @Override
//    public CommonPageObject tagIsNot(Enums.Tag tag) {
//        return this;
//    }

    @Override
    public CommonPageObject elementPrint() {
        return this;
    }

    @Override
    public CommonPageObject assemblyPrint() {
        return this;
    }

}
package sonnet;

import sonnet.reference.playwright.Enums;

import java.util.List;

public class CommonPageObject extends TargetFactory implements DOMUtilsInterface {
    CommonPage commonPage;

    public CommonPageObject() {
        commonPage = CommonPage.getInstance();
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

    public CommonPageObject collect(Target target) {
        commonPage.collect(target);
        commonPage.printElements();
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

    public CommonPageObject store(String key, String value) {
        commonPage.store(key, value);
        return this;
    }

    public String retrieve(String key) {
        return commonPage.retrieve(key);
    }

    ///////////////
    // DOM UTILS //
    ///////////////

    @Override
    public CommonPageObject urlEquals(String url) {
        return null;
    }

    @Override
    public CommonPageObject urlContains(String url) {
        return null;
    }

    @Override
    public CommonPageObject urlStartsWith(String url) {
        return null;
    }

    @Override
    public CommonPageObject urlEndsWith(String url) {
        return null;
    }

    @Override
    public CommonPageObject titleEquals(String title) {
        return null;
    }

    @Override
    public CommonPageObject titleContains(String title) {
        return null;
    }

    @Override
    public CommonPageObject titleStartsWith(String title) {
        return null;
    }

    @Override
    public CommonPageObject titleEndsWith(String title) {
        return null;
    }

    @Override
    public CommonPageObject textEquals(String text) {
        return null;
    }

    @Override
    public CommonPageObject textDiffers(String text) {
        return null;
    }

    @Override
    public CommonPageObject textContains(String text) {
        return null;
    }

    @Override
    public CommonPageObject textStartsWith(String text) {
        return null;
    }

    @Override
    public CommonPageObject textEndsWith(String text) {
        return null;
    }

    @Override
    public CommonPageObject textPresent() {
        return null;
    }

    @Override
    public CommonPageObject textPresent(String text) {
        return null;
    }

    @Override
    public CommonPageObject textNotPresent() {
        return null;
    }

    @Override
    public CommonPageObject textNotPresent(String text) {
        return null;
    }

    @Override
    public CommonPageObject textHasLength(int length) {
        return null;
    }

    @Override
    public CommonPageObject attributeEquals(Enums.ElementTrait attribute, String value) {
        return null;
    }

    @Override
    public CommonPageObject attributeContains(Enums.ElementTrait attribute, String value) {
        return null;
    }

    @Override
    public CommonPageObject attributeStartsWith(Enums.ElementTrait attribute, String value) {
        return null;
    }

    @Override
    public CommonPageObject attributeEndsWith(Enums.ElementTrait attribute, String value) {
        return null;
    }

    @Override
    public CommonPageObject elementPresent(Target target) {
        return null;
    }

    @Override
    public CommonPageObject elementNotPresent(Target target) {
        return null;
    }

    @Override
    public CommonPageObject collectionPresent() {
        return null;
    }

    @Override
    public CommonPageObject collectionNotPresent() {
        return null;
    }

    @Override
    public CommonPageObject collectionHasSize(int size) {
        return null;
    }

    public int size() {
        return commonPage.size();
    }

    @Override
    public CommonPageObject collectionLargerThan(int size) {
        return null;
    }

    @Override
    public CommonPageObject collectionSmallerThan(int size) {
        return null;
    }

    @Override
    public CommonPageObject collectionIs(List<String> expected) {
        return null;
    }

    @Override
    public CommonPageObject collectionIs(List<String> expected, boolean strict) {
        return null;
    }

    @Override
    public CommonPageObject collectionContains(String item) {
        return null;
    }

    @Override
    public CommonPageObject collectionContains(List<String> items) {
        return null;
    }

    @Override
    public CommonPageObject collectionDoesNotContain(String item) {
        return null;
    }

    @Override
    public CommonPageObject collectionDoesNotContain(List<String> items) {
        return null;
    }

    @Override
    public CommonPageObject tagIs(Enums.Tag tag) {
        return null;
    }

    @Override
    public CommonPageObject tagIsNot(Enums.Tag tag) {
        return null;
    }

    @Override
    public CommonPageObject elementPrint() {
        return null;
    }

    @Override
    public CommonPageObject collectionPrint() {
        return null;
    }

}
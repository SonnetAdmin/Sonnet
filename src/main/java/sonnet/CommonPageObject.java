package sonnet;

public class CommonPageObject extends TargetFactory {
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

    public CommonPageObject choose(String name) {
        commonPage.choose(name);
        log("lines --------------");
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

    public CommonPageObject collect(String locator) {
        commonPage.collect(locator);
        return this;
    }

    public CommonPageObject collect(Target target) {
        commonPage.collect(target);
        commonPage.printElements();
        return this;
    }

    public CommonPageObject compose(CommonPageInterface.Action action) {
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

    public String get(CommonPageInterface.Trait trait) {
        return commonPage.get(trait);
    }

    public CommonPageObject go(String url) {
        commonPage.go(url);
        return this;
    }

    public CommonPageObject open() {
        commonPage.open();
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

    public int size() {
        return commonPage.size();
    }

    public CommonPageObject submit() {
        commonPage.submit();
        return this;
    }

    public CommonPageObject log(String message) {
        commonPage.log(message);
        return this;
    }
}
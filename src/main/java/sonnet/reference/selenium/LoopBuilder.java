package sonnet.reference.selenium;


import org.openqa.selenium.Keys;
import sonnet.reference.selenium.locators.Target;
import sonnet.reference.selenium.utils.reflection.ArgumentResolutionException;
import sonnet.reference.selenium.utils.reflection.MethodCall;
import sonnet.reference.selenium.utils.reflection.MethodCallWithSupplierArgs;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LoopBuilder {

    private final Type type;
    private final CommonPage commonPage;
    private final CommonPageObject commonPageObject;
    private final int times;
    private final List<MethodCall> loopMethodCalls;

    private enum Type {
        COLLECTION,
        REPEAT
    }

    public LoopBuilder(CommonPage commonPage, CommonPageObject commonPageObject) {
        this.type = Type.COLLECTION;
        this.commonPage = commonPage;
        this.commonPageObject = commonPageObject;
        this.times = 0;
        loopMethodCalls = new ArrayList<>();
    }

    public LoopBuilder(CommonPage commonPage, CommonPageObject commonPageObject, int times) {
        this.type = Type.REPEAT;
        this.commonPage = commonPage;
        this.commonPageObject = commonPageObject;
        this.times = times;
        loopMethodCalls = new ArrayList<>();
    }

    private void addMethodCall(String methodName) {
        loopMethodCalls.add(new MethodCall(methodName));
    }

    private void addMethodCall(String methodName, Object... args) {
        loopMethodCalls.add(new MethodCall(methodName, args));
    }

    private void addMethodCall(String methodName, Supplier<?>... args) {
        loopMethodCalls.add(new MethodCallWithSupplierArgs(methodName, args));
    }

    private void invokeMethods() {
        for (MethodCall mc : loopMethodCalls) {
            try {
                mc.invoke(commonPage);
            } catch (InvocationTargetException | IllegalAccessException e) {
                commonPage.takeScreenShotAndExit("Failed to invoke loop method: " + e.getMessage());
            } catch (NoSuchMethodException e) {
                commonPage.takeScreenShotAndExit("Failed to resolve loop method: " + e.getMessage());
            } catch (ArgumentResolutionException e) {
                commonPage.takeScreenShotAndExit("Failed to resolve arguments from supplier: " + e.getMessage());
            }
        }
    }

    public CommonPageObject endLoop() {
        if (Type.COLLECTION.equals(this.type)) {
            for (int i = 0; i < commonPage.size(); i++) {
                commonPage.choose(i);
                invokeMethods();
            }
        }

        if (Type.REPEAT.equals(this.type)) {
            for (int i = 0; i < times; i++) {
                invokeMethods();
            }
        }

        return commonPageObject;
    }

    public LoopBuilder origin() {
        addMethodCall("origin");
        return this;
    }

    public LoopBuilder click() {
        addMethodCall("click");
        return this;
    }

    public LoopBuilder click(int waitTime) {
        addMethodCall("click", waitTime);
        return this;
    }

    public LoopBuilder click(Supplier<Integer> waitTimeSupplier) {
        addMethodCall("click", waitTimeSupplier);
        return this;
    }

    public LoopBuilder clear() {
        addMethodCall("clear");
        return this;
    }

    public LoopBuilder compose(String keysToSend) {
        addMethodCall("compose", keysToSend);
        return this;
    }

    public LoopBuilder compose(Supplier<String> keysToSendSupplier) {
        addMethodCall("compose", keysToSendSupplier);
        return this;
    }

    public LoopBuilder compose(Keys keyToSend) {
        addMethodCall("compose", keyToSend);
        return this;
    }

    public LoopBuilder compose(Keys keyToSend, int repeat) {
        addMethodCall("compose", keyToSend, repeat);
        return this;
    }

    public LoopBuilder compose(Supplier<Keys> keyToSendSupplier, Supplier<Integer> repeatSupplier) {
        addMethodCall("compose", keyToSendSupplier, repeatSupplier);
        return this;
    }

    public LoopBuilder focus(Target target) {
        addMethodCall("focus", target);
        return this;
    }

    public LoopBuilder focus(Supplier<Target> targetSupplier) {
        addMethodCall("focus", targetSupplier);
        return this;
    }

    public LoopBuilder probe(Target target) {
        addMethodCall("probe", target);
        return this;
    }

    public LoopBuilder probe(Supplier<Target> targetSupplier) {
        addMethodCall("probe", targetSupplier);
        return this;
    }

    public LoopBuilder probe(Target target, Enums.ElementState elementState) {
        addMethodCall("probe", target, elementState);
        return this;
    }

    public LoopBuilder probe(Supplier<Target> targetSupplier, Supplier<Enums.ElementState> elementStateSupplier) {
        addMethodCall("probe", targetSupplier, elementStateSupplier);
        return this;
    }

    public LoopBuilder probe(int waitTime) {
        addMethodCall("probe", waitTime);
        return this;
    }

    public LoopBuilder scroll(Target target) {
        addMethodCall("scroll", target);
        return this;
    }

    public LoopBuilder scroll(Supplier<Target> targetSupplier) {
        addMethodCall("scroll", targetSupplier);
        return this;
    }

    public LoopBuilder descend() {
        addMethodCall("descend");
        return this;
    }

    public LoopBuilder descend(Target target) {
        addMethodCall("descend", target);
        return this;
    }

    public LoopBuilder descend(Supplier<Target> targetSupplier) {
        addMethodCall("descend", targetSupplier);
        return this;
    }

    public LoopBuilder ascend(int index) {
        addMethodCall("ascend", index);
        return this;
    }

    public LoopBuilder ascend(Supplier<Integer> indexSupplier) {
        addMethodCall("ascend", indexSupplier);
        return this;
    }

    public LoopBuilder ascend(Enums.Index index) {
        addMethodCall("ascend", index);
        return this;
    }

    public LoopBuilder traverse(int index) {
        addMethodCall("traverse", index);
        return this;
    }

    public LoopBuilder traverse(Supplier<Integer> indexSupplier) {
        addMethodCall("traverse", indexSupplier);
        return this;
    }

    public LoopBuilder traverse(Enums.NodeEnum index) {
        addMethodCall("traverse", index);
        return this;
    }

    public LoopBuilder reverse(int index) {
        addMethodCall("reverse", index);
        return this;
    }

    public LoopBuilder reverse(Supplier<Integer> indexSupplier) {
        addMethodCall("reverse", indexSupplier);
        return this;
    }

    public LoopBuilder reverse(Enums.NodeEnum index) {
        addMethodCall("reverse", index);
        return this;
    }

    public LoopBuilder matches(String text) {
        addMethodCall("matches", text);
        return this;
    }

    public LoopBuilder matches(Supplier<String> textSupplier) {
        addMethodCall("matches", textSupplier);
        return this;
    }

    public LoopBuilder contains(String text) {
        addMethodCall("contains", text);
        return this;
    }

    public LoopBuilder contains(Supplier<String> textSupplier) {
        addMethodCall("contains", textSupplier);
        return this;
    }

    public LoopBuilder store(String key, String value) {
        addMethodCall("store", key, value);
        return this;
    }

    public LoopBuilder store(Supplier<String> keySupplier, Supplier<String> valueSupplier) {
        addMethodCall("store", keySupplier, valueSupplier);
        return this;
    }

    public LoopBuilder retrieve(String key) {
        addMethodCall("retrieve", key);
        return this;
    }

    public LoopBuilder retrieve(Supplier<String> keySupplier) {
        addMethodCall("retrieve", keySupplier);
        return this;
    }

    public LoopBuilder storeFocused(String key) {
        addMethodCall("storeFocused", key);
        return this;
    }

    public LoopBuilder storeFocused(Supplier<String> keySupplier) {
        addMethodCall("storeFocused", keySupplier);
        return this;
    }

    public LoopBuilder retrieveFocused(String key) {
        addMethodCall("retrieveFocused", key);
        return this;
    }

    public LoopBuilder retrieveFocused(Supplier<String> keySupplier) {
        addMethodCall("retrieveFocused", keySupplier);
        return this;
    }

    public LoopBuilder depart(Target target) {
        addMethodCall("depart", target);
        return this;
    }

    public LoopBuilder depart(Supplier<Target> targetSupplier) {
        addMethodCall("depart", targetSupplier);
        return this;
    }

    public LoopBuilder depart(Target target, Enums.Targets targets) {
        addMethodCall("depart", target, targets);
        return this;
    }

    public LoopBuilder depart(List<Target> targets) {
        addMethodCall("depart", targets);
        return this;
    }

    public LoopBuilder absent(Target target) {
        addMethodCall("absent", target);
        return this;
    }

    public LoopBuilder absent(Supplier<Target> targetSupplier) {
        addMethodCall("absent", targetSupplier);
        return this;
    }

    public LoopBuilder hover(Target target) {
        addMethodCall("hover", target);
        return this;
    }

    public LoopBuilder hover(Supplier<Target> targetSupplier) {
        addMethodCall("hover", targetSupplier);
        return this;
    }

    public LoopBuilder dragDrop(Target draggable, Target dropZone) {
        addMethodCall("dragDrop", draggable, dropZone);
        return this;
    }

    public LoopBuilder dragDrop(Supplier<Target> draggableSupplier, Supplier<Target> dropZoneSupplier) {
        addMethodCall("dragDrop", draggableSupplier, dropZoneSupplier);
        return this;
    }

    public LoopBuilder drop(Target dropZone) {
        addMethodCall("drop", dropZone);
        return this;
    }

    public LoopBuilder drop(Supplier<Target> dropZoneSupplier) {
        addMethodCall("drop", dropZoneSupplier);
        return this;
    }

    public LoopBuilder drag(Target draggable) {
        addMethodCall("drag", draggable);
        return this;
    }

    public LoopBuilder drag(Supplier<Target> draggableSupplier) {
        addMethodCall("drag", draggableSupplier);
        return this;
    }

    public LoopBuilder position(int x, int y) {
        addMethodCall("position", x, y);
        return this;
    }

    public LoopBuilder position(Supplier<Integer> xSupplier, Supplier<Integer> ySupplier) {
        addMethodCall("position", xSupplier, ySupplier);
        return this;
    }

    public LoopBuilder file(String filePath) {
        addMethodCall("file", filePath);
        return this;
    }

    public LoopBuilder file(Supplier<String> filePathSupplier) {
        addMethodCall("file", filePathSupplier);
        return this;
    }

    public LoopBuilder file(Target target, String filePath) {
        addMethodCall("file", target, filePath);
        return this;
    }

    public LoopBuilder file(Supplier<Target> targetSupplier, Supplier<String> filePathSupplier) {
        addMethodCall("file", targetSupplier, filePathSupplier);
        return this;
    }

    public LoopBuilder alert(Target.AlertAction action) {
        addMethodCall("alert", action);
        return this;
    }

    public LoopBuilder alert(int keys) {
        addMethodCall("alert", keys);
        return this;
    }

    public LoopBuilder alert(Supplier<Integer> keysSupplier) {
        addMethodCall("alert", keysSupplier);
        return this;
    }

    public LoopBuilder refresh() {
        addMethodCall("refresh");
        return this;
    }

    public LoopBuilder printFocused() {
        addMethodCall("printFocused");
        return this;
    }

    public LoopBuilder printCollection() {
        addMethodCall("printCollection");
        return this;
    }

    public LoopBuilder printElement() {
        addMethodCall("printElement");
        return this;
    }

    public LoopBuilder selected() {
        addMethodCall("selected");
        return this;
    }

    public LoopBuilder enabled() {
        addMethodCall("enabled");
        return this;
    }

    public LoopBuilder disabled() {
        addMethodCall("disabled");
        return this;
    }

    public LoopBuilder clickable() {
        addMethodCall("clickable");
        return this;
    }

    public LoopBuilder visible() {
        addMethodCall("visible");
        return this;
    }
}

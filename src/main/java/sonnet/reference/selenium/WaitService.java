package sonnet.reference.selenium;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.time.Duration;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

@AllArgsConstructor
@RequiredArgsConstructor
@Setter
class WaitService {

    private final WebDriver driver;

    private Integer pollingIntervalMillis = 500;
    private Integer numberOfConsistentValuesRequired = 3;

    public <T, G> void waitForStableValue(T object, Function<T, G> condition, int timeoutSeconds) {
        waitForStableValue(object, condition, List.of(), timeoutSeconds);
    }

    public <T, G> void waitForStableValue(T object, Function<T, G> condition, Class<? extends Throwable> ignoredException, int timeoutSeconds) {
        waitForStableValue(object, condition, List.of(ignoredException), timeoutSeconds);
    }

    public <T, G> void waitForStableValue(T object, Function<T, G> condition, Collection<Class<? extends Throwable>> ignoredExceptions, int timeoutSeconds) {
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutSeconds))
                .pollingEvery(Duration.ofMillis(pollingIntervalMillis))
                .ignoreAll(ignoredExceptions);

        AtomicReference<G> previousValue = new AtomicReference<>();
        AtomicInteger currentStableCount = new AtomicInteger();

        wait.until(webDriver -> {
            G currentValue = condition.apply(object);

            if (currentValue.equals(previousValue.get())) {
                currentStableCount.getAndIncrement();
            } else {
                currentStableCount.set(0);
                previousValue.set(currentValue);
            }

            return numberOfConsistentValuesRequired.equals(currentStableCount.get());
        });
    }
}

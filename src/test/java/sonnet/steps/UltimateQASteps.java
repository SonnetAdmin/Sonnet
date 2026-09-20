package sonnet.steps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.Given;
import sonnet.po.Common;
import sonnet.po.UltimateQA;

public class UltimateQASteps {
    UltimateQA ultimateQA = new UltimateQA();

        @Given("I select the car {word}")
        public void iSelectTheCar(String car) {
            ultimateQA.chooseCar(car);
        }
    }

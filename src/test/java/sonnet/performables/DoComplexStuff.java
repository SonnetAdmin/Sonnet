package sonnet.performables;

import sonnet.Performable;

import java.net.URL;

public class DoComplexStuff extends Performable {
    String name;
    URL url;

    public DoComplexStuff() {}

    public DoComplexStuff(String name) {
        this.name = name;
    }

    public DoComplexStuff(URL url) {
        this.url = url;
    }

    @Override
    public void run() {
//        Page page = getPage();
        System.out.println("Hello World from Performable?");
    }

    @Override
    public String getDescription() {
        return "Hello World from DoComplexStuff " + name;
    }
}

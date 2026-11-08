package sonnet.reference.playwright;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
/**
 * A container for HTML table cell
 */
public class Cell {

    private String text;

    public Cell(String text) {
        this.text = text;
    }
}

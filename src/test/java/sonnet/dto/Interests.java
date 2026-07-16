package sonnet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Interests {
    private String sports;
    private String music;
    private String reading;
    private String travel;

    public static Interests decode(DTOMap row) {
        return new Interests(
                row.get("sports"),
                row.get("music"),
                row.get("reading"),
                row.get("travel"));
    }
}

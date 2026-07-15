package dto.keyvalue;

import dto.DTOMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeyValue {
    private String key;
    private String value;

    public static KeyValue decode(DTOMap row) {
        return new KeyValue(
                row.get("key"),
                row.get("value"));
    }
}

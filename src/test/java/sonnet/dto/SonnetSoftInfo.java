package sonnet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SonnetSoftInfo {
    private String username;
    private String password;


    public static SonnetSoftInfo decode(DTOMap row) {
        return new SonnetSoftInfo(
                row.get("username"),
                row.get("password"));
    }
}

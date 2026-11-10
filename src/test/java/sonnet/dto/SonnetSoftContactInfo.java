package sonnet.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SonnetSoftContactInfo {
    private String fullName;
    private String email;
    private String message;



    public static SonnetSoftContactInfo decode(DTOMap row) {
        return new SonnetSoftContactInfo(
                row.get("fullName"),
                row.get("email"),
                row.get("message"));
    }
}

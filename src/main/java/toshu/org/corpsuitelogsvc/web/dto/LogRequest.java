package toshu.org.corpsuitelogsvc.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String action;

    @NotBlank
    private String module;

    @NotBlank
    private String comment;

}

package toshu.org.corpsuitelogsvc.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogResponse {

    private String email;

    private String action;

    private String module;

    private String comment;

    private LocalDateTime timestamp;

}

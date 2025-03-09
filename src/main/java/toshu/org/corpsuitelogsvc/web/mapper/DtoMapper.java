package toshu.org.corpsuitelogsvc.web.mapper;

import lombok.experimental.UtilityClass;
import toshu.org.corpsuitelogsvc.model.Log;
import toshu.org.corpsuitelogsvc.web.dto.LogRequest;
import toshu.org.corpsuitelogsvc.web.dto.LogResponse;

@UtilityClass
public class DtoMapper {

    public static LogResponse fromLog(Log log) {
        return LogResponse.builder()
                .email(log.getEmail())
                .action(log.getAction())
                .comment(log.getComment())
                .module(log.getModule())
                .timestamp(log.getTimestamp())
                .build();
    }

    public static Log fromLogRequest(LogRequest logRequest) {
        return Log.builder()
                .email(logRequest.getEmail())
                .action(logRequest.getAction())
                .comment(logRequest.getComment())
                .module(logRequest.getModule())
                .build();
    }
}

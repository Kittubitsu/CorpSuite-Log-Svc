package toshu.org.corpsuitelogsvc.service;

import org.springframework.stereotype.Service;
import toshu.org.corpsuitelogsvc.model.Log;
import toshu.org.corpsuitelogsvc.repository.LogRepository;
import toshu.org.corpsuitelogsvc.web.dto.LogRequest;
import toshu.org.corpsuitelogsvc.web.mapper.DtoMapper;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogService {

    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<Log> getAllLogs() {

        return logRepository.findAll();
    }

    public Log saveLog(LogRequest logRequest) {

        Log log = DtoMapper.fromLogRequest(logRequest);

        log.setTimestamp(LocalDateTime.now());

        return logRepository.save(log);
    }
}

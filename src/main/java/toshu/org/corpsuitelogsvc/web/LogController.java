package toshu.org.corpsuitelogsvc.web;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toshu.org.corpsuitelogsvc.model.Log;
import toshu.org.corpsuitelogsvc.service.LogService;
import toshu.org.corpsuitelogsvc.web.dto.LogRequest;
import toshu.org.corpsuitelogsvc.web.dto.LogResponse;
import toshu.org.corpsuitelogsvc.web.mapper.DtoMapper;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("api/v1/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping
    public ResponseEntity<List<LogResponse>> getLogs() {

        List<LogResponse> allLogs = logService.getAllLogs().stream().sorted(Comparator.comparing(Log::getTimestamp).reversed()).map(DtoMapper::fromLog).toList();

        return ResponseEntity.status(HttpStatus.OK).body(allLogs);
    }

    @PostMapping
    public ResponseEntity<LogResponse> sendLog(@Valid @RequestBody LogRequest logRequest) {

        Log log = logService.saveLog(logRequest);

        LogResponse logResponse = DtoMapper.fromLog(log);

        return ResponseEntity.status(HttpStatus.CREATED).body(logResponse);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllLogs() {

        logService.deleteAllLogs();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}

package toshu.org.corpsuitelogsvc.web.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import toshu.org.corpsuitelogsvc.model.Log;
import toshu.org.corpsuitelogsvc.web.dto.LogRequest;
import toshu.org.corpsuitelogsvc.web.dto.LogResponse;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class DtoMapperUTest {

    @Test
    void givenHappyPath_whenMappingLogToLogResponse() {

        //Given
        Log log = Log.builder()
                .action("Edit")
                .comment("Edited")
                .email("toshu@abv.bg")
                .module("Card")
                .timestamp(LocalDateTime.now())
                .build();

        //When
        LogResponse resultDto = DtoMapper.fromLog(log);

        //Then
        assertEquals(log.getAction(), resultDto.getAction());
        assertEquals(log.getComment(), resultDto.getComment());
        assertEquals(log.getEmail(), resultDto.getEmail());
        assertEquals(log.getModule(), resultDto.getModule());
    }

    @Test
    void givenHappyPath_whenMappingLogRequestToLog() {

        //Given
        LogRequest logRequest = LogRequest.builder()
                .action("Edit")
                .comment("Edited")
                .email("toshu@abv.bg")
                .module("Card")
                .build();

        //When
        Log result = DtoMapper.fromLogRequest(logRequest);

        //Then
        assertEquals(logRequest.getAction(), result.getAction());
        assertEquals(logRequest.getComment(), result.getComment());
        assertEquals(logRequest.getEmail(), result.getEmail());
        assertEquals(logRequest.getModule(), result.getModule());
    }
}

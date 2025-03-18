package toshu.org.corpsuitelogsvc.log;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import toshu.org.corpsuitelogsvc.model.Log;
import toshu.org.corpsuitelogsvc.repository.LogRepository;
import toshu.org.corpsuitelogsvc.service.LogService;
import toshu.org.corpsuitelogsvc.web.dto.LogRequest;
import toshu.org.corpsuitelogsvc.web.dto.LogResponse;
import toshu.org.corpsuitelogsvc.web.mapper.DtoMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LogServiceUTest {

    @Mock
    private LogRepository logRepository;

    @Mock
    private DtoMapper dtoMapper;

    @InjectMocks
    private LogService logService;


    @Test
    void givenLogsInDataBase_whenCalled_thenReturnLogs() {

        //Given
        List<Log> logs = new ArrayList<>(List.of(new Log(), new Log()));

        //When
        when(logRepository.findAll()).thenReturn(logs);

        //Then
        List<Log> allLogs = logService.getAllLogs();

        assertIterableEquals(logs, allLogs);
        verify(logRepository, times(1)).findAll();
    }

    @Test
    void givenLog_whenMethodCalled_thenSavedInDataBase() {

        //Given
        LogRequest logRequest = LogRequest.builder().build();
        Log logMock = spy(new Log());

        MockedStatic<DtoMapper> dtoMapperMockedStatic = mockStatic(DtoMapper.class);
        dtoMapperMockedStatic.when(() -> DtoMapper.fromLogRequest(logRequest)).thenReturn(logMock);


        //When
        when(logRepository.save(any())).thenReturn(logMock);

        logService.saveLog(logRequest);
        //Then
        verify(logMock, times(1)).setTimestamp(any());
        verify(logRepository, times(1)).save(any());

        dtoMapperMockedStatic.close();
    }

    @Test
    void givenLogsInDatabase_whenCalled_thenVerify() {

        logService.deleteAllLogs();

        verify(logRepository, times(1)).deleteAll();

    }

    @Test
    void givenLogsInDatabase_whenCalled_thenReturnSortedReversedAndMapped() {

        //Given
        Log log1 = Log.builder()
                .timestamp(LocalDateTime.of(2025, 3, 1, 10, 0))
                .build();
        Log log2 = Log.builder()
                .timestamp(LocalDateTime.of(2025, 3, 2, 10, 0))
                .build();
        Log log3 = Log.builder()
                .timestamp(LocalDateTime.of(2025, 3, 3, 10, 0))
                .build();

        List<Log> logs = List.of(log1, log2, log3);

        //When
        when(logService.getAllLogs()).thenReturn(logs);

        List<LogResponse> result = logService.getLogResponseSortedReversedMappedToResponse();

        //Then
        assertEquals(3, result.size());
        assertEquals(log3.getTimestamp(), result.get(0).getTimestamp());
        assertEquals(log2.getTimestamp(), result.get(1).getTimestamp());
        assertEquals(log1.getTimestamp(), result.get(2).getTimestamp());

    }
}

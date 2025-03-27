package toshu.org.corpsuitelogsvc.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import toshu.org.corpsuitelogsvc.model.Log;
import toshu.org.corpsuitelogsvc.service.LogService;
import toshu.org.corpsuitelogsvc.web.dto.LogRequest;
import toshu.org.corpsuitelogsvc.web.dto.LogResponse;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogController.class)
public class LogControllerApiTest {

    @MockitoBean
    private LogService logService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getRequestToLogsEndpoint_getListOfLogs() throws Exception {
        MockHttpServletRequestBuilder request = get("/api/v1/logs");

        when(logService.getLogResponseSortedReversedMappedToResponse())
                .thenReturn(new ArrayList<>(List.of(new LogResponse(), new LogResponse())));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));

        verify(logService, times(1)).getLogResponseSortedReversedMappedToResponse();
    }

    @Test
    void postRequestToLogsEndpoint_savesLogAndReturns() throws Exception {

        String jsonRequest = objectMapper.writeValueAsString(LogRequest.builder()
                .module("test")
                .email("test@test.test")
                .comment("test")
                .action("test")
                .build());

        MockHttpServletRequestBuilder request = post("/api/v1/logs").content(jsonRequest).contentType("application/json");

        when(logService.saveLog(any()))
                .thenReturn(new Log());

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"));

        verify(logService, times(1)).saveLog(any());
    }

    @Test
    void deleteRequestToLogsEndpoint_deleteAllLogs() throws Exception {

        MockHttpServletRequestBuilder request = delete("/api/v1/logs");

        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        verify(logService, times(1)).deleteAllLogs();
    }
}

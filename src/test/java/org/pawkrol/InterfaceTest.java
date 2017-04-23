package org.pawkrol;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Created by pawkrol on 2017-04-23.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(NotificationController.class)
public class InterfaceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArduinoService arduinoService;

    @Test
    public void shouldReturnZeroOrOne() throws Exception{
        when(arduinoService.checkRemove()).thenReturn(true);

        this.mockMvc.perform( get("/notif/check") )
                .andDo(print())
                .andExpect(content().string("1"));

        when(arduinoService.checkRemove()).thenReturn(false);

        this.mockMvc.perform( get("/notif/check") )
                .andDo(print())
                .andExpect(content().string("0"));
    }
}

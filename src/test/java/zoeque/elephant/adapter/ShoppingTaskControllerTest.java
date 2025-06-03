package zoeque.elephant.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import zoeque.elephant.testtool.ShoppingTaskDropForTestService;
import zoeque.elephant.usecase.dto.ShoppingTaskDto;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ShoppingTaskControllerTest {
  private MockMvc mvc;
  @Autowired
  ShoppingTaskDropForTestService dropForTestService;
  @Autowired
  ObjectMapper objectMapper;
  @Autowired
  ShoppingTaskController controller;

  @BeforeEach
  public void cleanTable() {
    dropForTestService.deleteAllData();
    mvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void sendRestRequestWithSpecifiedDto_controllerReturns200Ok() {
    Assertions.assertDoesNotThrow(() -> {
      String testDate = "2999/12/31 23:59:59";

      ShoppingTaskDto jsonDto = new ShoppingTaskDto(Long.valueOf(1), "test", testDate);

      String json = objectMapper.writeValueAsString(jsonDto);

      mvc.perform(post("/create")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(json))
              .andExpect(status().isOk());
    });
  }
}

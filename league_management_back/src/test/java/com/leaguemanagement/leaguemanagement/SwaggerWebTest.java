package com.leaguemanagement.leaguemanagement;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class SwaggerWebTest {

  @Autowired
  private MockMvc mockMvc;

  private static final String OUTPUT_DIR = "target";
  private static final String  JSON_FILENAME = "swagger.json";
  private static final String  YML_FILENAME = "swagger.yml";
  private static final String  APIDOCS = "/v2/api-docs";

  @Test
  public void createSpringfoxSwaggerJson() throws Exception {

    Files.createDirectories(Paths.get(OUTPUT_DIR));

    MvcResult mvcResult =
            this.mockMvc.perform(get(APIDOCS).accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andReturn();

    MockHttpServletResponse response = mvcResult.getResponse();
    String swaggerJson = response.getContentAsString();
    writeToFile(JSON_FILENAME, swaggerJson);
    writeToFile(YML_FILENAME, convert(swaggerJson));

  }

  private void writeToFile(String fileName, String content) throws Exception{
    try (BufferedWriter writer =
                 Files.newBufferedWriter(Paths.get(OUTPUT_DIR, fileName), StandardCharsets.UTF_8)) {
      writer.write(content);
    }
  }

  private String convert(String jsonString) throws JsonProcessingException {
    JsonNode jsonNodeTree = (new ObjectMapper()).readTree(jsonString);
    return (new YAMLMapper()).writeValueAsString(jsonNodeTree);
  }
}

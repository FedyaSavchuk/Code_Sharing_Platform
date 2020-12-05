package platform;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.Options;
import model.Code;
import model.EmptyJsonResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class CodeSharingPlatform {

    public void main(String[] args) {
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    @GetMapping("/code")
    public ModelAndView getCode() throws IOException {
        Map<String, Object> params = new HashMap<>();

        ObjectMapper mapper = new ObjectMapper();
        Code code = mapper.readValue(new File(Options.CODE_JSON_FILE), Code.class);
        params.put("code", code);
        return new ModelAndView("getCode", params);
    }

    @GetMapping("api/code")
    public ResponseEntity<String> getJson() throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");

        String content = Files.readString(Paths.get(Options.CODE_JSON_FILE),
                StandardCharsets.US_ASCII);

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(content);
    }

    @GetMapping("/code/new")
    public ModelAndView getNewCode() {
        Map<String, Object> params = new HashMap<>();
        return new ModelAndView("getCodeC", params);
    }

    @PostMapping("api/code/new")
    public ResponseEntity<EmptyJsonResponse> postNewCode(@RequestBody Code request) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formatDateTime = LocalDateTime.now().format(formatter);

        Code code = new Code(request.getCode(), formatDateTime);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(code);
        Files.writeString(Paths.get(Options.CODE_JSON_FILE), jsonString);

        return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
    }
}

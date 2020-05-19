package fse.project.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;

import java.io.IOException;

import static java.nio.charset.Charset.forName;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class TestUtil {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(APPLICATION_JSON.getType(),
            APPLICATION_JSON.getSubtype(), forName("utf8"));
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return OBJECT_MAPPER.writeValueAsBytes(object);
    }

}
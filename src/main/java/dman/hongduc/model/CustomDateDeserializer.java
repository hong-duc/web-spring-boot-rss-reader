package dman.hongduc.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author duc
 */
public class CustomDateDeserializer extends StdDeserializer<LocalDate> {
    
    public CustomDateDeserializer(){
        this(null);
    }
    
    public CustomDateDeserializer(Class c){
        super(c);
    }
    

    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        String date = jp.getText();
        return LocalDate.parse(date,DateTimeFormatter.ISO_DATE_TIME);
    }
    
}

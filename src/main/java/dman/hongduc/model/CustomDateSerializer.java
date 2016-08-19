package dman.hongduc.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.LocalDate;

/**
 *
 * @author duc
 */
public class CustomDateSerializer extends StdSerializer<LocalDate> {
    
    public CustomDateSerializer(){
        this(null);
    }
    
    public CustomDateSerializer(Class c){
        super(c);
    }

    @Override
    public void serialize(LocalDate value, JsonGenerator jg, SerializerProvider sp) throws IOException {
        jg.writeString(value.toString());
    }
    
}

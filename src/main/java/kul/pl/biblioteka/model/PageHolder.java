package kul.pl.biblioteka.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;
import java.io.IOException;


@JsonComponent
public class PageHolder extends JsonSerializer<Page<?>> {
    @Override
    public void serialize(Page page, JsonGenerator json, SerializerProvider serializers) throws IOException {
        json.writeStartObject();
        json.writeObjectField("content", page.getContent());
        json.writeBooleanField("first", page.isFirst());
        json.writeBooleanField("last", page.isLast());
        json.writeNumberField("currentPage", page.getPageable().getPageNumber());
        json.writeNumberField("totalPages", page.getTotalPages());
        json.writeNumberField("totalElements", page.getTotalElements());
        json.writeNumberField("numberOfElements", page.getNumberOfElements());
        json.writeEndObject();
    }

}

package br.com.william.screenmatch.services;

import br.com.william.screenmatch.models.Series;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FormatarJSON {
    ObjectMapper mapper = new ObjectMapper();
    public <T> String converterJSON(Object json) throws JsonProcessingException {
        var prettyObject = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        return prettyObject;
    }
}

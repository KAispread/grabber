package com.kaispread.grabber.application.json;

import com.kaispread.grabber.application.dto.company.CompanyDto;
import com.kaispread.grabber.application.dto.scrap.ScrapJdDto;
import com.kaispread.grabber.exception.json.JsonParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import reactor.core.publisher.Flux;

public abstract class CustomJsonParser {

    private final JSONParser jsonParser;

    protected CustomJsonParser(JSONParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public JSONObject parseToJsonObject(final String scrapStr) {
        try {
            return (JSONObject) jsonParser.parse(scrapStr);
        } catch (ParseException | ClassCastException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public JSONObject parseToJsonObject(final Object object) {
        try {
            return (JSONObject) object;
        } catch (ClassCastException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public JSONArray parseToJsonArray(final JSONObject jsonObject, final String key) {
        try {
            return  (JSONArray) jsonObject.get(key);
        } catch (ClassCastException e) {
            throw new JsonParseException(e.getMessage());
        }
    }

    public abstract Flux<ScrapJdDto> parseToJdDto(JSONArray jsonArray, CompanyDto companyDto);
}

package lv.id.voldemars.deviceapi.models;

import lombok.Data;
import lv.id.voldemars.deviceapi.models.enums.ResponseStatus;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {

    ResponseStatus responseStatus;
    List<Pair<Long, String>> errorMessages = new ArrayList<>();

}

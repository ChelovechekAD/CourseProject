package it.academy.utilities;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import it.academy.exceptions.ExtractorReflectionException;
import it.academy.exceptions.RequestParamInvalidException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.lang.reflect.Field;

@UtilityClass
public class Extractor {

    public static <T> T extractSingleParamFromRequest(HttpServletRequest request, String paramKey, Class<T> targetType) {
        String value = request.getParameter(paramKey);
        System.out.println(value + " " + paramKey);
        if (value == null) {
            throw new RequestParamInvalidException();
        }
        return (T) convertToType(value, targetType);
    }

    public static <T> T extractDTOFromRequest(HttpServletRequest request, T outDTO) {
        Class<?> tClass = outDTO.getClass();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            String param = request.getParameter(field.getName());
            if (param == null) {
                throw new RequestParamInvalidException();
            }
            field.setAccessible(true);
            try {
                field.set(outDTO, convertToType(param, field.getType()));
            } catch (IllegalAccessException e) {
                throw new ExtractorReflectionException(e.getMessage());
            }
        }
        return outDTO;
    }

    public static <T> T extractSingleParamFromRequestBody(String req, String paramKey, Class<T> targetType) throws IOException {
        JsonElement tree = JsonParser.parseString(req);
        JsonElement elem = null;
        if (tree.isJsonObject()) {
            elem = tree.getAsJsonObject().get(paramKey);
        } else {
            JsonArray elements = tree.getAsJsonArray();
            for (int i = 0; i < elements.size(); i++) {
                JsonElement testElem = elements.get(i).getAsJsonObject().get(paramKey);
                if (testElem != null) {
                    elem = testElem;
                    break;
                }
            }
        }
        if (elem == null) {
            throw new RequestParamInvalidException();
        }
        return (T) convertToTypeJsonElement(elem, targetType);
    }

    private static Object convertToType(String value, Class<?> targetType) {

        if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(value);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value);
        } else if (targetType == String.class) {
            return value;
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            return Boolean.getBoolean(value);
        } else {
            throw new RequestParamInvalidException(Constants.UNSUPPORTED_FIELD_TYPE + targetType);
        }
    }

    private static Object convertToTypeJsonElement(JsonElement value, Class<?> targetType) {

        if (targetType == int.class || targetType == Integer.class) {
            return value.getAsInt();
        } else if (targetType == long.class || targetType == Long.class) {
            return value.getAsLong();
        } else if (targetType == double.class || targetType == Double.class) {
            return value.getAsDouble();
        } else if (targetType == String.class) {
            return value.getAsString();
        } else if (targetType == Boolean.class || targetType == boolean.class) {
            return value.getAsBoolean();
        } else {
            throw new RequestParamInvalidException(Constants.UNSUPPORTED_FIELD_TYPE + targetType);
        }
    }

}

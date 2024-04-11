package it.academy.utilities;

import it.academy.exceptions.ExtractorReflectionException;
import it.academy.exceptions.RequestParamInvalidException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;

@UtilityClass
public class Extractor {

    public static <T> T extractSingleParamFromRequest(HttpServletRequest request, String paramKey, Class<T> targetType) {
        String value = request.getParameter(paramKey);
        if (value == null){
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

    private static Object convertToType(String value, Class<?> targetType) {

        if (targetType == int.class || targetType == Integer.class) {
            return Integer.parseInt(value);
        } else if (targetType == long.class || targetType == Long.class) {
            return Long.parseLong(value);
        } else if (targetType == double.class || targetType == Double.class) {
            return Double.parseDouble(value);
        } else if (targetType == String.class) {
            return value;
        } else {
            throw new RequestParamInvalidException(Constants.UNSUPPORTED_FIELD_TYPE + targetType);
        }
    }

}

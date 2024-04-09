package it.academy.utilities;

import com.google.gson.Gson;
import it.academy.DTO.request.LoginUserDTO;
import it.academy.DTO.request.RegUserDTO;
import it.academy.DTO.response.LoginUserJwtDTO;
import it.academy.JsonDTO.request.LoginRequest;
import it.academy.JsonDTO.request.RegistrationRequest;
import lombok.experimental.UtilityClass;
import org.json.JSONObject;

import static it.academy.utilities.Constants.GSON;

@UtilityClass
public class ConverterJson {

    public static RegUserDTO convertJsonRegReqToDTO(String json){

        RegistrationRequest request = GSON.fromJson(json, RegistrationRequest.class);
        return RegUserDTO.builder()
                .id(0L)
                .email(request.getEmail())
                .password(request.getPassword())
                .passwordConfirm(request.getPasswordConfirm())
                .name(request.getName())
                .surname(request.getSurname())
                .phoneNumber(request.getPhoneNumber())
                .build();

    }

    public static LoginUserDTO convertJsonLoginToDTO(String json){

        LoginRequest request = GSON.fromJson(json, LoginRequest.class);
        return LoginUserDTO.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

    }

}

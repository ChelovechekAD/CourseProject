package it.academy.JsonDTO.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest implements Serializable {

    @SerializedName(value = "email")
    @Expose
    private String email;
    @SerializedName(value = "password")
    @Expose
    private String password;
    @SerializedName(value = "passwordConfirm")
    @Expose
    private String passwordConfirm;
    @SerializedName(value = "name")
    @Expose
    private String name;
    @SerializedName(value = "surname")
    @Expose
    private String surname;
    @SerializedName(value = "phoneNumber")
    @Expose
    private String phoneNumber;


}
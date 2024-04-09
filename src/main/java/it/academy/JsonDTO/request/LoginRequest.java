package it.academy.JsonDTO.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {

    @SerializedName(value = "email")
    @Expose
    private String email;
    @SerializedName(value = "password")
    @Expose
    private String password;

}

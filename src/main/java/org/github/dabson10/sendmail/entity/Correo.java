package org.github.dabson10.sendmail.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Correo {
    private String mail;
    private String header;
    private String body;
    private String name;

    public Map<String, String> saveData() {
        Map<String, String> data = new HashMap<>();
        data.put("mail", mail);
        data.put("header", header);
        data.put("body", body);
        data.put("name", name);
        return data;
    }

    public boolean validateData() {
        boolean validacion = false;
        Map<String, String> data = saveData();
        //Se recorre el mapa para validar que no existan datos vacíos.
        for (Map.Entry<String, String> valor : data.entrySet()) {
            if (valor.getValue() == null || valor.getValue().trim().isEmpty()) {
                //Si el valor es empty o vacío entonces hacemos un return y regresamos un valor booleano.
                return true;
            }
        }
        return validacion;
    }
}

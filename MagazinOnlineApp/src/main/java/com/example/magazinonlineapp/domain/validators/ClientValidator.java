package com.example.magazinonlineapp.domain.validators;

import com.example.magazinonlineapp.domain.Client;

public class ClientValidator implements Validator<Client>{
    @Override
    public void validate(Client client) throws ValidationException {
        String errors = "";

        if (client.getNume() == null || client.getNume().isEmpty())
            errors += "Client last name is null.\n";

        if (client.getPrenume() == null || client.getPrenume().isEmpty())
            errors += "Client first name is null.\n";

        if (client.getEmail() == null || client.getEmail().isEmpty()) {
            errors += "Email is null.\n";
        }

        if (client.getParola() == null || client.getParola().isEmpty()) {
            errors += "Password is not set. Null value!!\n";
        }

        if(!errors.isEmpty())
            throw new ValidationException(errors);
    }
}

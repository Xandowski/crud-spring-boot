package br.com.netservicos.clients.util.validation;

import br.com.netservicos.clients.exception.InvalidFormatEntityException;

public class ClientRequestValidationUtils {
    private ClientRequestValidationUtils(){
        throw new IllegalStateException("Utility class");
    }

    public static void validateCpfOrCnpj(String cpfOrCnpj) throws InvalidFormatEntityException {
        if(!isCpfOrCnjValid(cpfOrCnpj)){
            throw new InvalidFormatEntityException("CPF or CNPJ is invalid: ", cpfOrCnpj);
        }
    }

    private static boolean isCpfOrCnjValid(String cpfOrCnpj) {
        return cpfOrCnpj.matches("^([0-9]{11}|[0-9]{14})$");
    }
}

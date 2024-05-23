package com.example.crud.Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraints.br.CNPJ;

public class CnpjValidation {
    public static class CNPJValidator implements ConstraintValidator<CNPJ, String> {

        @Override
        public void initialize(CNPJ constraintAnnotation) {
        }

        @Override
        public boolean isValid(String cnpj, ConstraintValidatorContext context) {
            if (cnpj == null || cnpj.isEmpty()) {
                return false;
            }
            // Remover caracteres especiais
            cnpj = cnpj.replaceAll("\\D", "");

            if (cnpj.length() != 14) {
                return false;
            }

            // Validar dígitos verificadores
            try {
                char dig13, dig14;
                int sm, i, r, num, peso;

                // Calculo do 1o. Digito Verificador
                sm = 0;
                peso = 2;
                for (i = 11; i >= 0; i--) {
                    num = (int) (cnpj.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso + 1;
                    if (peso == 10) peso = 2;
                }

                r = sm % 11;
                if ((r == 0) || (r == 1)) dig13 = '0';
                else dig13 = (char) ((11 - r) + 48);

                // Calculo do 2o. Digito Verificador
                sm = 0;
                peso = 2;
                for (i = 12; i >= 0; i--) {
                    num = (int) (cnpj.charAt(i) - 48);
                    sm = sm + (num * peso);
                    peso = peso + 1;
                    if (peso == 10) peso = 2;
                }

                r = sm % 11;
                if ((r == 0) || (r == 1)) dig14 = '0';
                else dig14 = (char) ((11 - r) + 48);

                // Verifica se os dígitos calculados conferem com os dígitos informados.
                if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13))) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
    }
}

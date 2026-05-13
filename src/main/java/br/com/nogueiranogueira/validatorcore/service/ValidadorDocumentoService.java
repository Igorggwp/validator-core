package br.com.nogueiranogueira.validatorcore.service;

import br.com.nogueiranogueira.validatorcore.documento.Cnpj;
import br.com.nogueiranogueira.validatorcore.documento.Cpf;
import br.com.nogueiranogueira.validatorcore.documento.Documento;
import br.com.nogueiranogueira.validatorcore.documento.Ssn;

public class ValidadorDocumentoService {

    public boolean validar(Documento documento) {
        if (documento == null || documento.numero() == null) {
            return false;
        }

        return switch (documento) {
            case Cpf cpf -> validarCpf(cpf.numero());
            case Cnpj cnpj -> validarCnpj(cnpj.numero());
            case Ssn ssn -> validarSsn(ssn.numero());
        };
    }

    private boolean validarCpf(String valor) {
        String digits = somenteDigitos(valor);
        return digits.matches("\\d{11}");
    }

    private boolean validarCnpj(String valor) {
        String digits = somenteDigitos(valor);
        return digits.matches("\\d{14}");
    }

    private boolean validarSsn(String valor) {
        return valor.matches("\\d{3}-\\d{2}-\\d{4}");
    }

    private String somenteDigitos(String valor) {
        return valor.replaceAll("\\D", "");
    }
}

package br.com.nogueiranogueira.validatorcore.documento;

public sealed interface Documento permits Cpf, Cnpj, Ssn {
    String numero();
}

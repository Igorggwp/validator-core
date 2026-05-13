package br.com.nogueiranogueira.validatorcore.service;

import br.com.nogueiranogueira.validatorcore.documento.Cnpj;
import br.com.nogueiranogueira.validatorcore.documento.Cpf;
import br.com.nogueiranogueira.validatorcore.documento.Ssn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidadorDocumentoServiceTest {

    private final ValidadorDocumentoService service = new ValidadorDocumentoService();

    @Test
    void deveValidarCpf() {
        assertTrue(service.validar(new Cpf("12345678901")));
        assertFalse(service.validar(new Cpf("123")));
    }

    @Test
    void deveValidarCnpj() {
        assertTrue(service.validar(new Cnpj("12345678000199")));
        assertFalse(service.validar(new Cnpj("999")));
    }

    @Test
    void deveValidarSsn() {
        assertTrue(service.validar(new Ssn("123-45-6789")));
        assertFalse(service.validar(new Ssn("123456789")));
    }
}

# validator-core

Repositorio 1 da atividade de Reuso e Refatoracao.

## Objetivo

Componente base reutilizavel para validacao de documentos.
Este projeto e uma biblioteca Maven pura e nao executa fluxo de aplicacao completo.

## Requisitos Tecnicos Atendidos

1. Gestao de dependencias
- Projeto Maven puro (sem Spring).

2. Dominio modernizado
- Sealed interface: Documento.
- Records: Cpf, Cnpj, Ssn.

3. Motor de validacao
- Service com pattern matching em switch exaustivo para Documento.

4. Empacotamento
- groupId: br.com.nogueiranogueira.reuso
- artifactId: validator-core
- version: 1.0.0

## Estrutura

```text
src/main/java/br/com/nogueiranogueira/validatorcore/
  documento/
    Documento.java
    Cpf.java
    Cnpj.java
    Ssn.java
  service/
    ValidadorDocumentoService.java
```

## Build

```bash
mvn clean install
```

Resultado esperado:
- JAR gerado em target/validator-core-1.0.0.jar
- Artefato instalado em ~/.m2/repository/br/com/nogueiranogueira/reuso/validator-core/1.0.0

## Uso em outro repositorio

```xml
<dependency>
    <groupId>br.com.nogueiranogueira.reuso</groupId>
    <artifactId>validator-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Exemplo de Uso

```java
import br.com.nogueiranogueira.validatorcore.documento.Cpf;
import br.com.nogueiranogueira.validatorcore.service.ValidadorDocumentoService;

ValidadorDocumentoService service = new ValidadorDocumentoService();

// Validar CPF
boolean ehValido = service.validar(new Cpf("12345678901"));
System.out.println(ehValido); // true
```

## 🧪 Testes

O projeto inclui testes unitários para validação de cada tipo de documento:

```bash
mvn test
```

Cobertura de testes:
- ✅ Cpf com 11 dígitos → válido
- ✅ Cpf com menos dígitos → inválido
- ✅ Cnpj com 14 dígitos → válido
- ✅ Cnpj com formato inválido → inválido
- ✅ Ssn no formato XXX-XX-XXXX → válido
- ✅ Ssn em formato incorreto → inválido
- ✅ Documento nulo → inválido
- ✅ Número nulo → inválido

## 🔧 Configuração do Projeto

**pom.xml** - Configurações principais:

| Propriedade | Valor |
|---|---|
| groupId | br.com.nogueiranogueira.reuso |
| artifactId | validator-core |
| version | 1.0.0 |
| Java Release | 21 |
| Codificação | UTF-8 |
| JUnit Version | 5.12.2 |

## 📝 Notas de Desenvolvimento

### Adicionar um Novo Tipo de Documento

1. **Criar nova record** em `documento/`:
   ```java
   public record NovoDocumento(String numero) implements Documento { }
   ```

2. **Atualizar interface selada** `Documento.java`:
   ```java
   public sealed interface Documento permits Cpf, Cnpj, Ssn, NovoDocumento { ... }
   ```

3. **Adicionar caso no ValidadorDocumentoService**:
   ```java
   return switch (documento) {
       case Cpf cpf -> validarCpf(cpf.numero());
       case Cnpj cnpj -> validarCnpj(cnpj.numero());
       case Ssn ssn -> validarSsn(ssn.numero());
       case NovoDocumento novo -> validarNovoDocumento(novo.numero());
   };
   ```

4. **Adicionar testes** em `ValidadorDocumentoServiceTest.java`

O compilador **força a atualização** do switch - nenhum novo tipo pode ser adicionado sem atualizar a lógica de validação.

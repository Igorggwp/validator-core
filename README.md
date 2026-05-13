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

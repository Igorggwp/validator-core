# validator-core

Biblioteca Maven pura com ativos de domínio reutilizáveis para validação de documentos.

## 📋 Objetivo deste Repositório

Este projeto representa o **componente base da arquitetura** de validação de documentos. Não é um servidor web nem define um fluxo de aplicação completo. Seu papel é disponibilizar **tipos de domínio fortemente tipados** e **serviços reutilizáveis** para outros sistemas da organização.

### Por que um repositório separado?

- **Reusabilidade**: Componente independente que pode ser consumido por múltiplos projetos (web, mobile, jobs, etc.)
- **Versionamento**: Controle claro de versões sem acoplamento com aplicações específicas
- **Manutenção**: Evolução do domínio centralizada em um único lugar
- **Testabilidade**: Testes isolados de lógica de validação

## 🏛️ Tecnologia

- **Java**: 21
- **Build**: Maven 3.8+
- **Testes**: JUnit 5 (Jupiter)
- **Arquitetura**: Domain-Driven Design com Sealed Types

## ✨ Características Técnicas

### 1. **Sealed Interfaces + Records**
Implementação modernizada do domínio utilizando features do Java 21:

```java
// Interface selada garante set fechado de implementadores
public sealed interface Documento permits Cpf, Cnpj, Ssn {
    String numero();
}

// Records elimina boilerplate (getters, equals, hashCode, toString)
public record Cpf(String numero) implements Documento { }
public record Cnpj(String numero) implements Documento { }
public record Ssn(String numero) implements Documento { }
```

### 2. **Pattern Matching com Switch Exaustivo**
O serviço de validação utiliza pattern matching para garantir tratamento de todos os tipos:

```java
return switch (documento) {
    case Cpf cpf -> validarCpf(cpf.numero());
    case Cnpj cnpj -> validarCnpj(cnpj.numero());
    case Ssn ssn -> validarSsn(ssn.numero());
};
```

O compilador **garante exaustividade** - se um novo tipo de `Documento` for adicionado, o `switch` gerará erro de compilação.

## 📁 Estrutura do Projeto

```
src/main/java/br/com/nogueiranogueira/validatorcore/
├── documento/           # Contratos de domínio (Sealed Interface + Records)
│   ├── Documento.java   # Interface selada
│   ├── Cpf.java        # CPF (11 dígitos)
│   ├── Cnpj.java       # CNPJ (14 dígitos)
│   └── Ssn.java        # SSN americano (XXX-XX-XXXX)
└── service/
    └── ValidadorDocumentoService.java  # Motor de validação
    
src/test/java/br/com/nogueiranogueira/validatorcore/
└── service/
    └── ValidadorDocumentoServiceTest.java  # Testes unitários
```

## 🚀 Build e Instalação Local

### Pré-requisitos
- Java 21 (JDK)
- Maven 3.8+

### Compilar e Instalar

```bash
mvn clean install
```

**Resultado esperado:**
- ✅ 3/3 testes passando
- ✅ JAR gerado em `target/validator-core-1.0.0.jar`
- ✅ Publicado no repositório local em `~/.m2/repository/br/com/nogueiranogueira/reuso/validator-core/1.0.0`

### Apenas Compilar (sem testes)

```bash
mvn clean compile
```

### Executar Testes

```bash
mvn test
```

## 📦 Integração em Outros Projetos

### Adicionar Dependência no pom.xml

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

# Concessionria [![Java CI with Maven](https://github.com/abnerjosefelixbarbosa/concessionria/actions/workflows/maven.yml/badge.svg)](https://github.com/abnerjosefelixbarbosa/concessionria/actions/workflows/maven.yml)

# Sobre

Aplicativo web para gerenciamento de concessionaria.

## Modelo

```mermaid
classDiagram

Employee "1..1" -- "0..*" Sale

Customer "1..1" -- "0..*" Sale

Item "0..*" -- "1..1" Vehicle 

Item "0..*" -- "1..1" Sale 

Model "1..1" -- "0..*" Vehicle 

Brand "1..1" -- "0..*" Model

CustomerType -- Customer

EmployeeStatus -- Employee

EmployeeType -- Employee

Sale -- PaymentType

Vehicle -- TransmissionType

Vehicle -- VehicleStatus

class Employee {
<<Entity>>
- String id
- String name
- String matriculation
- String email
- String phone
- LocalDate birthDate
- String cpf
- BigDecimal salary
- Integer commission
- EmployeeStatus employeeStatus
- EmployeeType employeeType
- List~Sale~ sales
}

class Customer {
<<Entity>>
- String id
- String name
- String document
- String email
- String phone
- CustomerType customerType
- List~Sale~ sales
}

class Sale {
<<Entity>>
- String id
- LocalDate saleDate
- PaymentType paymentType
- BigDecimal totalValue
- Employee employee
- Customer customer
- List~Item~ items
}

class Item {
<<Entity>>
- String id
- Sale sale
- Vehicle vehicle 
}

class Vehicle {
<<Entity>>
- String id
- String placa
- TransmissionType transmissionType
- VehicleStatus vehicleStatus
- String color
- BigDecimal price
- Model model
- List~Item~ items
}

class Model {
<<Entity>>
- String id
- String name
- Brand brand
- List~Vehicle~ vehicles
}

class Brand {
<<Entity>>
- String id
- String name
- List~Model~ models
}

class EmployeeStatus {
<<Enum>>
ACTIVE, INACTIVE;
} 

class EmployeeType {
<<Enum>>
MANAGER, ASSISTANT_MANAGER, SALLER;
} 

class PaymentType {
<<Enum>>
CASH, CREDIT_CARD, DEBIT_CARD, PIX;
}

class TransmissionType {
<<Enum>>
MANUAL, AUTOMATIC;
}

class VehicleStatus {
<<Enum>>
FOR_SALE, SOLD;
}

class CustomerType {
<<Enum>>
PF,PJ;
}
```

# Recursos do projeto

## Backend

- Spring Boot
- H2 DB
- PostgreeSQL
- Spring Data JPA
- MVC
- SOLID

## Fucionalidades

- Registrar funcionario.
- Atualizar funcionario pelo id.
- Listar funcionario filtrado pelo nome, status do funcionário ou tipo do funcionário.
- Procurar funcionario pelo id.
- Registrar cliente.
- Atualizar cliente pelo id.
- Listar cliente filtrado pelo nome.
- Procurar cliente pelo id.

# Execução do projeto 

- Copie o repositorio em uma IDE.
- Execute o projeto.

```bash
# clone repository
git clone https://github.com/abnerjosefelixbarbosa/api-controle-de-estoque.git
```

# Autor

Abner José Felix Barbosa

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/abner-jose-feliz-barbosa/)

# Concessionria [![Java CI with Maven](https://github.com/abnerjosefelixbarbosa/concessionria/actions/workflows/maven.yml/badge.svg)](https://github.com/abnerjosefelixbarbosa/concessionria/actions/workflows/maven.yml)

# Sobre

Aplicativo web para gerenciamento de concessionaria.

## Modelo

```mermaid
classDiagram

Employee "1" -- "*" Sale

Customer "1" -- "*" Sale

Item "*" --  "1" Vehicle 

Item "*" -- "1" Sale 

Model "1" -- "*" Vehicle 

Brand "1" -- "*" Model

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
- String plate
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

## Funcionalidades

- Registrar funcionário.
- Atualizar funcionário pelo id.
- Listar funcionários.
- Procurar funcionário pelo id.
- Registrar cliente.
- Atualizar cliente pelo id.
- Listar clientes.
- Procurar cliente pelo id.
- Registrar marca.
- Atualizar marca pelo id.
- Procurar marca pelo id.
- Listar marcas.
- Registrar modelo.
- Atualizar modelo pelo id.
- Procurar modelo pelo id.
- Listar modelos.
- Registrar veículo.
- Atualizar veículo pelo id.
- Listrar veículos.
- Procurar veículo pelo id.
- Registrar venda
- Listar vendas
- Deletar vendas pelo id.   

# Execução do projeto 

- Copie o repositório em uma IDE.
- Execute o projeto.

```bash
# clone repository
git clone https://github.com/abnerjosefelixbarbosa/api-controle-de-estoque.git
```

# Documentação da API

Documentação da API concessionaria

## Marca

### Registar marca

Método: 

POST

Path:

`/brands`

Corpo da requisição:

```json
{
  "name": "Volkswagen"
}
```

Corpo da resposta:

```json
{
  "id": "5609a5c0-8334-4379-94b6-ac7a55d6c755",
  "name": "Volkswagen"
}
```

Corpo de resposta para error de requisção:

```json
{
  "localDateTime": "2026-09-03 15:41",
  "status": 400,
  "message": "Nome deve não deve ser repetido.",
  "path": "/brands"
}
```

### Atualizar marca pelo id

Método: 

PUT

Path:

`/brands/5609a5c0-8334-4379-94b6-ac7a55d6c755`

Parametro:

- id

Corpo da requisição:

```json
{
  "name": "Fiat"
}
```

Corpo da resposta:

```json
{
  "id": "5609a5c0-8334-4379-94b6-ac7a55d6c755",
  "name": "Fiat"
}
```

Corpo de resposta para error de requisção:

```json
{
  "localDateTime": "2026-09-03 16:01",
  "status": 400,
  "message": "Nome deve não deve ser repetido.",
  "path": "/brands/5609a5c0-8334-4379-94b6-ac7a55d6c755"
}
```

Corpo de resposta para erro de conteúdo não encontrado:

```json
{
  "localDateTime": "2026-09-03 16:42",
  "status": 404,
  "message": "Id deve ser existente.",
  "path": "/brands/5609a5c0-8334-4379-94b6-ac7a55d6c7551"
}
```

### Procurar marca pelo id

Método: 

GET

Path:

`/brands/5609a5c0-8334-4379-94b6-ac7a55d6c755`

Parametro:

- id

Corpo da resposta:

```json
{
  "id": "5609a5c0-8334-4379-94b6-ac7a55d6c755",
  "name": "Fiat"
}
```

Corpo de resposta para erro de conteúdo não encontrado:

```json
{
  "localDateTime": "2026-09-03 16:42",
  "status": 404,
  "message": "Id deve ser existente.",
  "path": "/brands/5609a5c0-8334-4379-94b6-ac7a55d6c7551"
}
```

### Listar marcas

Método: 

GET

Path:

`/brands`

Parametro:

- nome

Corpo da resposta:

```json
{
  "content": [
    {
      "id": "5609a5c0-8334-4379-94b6-ac7a55d6c755",
      "name": "Volkswagen"
    }
  ],
  "empty": false,
  "first": true,
  "last": true,
  "number": 0,
  "numberOfElements": 1,
  "pageable": {
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 20,
    "paged": true,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "unpaged": false
  },
  "size": 20,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "totalElements": 1,
  "totalPages": 1
}
```

# Autor

Abner José Felix Barbosa

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/abner-jose-feliz-barbosa/)

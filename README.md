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

Status:

201

Path:

`/brands`

Corpo de requisição:

```json
{
  "name": "Volkswagen"
}
```

Corpo de resposta:

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

Status:

200

Path:

`/brands/{id}`

Parametro:

- id

Corpo de requisição:

```json
{
  "name": "Fiat"
}
```

Corpo de resposta:

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

Status:

200

Path:

`/brands/{id}`

Parametro:

- id

Corpo de resposta:

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

Status:

200

Path:

`/brands`

Parametro:

- nome

Corpo de resposta:

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

## Modelo

### Registrar modelo

Método: 

POST

Status:

201

Path:

`/models`

Corpo de requisição:

```json
{
  "name": "Polo",
  "brandName": "Volkswagen"
}
```

Corpo de resposta:

```json
{
  "id": "4f7913af-4e06-4745-954a-6e2c6f664ff7",
  "name": "Polo",
  "brandName": "Volkswagen"
}
```

Corpo de resposta para erro de requisição:

```json
{
  "localDateTime": "2026-09-03 18:12",
  "status": 400,
  "message": "Nome não deve ser repetido.",
  "path": "/models"
}
```

Corpo de resposta para erro de conteúdo não encontrado:

```json
{
  "localDateTime": "2026-09-03 18:08",
  "status": 404,
  "message": "Nome da marca deve ser existente.",
  "path": "/models"
}
```

### Atualizar modelo pelo id

Método: 

PUT

Status:

200

Path:

`/models/{id}`

Parametro:

- id

Corpo de requisição:

```json
{
  "name": "Polo Track",
  "brandName": "Volkswagen"
}
```

Corpo de resposta:

```json
{
  "id": "4f7913af-4e06-4745-954a-6e2c6f664ff7",
  "name": "Polo Track",
  "brandName": "Volkswagen"
}
```

Corpo de resposta para erro de requisição:

```json
{
  "localDateTime": "2026-09-03 18:12",
  "status": 400,
  "message": "Nome não deve ser repetido.",
  "path": "/models"
}
```

Corpo de resposta para erro de conteúdo não encontrado:

```json
{
  "localDateTime": "2026-09-03 18:08",
  "status": 404,
  "message": "Nome da marca deve ser existente.",
  "path": "/models"
}
```

```json
{
  "localDateTime": "2026-09-03 18:45",
  "status": 404,
  "message": "Id deve ser existente.",
  "path": "/models/4f7913af-4e06-4745-954a-6e2c6f664ff71"
}
```

### Procurar modelo pelo id

Método: 

GET

Status:

200

Path:

`/models/{id}`

Parametro:

- id

Corpo de resposta:

```json
{
  "id": "4f7913af-4e06-4745-954a-6e2c6f664ff7",
  "name": "Polo",
  "brandName": "Volkswagen"
}
```

Corpo de resposta para erro de conteúdo não encontrado:

```json
{
  "localDateTime": "2026-09-03 18:45",
  "status": 404,
  "message": "Id deve ser existente.",
  "path": "/models/4f7913af-4e06-4745-954a-6e2c6f664ff71"
}
```

### Listar modelos

Método: 

GET

Status:

200

Path:

`/models`

Parametro:

- nome

Corpo de resposta:

```json
{
  "content": [
    {
      "id": "4f7913af-4e06-4745-954a-6e2c6f664ff7",
      "name": "Polo",
      "brandName": "Volkswagen"
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

## Veículo

### Registrar veículo

Método: 

POST

Status:

201

Path:

`/vehicles`

Corpo de requisição:

```json
{
  "plate": "AAA-1111",
  "transmissionType": "MANUAL",
  "vehicleStatus": "FOR_SALE",
  "color": "Preto",
  "price": 5000.00,
  "modelName": "Polo"
}
```

Corpo de resposta:

```json
{
  "id": "0b0b48ff-a9e7-40f7-8ab2-0954fcb172c9",
  "plate": "AAA-1111",
  "transmissionType": "MANUAL",
  "vehicleStatus": "FOR_SALE",
  "color": "Preto",
  "price": 5000,
  "modelName": "Polo"
}
```

Corpo de resposta para erro de requisição

```json
{
  "localDateTime": "2026-09-04 15:43",
  "status": 400,
  "message": "Placa não deve ser repetida.",
  "path": "/vehicles"
}
```

Corpo de resposta para erro de conteúdo não encontrado

```json
{
  "localDateTime": "2026-09-04 16:57",
  "status": 404,
  "message": "Nome do modelo deve ser existente.",
  "path": "/vehicles"
}
```

### Atualizar veículo pelo id

Método: 

PUT

Status:

200

Path:

`/vehicles/{id}`

Paramtro:

- id

Corpo de requisição:

```json
{
  "plate": "AAA-1112",
  "transmissionType": "MANUAL",
  "vehicleStatus": "FOR_SALE",
  "color": "Preto",
  "price": 5000.00,
  "modelName": "Polo"
}
```

Corpo de resposta:

```json
{
  "id": "0b0b48ff-a9e7-40f7-8ab2-0954fcb172c9",
  "plate": "AAA-1112",
  "transmissionType": "MANUAL",
  "vehicleStatus": "FOR_SALE",
  "color": "Preto",
  "price": 5000.00,
  "modelName": "Polo"
}
```

Corpo de resposta para erro de requisição

```json
{
  "localDateTime": "2026-09-04 15:43",
  "status": 400,
  "message": "Placa não deve ser repetida.",
  "path": "/vehicles"
}
```

Corpo de resposta para erro de conteúdo não encontrado

```json
{
  "localDateTime": "2026-09-04 16:57",
  "status": 404,
  "message": "Nome do modelo deve ser existente.",
  "path": "/vehicles"
}
```

```json
{
  "localDateTime": "2026-09-04 16:35",
  "status": 404,
  "message": "Id deve ser existente.",
  "path": "/vehicles/0b0b48ff-a9e7-40f7-8ab2-0954fcb172c91"
}
```

### Procurar veículo pelo id

Método: 

GET

Status:

200

Path:

`/vehicles/{id}`

Paramtro:

- id

Corpo de resposta:

```json
{
  "id": "0b0b48ff-a9e7-40f7-8ab2-0954fcb172c9",
  "plate": "AAA-1111",
  "transmissionType": "MANUAL",
  "vehicleStatus": "FOR_SALE",
  "color": "Preto",
  "price": 5000,
  "modelName": "Polo"
}
```

Corpo de resposta para erro de conteúdo não encontrado

```json
{
  "localDateTime": "2026-09-04 16:35",
  "status": 404,
  "message": "Id deve ser existente.",
  "path": "/vehicles/0b0b48ff-a9e7-40f7-8ab2-0954fcb172c91"
}
```

### Listar veículos

Método: 

GET

Status:

200

Path:

`/vehicles`

Paramtro:

- tipo de transmisão
- preço
- status do veículo
- cor
- placa

Corpo de resposta:

```json
{
  "content": [
    {
      "id": "0b0b48ff-a9e7-40f7-8ab2-0954fcb172c9",
      "plate": "AAA-1111",
      "transmissionType": "MANUAL",
      "vehicleStatus": "FOR_SALE",
      "color": "Preto",
      "price": 5000,
      "modelName": "Polo"
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

## Funcionário

### Registrar funcionário

Método: 

POST

Status:

201

Path:

`/employees`

Corpo de requisição:

```json
{
  "name": "Raphael Norte Frotté",
  "matriculation": "3061391536",
  "email": "raphael.frotte@gmail.com",
  "phone": "81971862916",
  "birthDate": "1998-09-05",
  "cpf": "24174612420",
  "salary": 3000.00,
  "commission": 12,
  "employeeStatus": "ACTIVE",
  "employeeType": "SALLER"
}
```

Corpo de resposta:

```json
{
  "id": "b6682a86-a784-4773-8825-b2b5b7920180",
  "name": "Raphael Norte Frotté",
  "matriculation": "3061391536",
  "email": "raphael.frotte@gmail.com",
  "phone": "81971862916",
  "birthDate": "1998-09-05",
  "cpf": "24174612420",
  "salary": 3000,
  "commission": 12,
  "employeeStatus": "ACTIVE",
  "employeeType": "SALLER"
}
```

Corpo de resposta para erro de requisição

```json
{
  "localDateTime": "2026-09-05 16:58",
  "status": 400,
  "message": "Nome, matrícula, email, telefone ou cpf não deve ser repetido.",
  "path": "/employees"
}
```

### Atualizar funcionário pelo id

Método: 

PUT

Status:

200

Path:

`/employees/{id}`

Parametro:

- id

Corpo de requisição:

```json
{
  "name": "Robson Knupp Mayerhofer",
  "matriculation": "5255598908",
  "email": "robson.mayerhofer@gmail.com",
  "phone": "81987548812",
  "birthDate": "1993-09-05",
  "cpf": "28671494446",
  "salary": 3000.00,
  "commission": 15,
  "employeeStatus": "ACTIVE",
  "employeeType": "SALLER"
}
```

Corpo de resposta:

```json
{
  "id": "b6682a86-a784-4773-8825-b2b5b7920180",
  "name": "Robson Knupp Mayerhofer",
  "matriculation": "5255598908",
  "email": "robson.mayerhofer@gmail.com",
  "phone": "81987548812",
  "birthDate": "1993-09-05",
  "cpf": "28671494446",
  "salary": 3000,
  "commission": 15,
  "employeeStatus": "ACTIVE",
  "employeeType": "SALLER"
}
```

Corpo de resposta para erro de requisição:

```json
{
  "localDateTime": "2026-09-05 16:58",
  "status": 400,
  "message": "Nome, matrícula, email, telefone ou cpf não deve ser repetido.",
  "path": "/employees"
}
```

Corpo de resposta para erro de não encontrado:

```json
{
  "localDateTime": "2026-09-05 18:54",
  "status": 404,
  "message": "Id deve ser existente.",
  "path": "/employees/b6682a86-a784-4773-8825-b2b5b79201801"
}
```

### Procurar funcionário pelo id

Método: 

GET

Status:

200

Path:

`/employees/{id}`

Parametro:

- id

Corpo de resposta:

```json
{
  "id": "b6682a86-a784-4773-8825-b2b5b7920180",
  "name": "Raphael Norte Frotté",
  "matriculation": "3061391536",
  "email": "raphael.frotte@gmail.com",
  "phone": "81971862916",
  "birthDate": "1998-09-05",
  "cpf": "24174612420",
  "salary": 3000,
  "commission": 12,
  "employeeStatus": "ACTIVE",
  "employeeType": "SALLER"
}
```

Corpo de resposta para erro de não encontrado:

```json
{
  "localDateTime": "2026-09-05 18:54",
  "status": 404,
  "message": "Id deve ser existente.",
  "path": "/employees/b6682a86-a784-4773-8825-b2b5b79201801"
}
```

### Listar funcionários

Método: 

GET

Status:

200

Path:

`/employees`

Parametro:

- nome
- status de funcionario
- tipo de funcionario

Corpo de resposta:

```json
{
  "content": [
    {
      "id": "b6682a86-a784-4773-8825-b2b5b7920180",
      "name": "Raphael Norte Frotté",
      "matriculation": "3061391536",
      "email": "raphael.frotte@gmail.com",
      "phone": "81971862916",
      "birthDate": "1998-09-05",
      "cpf": "24174612420",
      "salary": 3000,
      "commission": 12,
      "employeeStatus": "ACTIVE",
      "employeeType": "SALLER"
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

## Cliente

### Registrar cliente

Método: 

POST

Status:

201

Path:

`/employees`

Corpo de requição:

```json
{
  "name": "Alan Youssef Alvarenga",
  "document": "32643861434",
  "email": "alan.alvarenga@gmail.com.br",
  "phone": "81981684308",
  "customerType": "PF"
}
```

Corpo de resposta:

```json
{
  "id": "7a9b25a3-b9b9-4967-8a08-1d406a394299",
  "name": "Alan Youssef Alvarenga",
  "document": "32643861434",
  "email": "alan.alvarenga@gmail.com.br",
  "phone": "81981684308",
  "customerType": "PF"
}
```

Corpo de resposta para erro de requisição:

```json
{
  "localDateTime": "2026-09-05 18:33",
  "status": 400,
  "message": "Nome, documento, email ou telefone não deve ser repetido.",
  "path": "/customers"
}
```

### Atualizar cliente pelo id

Método: 

PUT

Status:

200

Path:

`/employees/{id}`

Corpo de requição:

```json
{
  "name": "Everaldo Pires Vogas",
  "document": "49438977465",
  "email": "everaldo.vogas@gmail.com",
  "phone": "87981873455",
  "customerType": "PF"
}
```

Corpo de resposta:

```json
{
  "id": "7a9b25a3-b9b9-4967-8a08-1d406a394299",
  "name": "Everaldo Pires Vogas",
  "document": "49438977465",
  "email": "everaldo.vogas@gmail.com",
  "phone": "87981873455",
  "customerType": "PF"
}
```

Corpo de resposta para erro de requisição:

```json
{
  "localDateTime": "2026-09-05 18:33",
  "status": 400,
  "message": "Nome, documento, email ou telefone não deve ser repetido.",
  "path": "/customers"
}
```

Corpo de resposta para erro de não encontrado:

```json
{
  "localDateTime": "2026-09-05 19:31",
  "status": 404,
  "message": "Id deve ser existente.",
  "path": "/customers/7a9b25a3-b9b9-4967-8a08-1d406a3942991"
}
```

### Procurar cliente pelo id

Método: 

GET

Status:

200

Path:

`/employees/{id}`

Parametro:

- id

Corpo de resposta:

```json
{
  "id": "7a9b25a3-b9b9-4967-8a08-1d406a394299",
  "name": "Alan Youssef Alvarenga",
  "document": "32643861434",
  "email": "alan.alvarenga@gmail.com.br",
  "phone": "81981684308",
  "customerType": "PF"
}
```

Corpo de resposta para erro de não encontrado:

```json
{
  "localDateTime": "2026-09-05 19:31",
  "status": 404,
  "message": "Id deve ser existente.",
  "path": "/customers/7a9b25a3-b9b9-4967-8a08-1d406a3942991"
}
```

### Listar clientes

Método: 

GET

Status:

200

Path:

`/employees`

Parametro:

- nome
- tipo de cliente

Corpo de resposta:

```json
{
  "content": [
    {
      "id": "7a9b25a3-b9b9-4967-8a08-1d406a394299",
      "name": "Alan Youssef Alvarenga",
      "document": "32643861434",
      "email": "alan.alvarenga@gmail.com.br",
      "phone": "81981684308",
      "customerType": "PF"
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

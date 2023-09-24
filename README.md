# Legacy System Integration Project

This Java project integrates users from a legacy system.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Example](#example)

## Features

- Integrates users from a legacy system.
- Returns integrated data in JSON format.

## Getting Started

Follow these steps to set up and run the project.

### Prerequisites

Ensure you have the following prerequisites installed:

- Java 17 or higher
- Maven

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/Testoni/integration.git

2. Navigate to the project directory:

   ```bash
   cd integration

3. Build the project using Maven:

   ```bash
   mvn clean install

## Example

To help you understand how this project works, here's a test example using the provided input format:

**Sample Input Text File: input.txt**
   ```bash
   0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308
   0000000075                                  Bobbie Batz00000007980000000002     1578.5720211116
```

The input text file for this project follows specific formatting rules:

- Each line in the text file adheres to a pattern with the following rules:

  - `user_id` (size = 10, type = number): Represents the user's unique identifier.
  - `name` (size = 45, type = text): Represents the user's name.
  - `order_id` (size = 10, type = number): Represents the unique identifier for an order.
  - `product_id` (size = 10, type = number): Represents the unique identifier for a product.
  - `value` (size = 12, type = decimal): Represents a decimal value.
  - `date` (size = 8, type = number): Represents a date in the format YYYYMMDD.
    
**Integration and JSON Output:**

After processing the input file, the application generates the following JSON output:
```json
[
  {
    "user_id": "0000000070",
    "name": "Palmer Prosacco",
    "orders": [
      {
        "order_id": "0000000753",
        "total": "1836.74",
        "date": "2021-03-08",
        "products": [
          {
            "product_id": "0000000003",
            "value": "1836.74"
          }
        ]
      }
    ]
  },
  {
    "user_id": "0000000075",
    "name": "Bobbie Batz",
    "orders": [
      {
        "order_id": "0000000798",
        "total": "1578.57",
        "date": "2021-11-16",
        "products": [
          {
            "product_id": "0000000002",
            "value": "1578.57"
          }
        ]
      }
    ]
  }
]

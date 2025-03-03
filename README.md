## 📈 Tandem Workflow Manager - Market Data CockroachDB Service

### 📌 Overview

This service acts as the **central handler** for managing market-data and personal market transactions in a locally
hosted **CockroachDB** instance.
It provides **RESTful APIs** to perform various operations, including **CRUD and complex compute support** for
market entities.

### 🗄️ Managed Tables

#### 1️⃣ **Market Data**

- 📅 **Date** – Stored in `yyyyMMdd` format
- 🔖 **Ticker** – Includes stock exchange & country code
- 💰 **Close Price** *(or Adjusted Close Price)*

#### 2️⃣ **Market Transactions**

- 🔢 **Order ID** *(Primary Key)*
- 🔼 **Direction** – `BUY` / `SELL`
- 🔖 **Ticker** – Includes stock exchange & country code
- 🔄 **Quantity Transacted**
- 💵 **Transaction Price** *(Total Cost)*
- 💲 **Price per Share**
- 📅 **Transaction Date**
- 📆 **Settlement Date**
- 🏷️ **Ticker Name**
- 🏛️ **Ticker Sector**
- 🏦 **Account Type** – `TFSA 🇨🇦` / `FHSA 🇨🇦` / `Non-Registered 🇨🇦`
- 🔢 **Account Number**
- 🏷️ **CUSIP Number** *(Committee on Uniform Securities Identification Procedures)*
- ⚖️ **Transaction Type** – `Fractional` / `Limit` / `Market` / `Stock Split`, etc.
- 📊 **Instrument Type** – `ETF` / `Equity`
- 🌍 **Country Code** – `CA`

### ⚙️ Features

✔ **CRUD and complex operations** on market entities  
✔ **RESTful API endpoints** for seamless integration  
✔ **CockroachDB-backed** storage for high availability and scalability

---

## 🚀 Versions

| Stable     | Latest |
|------------|--------|
| **2.1.22** | 2.1.22 |

## 🛠️ Tech Stack

![Java](https://img.shields.io/badge/Java-17-blue?style=for-the-badge&logo=openjdk)  
![CockroachDB](https://img.shields.io/badge/Database-CockroachDB-green?style=for-the-badge)  
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-brightgreen?style=for-the-badge&logo=spring)  
![Spring JPA](https://img.shields.io/badge/Spring%20JPA-Enabled-orange?style=for-the-badge)  
![Feign Client](https://img.shields.io/badge/Feign%20Client-REST-red?style=for-the-badge)  
![Eureka](https://img.shields.io/badge/Eureka%20Client-Service%20Discovery-blueviolet?style=for-the-badge)  
![Protobuf](https://img.shields.io/badge/Protobuf-Protocol%20Buffers-red?style=for-the-badge)  
![Guava](https://img.shields.io/badge/Guava-Google%20Library-yellow?style=for-the-badge)  
![Gson](https://img.shields.io/badge/Gson-JSON%20Parsing-lightgrey?style=for-the-badge)  
![Apache Commons Lang3](https://img.shields.io/badge/Apache%20Commons%20Lang3-Utilities-orange?style=for-the-badge)  
![Lombok](https://img.shields.io/badge/Lombok-Reduces%20Boilerplate-red?style=for-the-badge)

## 📚 Personal Libraries

- 🏗️ **[twm-artifactory](https://github.com/crackCodeLogn/twm-artifactory/)**  
  *Centralized repository for source and compiled Protocol Buffers (Protobufs) used across TWM projects.*

- 🔗 **[twm-ping-client](https://github.com/crackCodeLogn/twm-ping-client/)**  
  *A lightweight library providing Eureka-based heartbeat (ping) functionality for service discovery.*

## 🧪 Test Libraries

![Mockito](https://img.shields.io/badge/Mockito-Mocking-blue?style=for-the-badge&logo=java)  
![JUnit](https://img.shields.io/badge/JUnit-Testing-red?style=for-the-badge&logo=java)  
![AssertJ](https://img.shields.io/badge/AssertJ-Fluent%20Assertions-orange?style=for-the-badge)  
![Spring Boot Test](https://img.shields.io/badge/Spring%20Boot%20Test-Integration-brightgreen?style=for-the-badge&logo=spring)


---

## Launch mechanism (local mode)

#### Pre-requisites:

1. Start up local cockroachdb cluster
2. Start up [twm-eureka](https://github.com/crackCodeLogn/twm-eureka-service)

#### Launch script:

1. Ensure the stable version is checked out (or latest if you are more brave)
2. Run the [Local Build](#local-build) step if not already done, to generate the jar
3. Fire up the script at '[twm-market-data-crdb.sh](bin/twm-market-data-crdb.sh)' using no args

---

## Local Build

#### Pre-requisites:

1. Checkout the latest [libraries](#-personal-libraries) and build their jars for local maven repo (~/.m2)

#### Command:

> mvn clean install

---
For questions or suggestions, please feel free to reach out to me at [v2k.verma@gmail.com](mailto:v2k.verma@gmail.com)

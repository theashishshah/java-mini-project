# Project Setup Guide

This document provides a step-by-step guide to set up the development environment for this Java-based project, including IDE setup, MySQL configuration, and library dependencies.

---

## 🔧 Prerequisites

Before getting started, ensure the following tools are installed on your system:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)

---

## 📥 Step 1: Clone the Repository

First, clone the project repository:

```bash
git clone <your-repo-url>
cd <your-project-directory>
```


## How to set-up guide
```bash
# install intellij idea

# install mysql workbench

# to see if your local sql is running or not
mysqladmin -u root -p ping

# to see if your local sql is running on particalar port or not
mysqladmin -u root -p --port=4406 ping


```

🔹 Step 2: Add JAR to Project in IntelliJ
Open IntelliJ

From the top menu:
File → Project Structure (or press Ctrl+Alt+Shift+S)

Go to Modules → Dependencies tab

Click the + button → Select JARs or Directories

Navigate to where you downloaded mysql-connector-j-8.0.33.jar and select it

Choose "Compile" scope when prompted

Click OK and Apply
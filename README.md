# Social Network Web Application

## Tecnologies
### *Backend*
* Spring boot (Java)
* JPA - Hibernate
* Spring Security
### *Frontend*
* React
* Tailwindcss
### *Unit Testing*
* JUnit
* Mockito
* Cypress
### *Functional Testing*
* Selenium
* WebDriver
* Thunder Client
### *Performance Testing*
* Jmeter
### *Security Testing*
* Zaproxy

## Download Project
* Clone Repository:
    ```bash
    git clone https://github.com/i-am-sergio/fullstack_project_soft_engineering_ii.git
    ```
* Create Branch:
    ```bash
    git branch examplebranch
    ```
* Switch to that branch:
    ```bash
    git checkout examplebranch
    ```
* Prepare / Stage changes:
    ```bash
    git add .
    ```
* Describe your changes:
    ```bash
    git commit -m "descripcion de tus cambios"
    ```
* Push your changes to your branch:
    ```bash
    git push origin examplebranch
    ```

## Execution (development)

### Backend
Inside the directory **backend**:
* With Maven:
    ```bash
    mvn spring-boot:run
    ```
* With Gradle:
    ```bash
    gradle bootRun
    ```

### Frontend
Inside the directory **client**:
* For the first time, install dependencies:
    ```bash
    npm install
    ```
* Start with npm:
    ```bash
    npm start
    ```

In the browser enter the following url: <https://localhost:3000>

## Run Tests (testing)
### *Unit Testing*
* With Maven:
    ```bash
    mvn test
    ```
* With Gradle:
    ```bash
    gradle test --info
    ```

### *Functional Testing*
* With Python:
    ```bash
    python ./backend/src/test/resources/selenium/login_test.py --verbose
    ```
### *Performance Testing*
* With Jmeter installed:
    ```bash
    jmeter -n -t ./backend/src/test/resources/jmeter/PerformanceTests.jmx -l results.csv -e -o report
    ```
### *Security Testing*
* With Zaproxy installed:
    ```bash
    zap.sh -daemon -port 7000 -quickurl http://localhost:3000 -quickout ${PROJECT_DIR}/reports/security_testing_report.html -quickprogress
    ```
pipeline {
    agent any

    environment {
        SONAR_SCANNER_BIN = "/opt/sonar-scanner/bin"
        PATH = "${SONAR_SCANNER_BIN}:${env.PATH}"
        PROJECT_DIR = "/home/neodev/Develop/fullstack_project_soft_engineering_ii"
        BACKEND_DIR = "${PROJECT_DIR}/backend"
    }

    stages {
        stage("Automatic Build") {
            steps {
                script {
                    dir(BACKEND_DIR) {
                        sh "mvn --version"
                        //sh "mvn clean install" //con tests (funciona)
                        //sh "mvn clean install -DskipTests" // sin tests (funciona)
                    }
                }
            }
        }
        stage("SonarQube Static Analysis") {
            steps {
                script {
                    dir(PROJECT_DIR) {
                        //sh "sonar-scanner" // Funciona
                    }
                }
            }
        }
        stage("Unit Testing") {
            steps {
                echo "junit + mockito tests..."
            }
        }
        stage("Functional Testing"){
            steps {
                echo "Selenium..."
            }
        }
        stage("Performance Testing"){
            steps {
                echo "Jmeter..."
            }
        }
        stage("Security Testing"){
            steps {
                echo "OWASP..."
            }
        }
        stage("Docker Image"){
            steps {
                script {
                    dir(PROJECT_DIR) {
                        // sh "docker compose build" // construye los contenedores (funciona)
                        // sh "docker compose up" // ejecuta los contenedores
                    }
                }
            }
        }
    }
}
pipeline {
    agent any
    environment {
        SONAR_TOKEN_BACKEND = 'sqp_b2872ad27625f15ff27b1e0b1204bc12b355c9cd'
        SONAR_TOKEN_CLIENT = 'sqp_58e7210fe555a54e4e45daacda3304374aa78774'
    }
    stages {
        stage("SonarQube analysis for Backend") {
            steps {
                script {
                    withSonarQubeEnv('sonarqube') {
                        dir("D:\\Proyects\\fullstack_project_soft_engineering_ii\\backend") {
                            bat "sonar-scanner -Dsonar.projectKey=SocialMedia_Backend -Dsonar.projectName=SocialMedia_Backend -Dsonar.host.url=http://localhost:9000 -Dsonar.login=%SONAR_TOKEN_BACKEND% -Dsonar.java.binaries=target/classes -Dsonar.sources=."
                        }
                    }
                }
            }
        }
        stage("SonarQube analysis for Client") {
            steps {
                script {
                    withSonarQubeEnv('sonarqube') {
                        dir("D:\\Proyects\\fullstack_project_soft_engineering_ii\\client") {
                            bat "sonar-scanner -Dsonar.projectKey=SocialMedia_Client -Dsonar.projectName=SocialMedia_Client -Dsonar.host.url=http://localhost:9000 -Dsonar.login=%SONAR_TOKEN_CLIENT% -Dsonar.exclusions=node_modules/** -Dsonar.sources=."
                        }
                    }
                }
            }
        }
        stage("Build") {
            steps {
                script {
                    // Ejecutar comandos en el directorio del proyecto backend
                    dir("D:\\Proyects\\fullstack_project_soft_engineering_ii\\backend") {
                        bat 'echo Hello Build from Jenkins in Backend!'
                        bat 'mvn clean package'
                    }

                    // Ejecutar comandos en el directorio del proyecto cliente
                    dir("D:\\Proyects\\fullstack_project_soft_engineering_ii\\client") {
                        bat 'echo Hello Build from Jenkins in Client!'
                        bat 'npm install'
                    }
                }
            }
        }
        stage("Test"){
            steps {
                bat 'echo Hello Test from Jenkins!'
                dir("D:\\Proyects\\fullstack_project_soft_engineering_ii\\backend") {
                    bat 'echo Hello Build from Jenkins in Backend!'
                    bat 'mvn test'
                }
            }
        }
        stage("Deploy"){
            steps {
                bat 'echo Hello Deploy from Jenkins!'
            }
        }
    }
}
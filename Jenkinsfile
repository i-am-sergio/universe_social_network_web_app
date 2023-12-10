pipeline {
    agent any
    stages {
        stage("SonarQube analysis for Backend") {
            steps {
                script {
                    withSonarQubeEnv('sonarqube') {
                        dir("D:\\Proyects\\fullstack_project_soft_engineering_ii\\backend") {
                            bat 'sonar-scanner.bat -D"sonar.projectKey=SocialMediaII" -D"sonar.sources=." -D"sonar.host.url=http://localhost:9000" -D"sonar.token=sqp_e7dc35d2f7f50c2a2847eef4d29c77f0eb3347e8"'
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
                            bat 'sonar-scanner.bat -D"sonar.projectKey=SocialMedia" -D"sonar.sources=." -D"sonar.host.url=http://localhost:9000" -D"sonar.token=sqp_b72523254b10d5f541905cbb08f019c1bbbfb1d7"'
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

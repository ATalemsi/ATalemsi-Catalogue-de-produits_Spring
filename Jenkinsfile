pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                script {
                    // Checkout source code
                    checkout scm
                    // Run build (e.g., for a Spring app)
                    sh './mvnw clean install -DskipTests'
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    // Run Docker Compose to start your app
                    sh 'docker-compose -f docker-compose.yml up -d'
                }
            }
        }
    }
}

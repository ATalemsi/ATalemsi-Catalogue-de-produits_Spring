pipeline {
    agent any  // This sets the default agent to any available node

    environment {
        SPRING_PROFILES_ACTIVE = 'dev'  // This ensures the correct Spring profile is active
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "Checking out the latest code"
                    checkout scm
                }
            }
        }
        
        stage('Build') {
            steps {
                script {
                    echo "Building the Spring Boot project with Maven"
                    sh 'chmod +x ./mvnw'
                    sh './mvnw clean install -DskipTests'
                }
            }
        }
        
        stage('Dockerize') {
            agent {
                docker {
                    image 'docker:20.10.21' // Docker image that includes Docker
                    args '--privileged -v /var/run/docker.sock:/var/run/docker.sock'
                }
            }
            steps {
                script {
                    echo "Building Docker image for the application"
                    sh 'docker build -t product_managementV2:latest .'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo "Deploying the application using Docker Compose"
                    sh 'docker-compose -f docker-compose.yml up -d'
                }
            }
        }
    }

    post {
        always {
            script {
                echo "Cleaning up workspace"
                cleanWs()
            }
        }
        success {
            script {
                echo "Build and deployment completed successfully!"
            }
        }
        failure {
            script {
                echo "Build or deployment failed!"
            }
        }
    }
}

pipeline {
    agent any  // Runs on any available agent (e.g., Docker, or the Jenkins server itself)

    environment {
        SPRING_PROFILES_ACTIVE = 'dev'  // This ensures the correct Spring profile is active
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the latest code from the Git repository
                checkout scm
            }
        }
        
        stage('Build') {
            steps {
                // Build the Spring Boot project using Maven
                script {
                    echo "Building the project with Maven"
                    sh './mvnw clean install -DskipTests'  // Run Maven wrapper to build the project
                }
            }
        }
        
        stage('Test') {
            steps {
                // Run tests (unit/integration tests) using Maven
                script {
                    echo "Running tests"
                    sh './mvnw test'  // Run tests using Maven
                }
            }
        }
        
        stage('Dockerize') {
            steps {
                script {
                    // Build Docker image for the app
                    echo "Building Docker image"
                    sh 'docker build -t product_managementV2:latest .'  // Replace 'my-app' with your app name
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Deploy the app using Docker Compose
                    echo "Deploying the app"
                    sh 'docker-compose -f docker-compose.yml up -d'  // Deploy using Docker Compose
                }
            }
        }
        
        stage('Clean Up') {
            steps {
                script {
                    // Clean up by stopping the containers after deployment (optional)
                    echo "Cleaning up"
                    sh 'docker-compose -f docker-compose.yml down'  // Optional step to stop containers
                }
            }
        }
    }

    post {
        always {
            // Always clean up workspace
            cleanWs()
        }
        success {
            // Send a success notification (you can use email or Slack)
            echo "Build completed successfully!"
        }
        failure {
            // Send failure notification
            echo "Build failed!"
        }
    }
}

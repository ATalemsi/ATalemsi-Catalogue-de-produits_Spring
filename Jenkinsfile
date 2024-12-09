pipeline {
    agent any  // Runs on any available agent

    environment {
        SPRING_PROFILES_ACTIVE = 'dev'  // Ensures the correct Spring profile is active
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
        
        stage('Install Docker if Missing') {
            steps {
                script {
                    echo "Checking and installing Docker if not present"

                    sh '''
                    if ! command -v docker &> /dev/null; then
                        echo "Docker is not installed. Installing..."
                        
                        # Update package list
                        sudo apt-get update -y

                        # Install required dependencies
                        sudo apt-get install -y \
                            apt-transport-https \
                            ca-certificates \
                            curl \
                            software-properties-common

                        # Add Docker's official GPG key
                        curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg

                        # Set up the stable Docker repository
                        echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

                        # Install Docker
                        sudo apt-get update -y
                        sudo apt-get install -y docker-ce docker-ce-cli containerd.io

                        # Verify Docker installation
                        docker --version
                    else
                        echo "Docker is already installed."
                    fi
                    '''
                }
            }
        }
        
        stage('Dockerize') {
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

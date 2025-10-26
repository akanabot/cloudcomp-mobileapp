pipeline {
    agent any

    environment {
        IMAGE_NAME = "appmobile1"
        CONTAINER_NAME = "appmobile1"
        JENKINS_CONTAINER = "new_cloudcomp_jenkins_1"
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo "🔄 Checkout source code dari repo kamu..."
                git branch: 'main', url: 'https://github.com/akanabot/cloudcomp-mobileapp.git'
            }
        }

        stage('Build Docker Images') {
            steps {
                bat 'docker-compose build'
            }
        }

        stage('Run Docker Containers') {
            steps {
                bat '''
                docker stop jenkins 
                docker rm jenkins 
                docker stop android-builder 
                docker rm android-builder 

                docker-compose down || exit 0
                docker-compose up -d

                docker ps
                '''
            }
        }

        stage('Verify Builder Container Running') {
            steps {
                
                bat '''
 
                ping 127.0.0.1 -n 20 >nul

                docker ps --filter "name=appmobile1"

                docker logs appmobile1 --tail 20
                '''
            }
        }

        stage('Build Android App') {
            steps {
                // --- PERBAIKAN #2 ---
                // Error sebelumnya adalah "no such file or directory", yang berarti
                // container tidak dapat menemukan file './gradlew' di direktori kerja default.
                //
                // Berdasarkan Dockerfile (dari log history), file proyek
                // disalin ke '/app' dan 'WORKDIR' diatur ke '/app'.
                //
                // Untuk mengatasi error "no such file", kita gunakan path absolut
                // ke file gradlew di dalam container, yaitu '/app/gradlew'.
                bat 'docker exec appmobile1 /app/gradlew clean build'
            }
        }
    }

    post {
        success {
            echo 'done'
        }
        failure {
            echo 'fail'
        }
    }
}

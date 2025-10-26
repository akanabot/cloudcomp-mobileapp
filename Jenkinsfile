pipeline {
    agent any

    environment {
        // Sesuaikan nama container dengan docker-compose.yml
        CONTAINER_NAME = "appmobile1"
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo "🔄 Checkout source code..."
                git branch: 'main', url: 'https://github.com/akanabot/cloudcomp-mobileapp.git' [cite: 5]
            }
        }

        stage('Build Docker Images') {
            steps {
                // 'docker-compose build' akan membuat image 'android-builder'
                bat 'docker-compose build'
            }
        }

        stage('Run Docker Containers') {
            steps {
                bat '''
                docker stop appmobile1 || exit 0 
                docker rm appmobile1 || exit 0 
                
                docker-compose down || exit 0
                docker-compose up -d

                docker ps
                ''' [cite: 6, 7, 8]
            }
        }

        stage('Verify Builder Container Running') {
            steps {
                bat '''
                ping 127.0.0.1 -n 20 >nul
                
                docker ps --filter "name=%CONTAINER_NAME%"
                
                docker logs %CONTAINER_NAME% --tail 20
                ''' [cite: 9, 10]
            }
        }

        stage('Build Android App') {
            steps {
                bat '''
                docker exec %CONTAINER_NAME% bash -c "chmod +x ./gradlew && ./gradlew clean build"
                ''' [cite: 11]
            }
        }
    }
    
}
    // 5. Kumpulkan hasil build
    post {
        success {
            echo '✅ Build Sukses. Mengarsipkan artifact...'
            // Ambil APK dari workspace container dan simpan di Jenkins
            archiveArtifacts artifacts: 'app/build/outputs/apk/release/app-release.apk', 
                             onlyIfSuccessful: true
        }
        failure {
            echo '🛑 Build Gagal.'
        }
        always {
            // Bersihkan workspace di dalam container
            cleanWs()
        }
    }
}

pipeline {
    agent any

    environment {
        // Nama container disamakan dengan di docker-compose.yml
        CONTAINER_NAME = "appmobile1"
    }

    stages {
        stage('Checkout Code') {
            steps {
                echo "🔄 Checkout source code..."
                git branch: 'main', url: 'https://github.com/akanabot/cloudcomp-mobileapp.git'
            }
        }

        stage('Build Docker Images') {
            steps {
                // Perintah ini akan build image 'android-builder'
                // menggunakan docker-compose.yml dan dockerfile.txt
                bat 'docker-compose build'
            }
        }

        stage('Run Docker Containers') {
            steps {
                bat '''
                rem Menghentikan dan menghapus container lama (jika ada)
                docker stop %CONTAINER_NAME% || exit 0 
                docker rm %CONTAINER_NAME% || exit 0 
                
                rem Menjalankan container baru
                docker-compose down || exit 0
                docker-compose up -d

                docker ps
                '''
            }
        }

        stage('Verify Builder Container Running') {
            steps {
                bat '''
                rem Beri waktu container untuk stabil
                ping 127.0.0.1 -n 20 >nul
                
                rem Cek apakah container appmobile1 benar-benar berjalan
                docker ps --filter "name=%CONTAINER_NAME%"
                
                rem Tampilkan log terakhir dari container
                docker logs %CONTAINER_NAME% --tail 20
                '''
            }
        }

        stage('Build Android App') {
            steps {
                bat '''
                rem Eksekusi perintah build di dalam container yang berjalan
                rem Menambahkan chmod +x untuk memperbaiki izin gradlew
                docker exec %CONTAINER_NAME% bash -c "chmod +x ./gradlew && ./gradlew clean build"
                '''
            }
        }
    }

    post {
        success {
            echo 'done'
            // Anda bisa tambahkan 'archiveArtifacts' di sini jika mau
            archiveArtifacts artifacts: 'app/build/outputs/apk/release/app-release.apk', 
                             onlyIfSuccessful: true
        }
        failure {
            echo 'fail'
        }
        always {
            // Selalu bersihkan workspace Jenkins
            cleanWs()
        }
    }
}

pipeline {
    // 1. Tentukan bahwa agent build adalah Dockerfile dari repo ini
    agent {
        dockerfile {
            filename 'dockerfile' // Sesuaikan dengan nama file Anda
        }
    }

    stages {
        // 2. Checkout SCM (Git)
        // Jenkins otomatis melakukan checkout kode Anda ke dalam container
        stage('Checkout Code') {
            steps {
                echo "🔄 Checking out code..."
                git branch: 'main', url: 'https://github.com/akanabot/cloudcomp-mobileapp.git' [cite: 5]
            }
        }

        // 3. Perbaiki Izin (sekarang dijalankan PADA kode yang di-checkout)
        stage('Fix Permissions') {
            steps {
                // Perintah 'sh' ini berjalan DI DALAM container Linux
                sh 'dos2unix ./gradlew' [cite: 3]
                sh 'chmod +x ./gradlew'
            }
        }

        // 4. Build Aplikasi
        stage('Build Android App') {
            steps {
                sh './gradlew clean build'
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

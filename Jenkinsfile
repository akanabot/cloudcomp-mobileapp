pipeline {
    // 1. Perintahkan Jenkins untuk menggunakan Dockerfile di repo ini
    agent {
        dockerfile true
    }

    stages {
        // 2. Tahap: Membersihkan proyek
        stage('Clean') {
            steps {
                // Memberi izin eksekusi pada script gradlew
                sh 'chmod +x ./gradlew'
                sh './gradlew clean'
            }
        }

        // 3. Tahap: Menjalankan Unit Test (Opsional tapi direkomendasikan)
        stage('Test') {
            steps {
                // Ganti 'testDebugUnitTest' jika Anda punya skema tes lain
                sh './gradlew testDebugUnitTest'
            }
        }

        // 4. Tahap: Build Aplikasi (Membuat APK)
        stage('Build') {
            steps {
                // Ganti ke 'bundleRelease' jika Anda ingin membuat AAB (untuk Play Store)
                sh './gradlew assembleRelease'
            }
        }
    }

    post {
        // 5. Tahap: Selesai (Sukses atau Gagal)
        always {
            // Simpan file APK/AAB yang sudah jadi
            archiveArtifacts artifacts: 'app/build/outputs/apk/release/app-release.apk',
                             onlyIfSuccessful: true

            // Bersihkan workspace setelah selesai
            cleanWs()
            echo 'Pipeline Finished.'
        }
    }
}
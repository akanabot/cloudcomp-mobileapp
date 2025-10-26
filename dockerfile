# Mulai dari image dasar yang sudah ada Java (JDK) 17
FROM openjdk:17-jdk-slim

# Set variabel lingkungan untuk Android
ENV ANDROID_SDK_ROOT /opt/android-sdk
ENV PATH $PATH:$ANDROID_SDK_ROOT/cmdline-tools/latest/bin:$ANDROID_SDK_ROOT/platform-tools

# Install tools dasar dan Android SDK Command-line tools
# Catatan: Versi commandlinetools bisa berubah, cek versi terbaru jika error
RUN apt-get update && \
    apt-get install -y wget unzip && \
    mkdir -p $ANDROID_SDK_ROOT/cmdline-tools && \
    wget -q "https://dl.google.com/android/repository/commandlinetools-linux-11076708_latest.zip" -O /tmp/tools.zip && \
    unzip /tmp/tools.zip -d $ANDROID_SDK_ROOT/cmdline-tools && \
    mv $ANDROID_SDK_ROOT/cmdline-tools/cmdline-tools $ANDROID_SDK_ROOT/cmdline-tools/latest && \
    rm /tmp/tools.zip

# Terima semua lisensi SDK
RUN yes | sdkmanager --licenses && \
    sdkmanager --update

# -----------------------------------------------------------------
# PENTING: Sesuaikan versi ini dengan file build.gradle Anda
# (compileSdk dan buildToolsVersion)
# -----------------------------------------------------------------
RUN sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"

# Set working directory di dalam container
WORKDIR /app

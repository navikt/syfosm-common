#!/usr/bin/env groovy

pipeline {
    agent any

    tools {
        jdk 'openjdk11'
    }

    environment {
        APPLICATION_NAME = 'syfosmoppgave'
        DOCKER_SLUG = 'syfo'
    }

    stages {
        stage('initialize') {
            steps {
                init action: 'gradle'
            }
        }
        stage('build') {
            steps {
                sh './gradlew build -x test'
            }
        }
        stage('run tests (unit & intergration)') {
            steps {
                sh './gradlew test'
            }
        }
        stage('publish maven artifacts') {
            steps {
                sh './gradlew publish'
            }
        }
    }
    post {
        always {
            postProcess action: 'always'
            //junit '**/build/test-results/test/*.xml'
            archiveArtifacts artifacts: '**/build/libs/*', allowEmptyArchive: true
        }
        success {
            postProcess action: 'success'
        }
        failure {
            postProcess action: 'failure'
        }
    }
}
